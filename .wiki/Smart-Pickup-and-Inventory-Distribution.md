# Smart Pickup & Inventory Distribution (MC 26.2)

This document details the single-batch inventory intake, capacity simulation, vanilla stack creation, and zero-NBT pollution architecture of **Item Clumps** for **Minecraft 26.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Smart Batch Inventory Dispatcher |
| **Target Class** | `net.minecraft.world.entity.item.ItemEntity` |
| **Inventory Limits** | Strict vanilla compliance ($64\text{ for normal items}$, $16\text{ for pearls/eggs}$) |
| **Data Cleanliness** | $100\%\text{ Pure Vanilla ItemStacks}$ (Zero custom NBT, tags, or components) |
| **Pickup Animation** | Native `player.take(this, added)` entity animation & sound effects |
| **Statistics Tracking** | Triggers vanilla `Stats.ITEM_PICKED_UP.get(item)` |
| **Partial Pickup** | Fully supported (Leaves uncollected remainder on ground) |

---

## 🎒 The Smart Pickup Lifecycle

A critical design requirement of Item Clumps is **Zero Inventory Pollution**. Even though an entity on the ground can hold $9,999$ items, the player's personal inventory stack limits are **never modified or expanded**.

```
                ┌──────────────────────────────────────────────┐
                │ Player Collides with Ground Clump (e.g. 500x)│
                └──────────────────────┬───────────────────────┘
                                       │
                                       ▼
                ┌──────────────────────────────────────────────┐
                │        Pickup Validity & Target Check        │
                │     - pickupDelay == 0?                      │
                │     - target == null || target == player?    │
                └──────────────────────┬───────────────────────┘
                                       │ (Pass)
                                       ▼
                ┌──────────────────────────────────────────────┐
                │  Single-Batch Native Inventory Dispatch      │
                │       player.getInventory().add(stack)       │
                └──────────────────────┬───────────────────────┘
                                       │
                     ┌─────────────────┴─────────────────┐
                     │                                   │
              Inventory Has Space                Inventory Becomes Full
                     │                                   │
                     ▼                                   ▼
        ┌─────────────────────────┐         ┌─────────────────────────┐
        │  Entire Clump Absorbed  │         │     Partial Pickup      │
        │ - 500 items transferred │         │ - e.g. 192 items added  │
        │ - Clump entity removed  │         │ - 308 items remain in   │
        │ - Sound & particles play│         │   ground clump entity   │
        └─────────────────────────┘         └─────────────────────────┘
```

---

## 🛡️ Zero NBT Pollution Guarantee

Items on the ground display a custom name tag (e.g. `Stone x500`) to communicate their count to players. However, when those items enter a player's inventory:
1. **Name Tags Removed**: The custom name tag belongs strictly to the `ItemEntity`, not the underlying `ItemStack`.
2. **Vanilla Stacking**: Because the `ItemStack` entering the player inventory has no custom components, it stacks seamlessly with existing items in chests, shulker boxes, and vanilla crafting tables.
3. **No Ghost Data**: World saves remain 100% clean and uncorrupted, even if the mod is later uninstalled from the server.

---

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.2:

```java
@Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
private void item_clumps$smartPickup(Player player, CallbackInfo ci) {
    if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING) || 
        this.level().isClientSide()) return;

    int count = this.getItem().getCount();
    if (count <= 1) return; // Allow vanilla to handle normal 1-count items

    if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID()))) {
        ItemStack pickupStack = this.getItem().copy();
        int originalCount = count;

        // Native batch addition into player inventory
        player.getInventory().add(pickupStack);
        int added = originalCount - pickupStack.getCount();

        if (added > 0) {
            player.take(this, added);
            player.awardStat(net.minecraft.stats.Stats.ITEM_PICKED_UP.get(pickupStack.getItem()), added);

            ItemStack remainingStack = this.getItem().copyWithCount(pickupStack.getCount());
            this.setItem(remainingStack);

            if (pickupStack.getCount() <= 0) {
                player.onItemPickup((ItemEntity) (Object) this);
            }
        }
        ci.cancel(); // Prevent vanilla pickup interference
    }
}
```

---

## 🔗 Related Documentation
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
* [[Hopper Integration|Hopper-and-Automation-Integration]]
* [[Advancements & Statistics|Advancements]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
