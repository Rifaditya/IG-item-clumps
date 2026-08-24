# Holographic Labels & Count Thresholds (MC 26.2)

This document details the floating count indicator system, `label_min_count` threshold gating, and pure vanilla client name tag rendering of **Item Clumps** for **Minecraft 26.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Server-Side Holographic Name Tag Provider |
| **Rendering Strategy** | Native Vanilla Custom Name Tags (`setCustomName` / `setCustomNameVisible`) |
| **Client Mod Required?** | **No** (100% rendered by un-modded vanilla Minecraft clients) |
| **Label Format Pattern** | `<item_localized_name> x<count>` (e.g. `Diamond x128`, `Oak Log x500`) |
| **Threshold GameRule** | `item_clumps:label_min_count` (Default: `-1`, uses `DataComponents.MAX_STACK_SIZE`) |
| **Display Toggle GameRule** | `item_clumps:render_labels` (Default: `true`) |
| **Tick Overhead** | $0.00\text{ ms}$ (Triggered strictly on `setItem()` mutation) |

---

## 🏷️ How Floating Labels Render on Vanilla Clients

Rather than requiring a client-side mod or sending custom OpenGL rendering packets, Item Clumps leverages vanilla Minecraft's built-in entity custom name tag rendering:

```
                      ┌───────────────────────────────┐
                      │ Ground Item Stack Size Changes│
                      │      (ItemEntity.setItem)     │
                      └───────────────┬───────────────┘
                                      │
                                      ▼
                      ┌───────────────────────────────┐
                      │  Is render_labels Enabled?    │
                      │  Does count > maxStack?       │
                      └───────────────┬───────────────┘
                                      │
                     ┌────────────────┴────────────────┐
                     │ (Yes)                           │ (No)
                     ▼                                 ▼
       ┌───────────────────────────┐     ┌───────────────────────────┐
       │ Set Entity Custom Name    │     │ Clear Entity Custom Name  │
       │ setCustomName(name xCount)│     │ setCustomName(null)       │
       │ setCustomNameVisible(true)│     │ setCustomNameVisible(false│
       └─────────────┬─────────────┘     └───────────────────────────┘
                     │
                     ▼
       ┌───────────────────────────┐
       │ SyncedEntityData Packet   │
       │ Sent to all nearby clients│
       └─────────────┬─────────────┘
                     │
                     ▼
       ┌───────────────────────────┐
       │ Vanilla Client Renders    │
       │ Clean Floating Name Tag!  │
       └───────────────────────────┘
```

---

## ⚙️ Threshold Customization (`label_min_count`)

In Minecraft 26.2, administrators can customize exactly when labels appear using the `item_clumps:label_min_count` GameRule:

| `label_min_count` Value | Threshold Behavior |
| :---: | :--- |
| **`-1` (Default)** | **Vanilla Stack Size**: Labels only appear once the clump exceeds its native maximum stack size ($> 64$ for cobblestone, $> 16$ for pearls, $> 1$ for swords). |
| **`0`** | **Always Visible**: Every dropped item entity in the world displays its count label (e.g. `Dirt x1`). |
| **`100`** | **Mega-Clump Filter**: Labels are suppressed until a clump reaches $101+$ items. |
| **`1000`** | **Ultra High-Yield Mode**: Only massive storage clumps display labels. |

---

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.2:

```java
private void item_clumps$updateVanillaNameTag() {
    if (this.level() == null || this.level().isClientSide()) return;
    ItemStack stack = this.getItem();
    if (stack.isEmpty()) {
        this.setCustomName(null);
        this.setCustomNameVisible(false);
        return;
    }

    int count = stack.getCount();
    int labelMin = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.LABEL_MIN_COUNT);
    int maxStack = (labelMin == -1) ? stack.getOrDefault(DataComponents.MAX_STACK_SIZE, 1) : labelMin;
    
    if (DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.RENDER_LABELS) && count > maxStack) {
        Component name = stack.getItemName().copy().append(" x" + count);
        if (!this.hasCustomName() || !this.getCustomName().getString().equals(name.getString())) {
            this.setCustomName(name);
            this.setCustomNameVisible(true);
        }
    } else {
        if (this.hasCustomName()) {
            this.setCustomName(null);
            this.setCustomNameVisible(false);
        }
    }
}

@Inject(method = "setItem", at = @At("TAIL"))
private void item_clumps$onSetItem(ItemStack itemStack, CallbackInfo ci) {
    if (!this.level().isClientSide()) {
        this.item_clumps$updateVanillaNameTag();
    }
}
```

---

## 🔗 Related Documentation
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
* [[GameRules Reference|GameRules]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
