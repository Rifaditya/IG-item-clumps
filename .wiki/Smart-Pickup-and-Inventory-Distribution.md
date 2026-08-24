# Smart Pickup & Inventory Distribution (MC 26.1.2)

This document details the chunked iterative inventory intake, capacity simulation, vanilla stack creation, and zero-NBT pollution architecture of **Item Clumps** for **Minecraft 26.1.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Chunked Inventory Dispatcher |
| **Target Class** | `net.minecraft.world.entity.item.ItemEntity` |
| **Inventory Limits** | Strict vanilla compliance ($64\text{ for normal items}$, $16\text{ for pearls/eggs}$) |
| **Data Cleanliness** | $100\%\text{ Pure Vanilla ItemStacks}$ (Zero custom NBT or tag pollution) |
| **Pickup Loop** | Chunked iteration (`Math.min(count, maxStack)`) |
| **Statistics Tracking** | Triggers vanilla `Stats.ITEM_PICKED_UP.get(item)` |

---

## 🎒 Chunked Pickup Loop Architecture

In Minecraft 26.1.2, Item Clumps feeds mega-clumps into the player inventory using a chunked while-loop that breaks down large quantities into safe, vanilla-sized stacks (e.g. 64 at a time):

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
                │         Iterative Chunked Loop               │
                │  toTake = Math.min(count, maxStackSize)      │
                │  player.getInventory().add(chunk)            │
                └──────────────────────┬───────────────────────┘
                                       │
                     ┌─────────────────┴─────────────────┐
                     │                                   │
              Inventory Has Space                Inventory Becomes Full
                     │                                   │
                     ▼                                   ▼
        ┌─────────────────────────┐         ┌─────────────────────────┐
        │  Entire Clump Absorbed  │         │     Partial Pickup      │
        │ - 500 items transferred │         │ - Transferred what fits │
        │ - Clump entity removed  │         │ - Remaining items stay  │
        │ - Sound & particles play│         │   in ground clump       │
        └─────────────────────────┘         └─────────────────────────┘
```

---

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.1.2:

```java
@Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
private void item_clumps$smartPickup(Player player, CallbackInfo ci) {
    if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING) || 
        this.level().isClientSide()) return;

    int count = this.getItem().getCount();
    if (count <= 1) return; // Allow vanilla to handle normal 1-count items

    ItemStack baseItem = this.getItem().copy();
    baseItem.setCount(1); // Ensure base count is 1 for simulation

    if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID()))) {
        int originalCount = count;
        int maxStack = baseItem.getMaxStackSize();
        
        while (count > 0) {
            int toTake = Math.min(count, maxStack);
            ItemStack chunk = baseItem.copy();
            chunk.setCount(toTake);

            player.getInventory().add(chunk);
            int added = toTake - chunk.getCount();

            if (added > 0) {
                count -= added;
                player.take(this, added);
                player.awardStat(net.minecraft.stats.Stats.ITEM_PICKED_UP.get(baseItem.getItem()), added);
            }

            if (!chunk.isEmpty()) {
                // Player inventory is full
                break;
            }
        }

        if (count != originalCount) {
            ItemStack stack = this.getItem();
            stack.setCount(count);
            this.setItem(stack);
            if (count <= 0) {
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
* [[Architecture & Mixins|Architecture-and-Mixins]]
