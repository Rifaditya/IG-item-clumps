# Holographic Labels (MC 26.1.2)

This document details the floating count indicator system and pure vanilla client name tag rendering of **Item Clumps** for **Minecraft 26.1.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Server-Side Holographic Name Tag Provider |
| **Rendering Strategy** | Native Vanilla Custom Name Tags (`setCustomName` / `setCustomNameVisible`) |
| **Client Mod Required?** | **No** (100% rendered by un-modded vanilla Minecraft clients) |
| **Label Format Pattern** | `<item_localized_name> x<count>` (e.g. `Diamond x128`, `Oak Log x500`) |
| **Threshold Rule** | Displays when $\text{count} > \text{maxStackSize}$ (e.g. $> 64$ for cobblestone) |
| **Display Toggle GameRule** | `item_clumps:render_labels` (Default: `true`) |

---

## 🏷️ How Floating Labels Render on Vanilla Clients

Item Clumps leverages vanilla Minecraft's built-in entity custom name tag rendering:

```
                      ┌───────────────────────────────┐
                      │ Ground Item Stack Size Changes│
                      └───────────────┬───────────────┘
                                      │
                                      ▼
                      ┌───────────────────────────────┐
                      │  Is render_labels Enabled?    │
                      │  Does count > maxStackSize?   │
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

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.1.2:

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
    int maxStack = stack.getMaxStackSize();
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

@Inject(method = "tick", at = @At("HEAD"))
private void item_clumps$onTick(CallbackInfo ci) {
    if (!this.level().isClientSide()) {
        this.item_clumps$updateVanillaNameTag();
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
