# Hopper & Automation Integration (MC 26.1.2)

This document details the hopper extraction mechanics, transfer rate parity, redstone sorter stability, and container integration of **Item Clumps** for **Minecraft 26.1.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Hopper Drip Extraction Engine |
| **Target Class** | `net.minecraft.world.level.block.entity.HopperBlockEntity` |
| **Extraction Method** | `HopperBlockEntity.addItem(Container, ItemEntity)` |
| **Extraction Rate** | $1\text{ item per } 8\text{ ticks}$ ($2.5\text{ items/second}$) |
| **Item Slice Strategy** | `baseItem.setCount(1)` |
| **Automation Compatibility** | 100% compatible with Vanilla Redstone Item Sorters & Filters |

---

## ⚙️ The Automation Drip Architecture

In vanilla Minecraft, hoppers extract entire `ItemEntity` instances if their stack size is $\le 64$ and the hopper has adequate space. If a mod blindly allowed a hopper to absorb a mega-clump containing $5,000$ items, it would instantly overflow the hopper or delete thousands of items.

Item Clumps intercepts `HopperBlockEntity.addItem` to enforce **Drip Extraction**:

```
                       ┌────────────────────────────┐
                       │     Hopper Tick Cycle      │
                       │     (Every 8 Game Ticks)   │
                       └─────────────┬──────────────┘
                                     │
                                     ▼
                       ┌────────────────────────────┐
                       │  Is Item Entity Count > 1? │
                       └─────────────┬──────────────┘
                                     │
                    ┌────────────────┴────────────────┐
                    │ (Yes)                           │ (No)
                    ▼                                 ▼
      ┌───────────────────────────┐     ┌───────────────────────────┐
      │ Clone Single Item Slice   │     │ Native Vanilla Extraction │
      │  baseItem = stack.copy()  │     │ (Standard 1-item / stack) │
      │  baseItem.setCount(1)     │     └───────────────────────────┘
      └─────────────┬─────────────┘
                    │
                    ▼
      ┌───────────────────────────┐
      │  Try Insert Into Hopper   │
      │ HopperBlockEntity.addItem │
      └─────────────┬─────────────┘
                    │
           ┌────────┴────────┐
           │                 │
      (Success)           (Failed / Full)
           │                 │
           ▼                 ▼
 ┌───────────────────┐ ┌───────────────┐
 │ Shrink Ground     │ │ Abort Transfer│
 │ Clump by 1;       │ │ Entity Retains│
 │ Refresh Name Tag. │ │ Full Count.   │
 └───────────────────┘ └───────────────┘
```

---

## 💻 Ground-Truth Java Source Implementation

From `HopperBlockEntityMixin.java` in Minecraft 26.1.2:

```java
@Inject(method = "addItem(Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/item/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
private static void item_clumps$customHopperExtract(Container container, ItemEntity entity, CallbackInfoReturnable<Boolean> cir) {
    ItemStack itemStack = entity.getItem();
    int count = itemStack.getCount();
    if (count > 1) {
        // Entity is a clump. Extract exactly 1 item.
        ItemStack baseItem = itemStack.copy();
        baseItem.setCount(1);
        
        ItemStack result = HopperBlockEntity.addItem(null, container, baseItem, null);
        if (result.isEmpty()) {
            // Hopper successfully absorbed the 1 item. Shrink entity stack.
            ItemStack originalStack = entity.getItem();
            originalStack.shrink(1);
            entity.setItem(originalStack); // Updates standard item tracker and custom name
            cir.setReturnValue(true);
        } else {
            // Hopper was full or couldn't take it
            cir.setReturnValue(false);
        }
    }
}
```

---

## 🔗 Related Documentation
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
* [[Despawn Age Rules|Despawn-Timer-and-Age-Inheritance]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
