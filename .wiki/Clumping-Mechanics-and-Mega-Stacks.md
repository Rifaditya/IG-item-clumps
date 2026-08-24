# Mega-Stack Clumping Mechanics (MC 26.1.2)

This document provides a technical and mathematical breakdown of ground item aggregation, search radius math, and entity merging in **Item Clumps** for **Minecraft 26.1.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Ground Item Mega-Stack Aggregator |
| **Target Class** | `net.minecraft.world.entity.item.ItemEntity` |
| **Default Merge Cap** | `9,999` items (Configurable up to $2,147,483,647$) |
| **Search Radius ($r$)** | Horizontal $1\text{ to }10\text{ blocks}$ (Default: $1\text{ block}$) |
| **Radius Injection** | `@ModifyArgs` on `AABB.inflate(DDD)` |
| **Controlling GameRules** | `item_clumps:enable_clumping`, `item_clumps:max_clump_size`, `item_clumps:merge_radius` |

---

## ⚙️ How Mega-Stack Aggregation Works

In vanilla Minecraft, dropped items call `mergeWithNeighbours()` during their tick cycle, searching for nearby items of the same type. However, vanilla strictly prohibits merging if the target entity's item count would exceed `itemStack.getMaxStackSize()` (typically 64).

Item Clumps intercepts this check via `ItemEntityMixin.java` to lift this constraint while preserving strict data component safety.

```
                  ┌──────────────────────────────┐
                  │    ItemEntity A Ticks In     │
                  └──────────────┬───────────────┘
                                 │
                                 ▼
                  ┌──────────────────────────────┐
                  │     Feasibility Check        │
                  │  - Matching player target?   │
                  │  - Identical DataComponents? │
                  └──────────────┬───────────────┘
                                 │ (Pass)
                                 ▼
                  ┌──────────────────────────────┐
                  │   Combine Stacks Math Check  │
                  └──────────────┬───────────────┘
                                 │
                 ┌───────────────┴───────────────┐
                 │                               │
    thisCount + otherCount <= maxClump    thisCount + otherCount > maxClump
                 │                               │
                 ▼                               ▼
       ┌───────────────────┐           ┌───────────────────┐
       │   Complete Merge  │           │   Partial Merge   │
       │ Larger absorbs    │           │ Fill entity A to  │
       │ smaller entity;   │           │ maxClumpSize;     │
       │ Younger age kept. │           │ B keeps remainder.│
       └───────────────────┘           └───────────────────┘
```

---

## 📐 Mathematical Formulas & Precision Math

### 1. Horizontal Bounding Box Inflation (`@ModifyArgs`)
$$\text{args.set}(0, r), \quad \text{args.set}(2, r)$$

Where:
* $r = \text{DynamicGameRuleManager}.\text{getInt}(\text{level}, \text{MERGE\_RADIUS}) \in [1, 10]$ blocks.
* Vertical height index ($1$) is untouched, preserving vertical separation between different floor levels.

### 2. Item Count Transfer Math
* **Full Absorption ($S \le \text{maxClumpSize}$)**:
  $$\text{count}_{\text{merged}} = \text{thisCount} + \text{otherCount}$$
* **Partial Absorption ($S > \text{maxClumpSize}$)**:
  $$\Delta = \text{maxClumpSize} - \text{thisCount}$$
  $$\text{thisCount}' = \text{maxClumpSize}, \quad \text{otherCount}' = \text{otherCount} - \Delta$$

---

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.1.2:

```java
@ModifyArgs(method = "mergeWithNeighbours", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
private void item_clumps$modifySearchRadius(Args args) {
    if (DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) {
        double radius = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MERGE_RADIUS);
        args.set(0, radius);
        args.set(2, radius);
    }
}

@Inject(method = "tryToMerge", at = @At("HEAD"), cancellable = true)
private void item_clumps$customMerge(ItemEntity other, CallbackInfo ci) {
    if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) return;

    ItemStack thisStack = this.getItem();
    ItemStack otherStack = other.getItem();

    if (!Objects.equals(this.target, ((ItemEntityMixin)(Object)other).target) || 
        !ItemStack.isSameItemSameComponents(thisStack, otherStack)) {
        return;
    }

    int thisCount = thisStack.getCount();
    int otherCount = otherStack.getCount();
    int maxClump = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);

    if (thisCount + otherCount > maxClump) {
        int spaceLeft = maxClump - thisCount;
        if (spaceLeft > 0) {
            thisStack.setCount(maxClump);
            this.setItem(thisStack);
            otherStack.setCount(otherCount - spaceLeft);
            other.setItem(otherStack);
        }
        ci.cancel();
        return;
    }

    // Full Merge: larger stack absorbs the smaller stack
    if (otherCount < thisCount) {
        thisStack.setCount(thisCount + otherCount);
        this.setItem(thisStack);
        this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
        this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
        other.discard();
    } else {
        otherStack.setCount(thisCount + otherCount);
        other.setItem(otherStack);
        ((ItemEntityMixin)(Object)other).pickupDelay = Math.max(((ItemEntityMixin)(Object)other).pickupDelay, this.pickupDelay);
        ((ItemEntityMixin)(Object)other).age = Math.min(((ItemEntityMixin)(Object)other).age, this.age);
        this.discard();
    }
    ci.cancel();
}
```

---

## 🔗 Related Documentation
* [[Smart Pickup System|Smart-Pickup-and-Inventory-Distribution]]
* [[Despawn Age Rules|Despawn-Timer-and-Age-Inheritance]]
* [[GameRules Reference|GameRules]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
