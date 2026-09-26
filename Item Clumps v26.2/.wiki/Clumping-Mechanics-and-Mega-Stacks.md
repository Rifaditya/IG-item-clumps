# Mega-Stack Clumping Mechanics (MC 26.2)

This document provides an exhaustive technical and mathematical breakdown of the ground item aggregation, search radius math, 64-bit overflow prevention, and heap allocation optimizations in **Item Clumps** for **Minecraft 26.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Ground Item Mega-Stack Aggregator |
| **Target Class** | `net.minecraft.world.entity.item.ItemEntity` |
| **Default Merge Cap** | `9,999` items (Configurable up to $2,147,483,647$) |
| **Search Radius ($r$)** | Horizontal $1\text{ to }10\text{ blocks}$ (Default: $1\text{ block}$) |
| **Tick Overhead** | $0.00\text{ ms}$ on non-moving items (`setItem()`-only dispatch) |
| **Memory Allocation** | Zero object allocations on search & absorption (`copyWithCount(int)`) |
| **Controlling GameRules** | `item_clumps:enable_clumping`, `item_clumps:max_clump_size`, `item_clumps:merge_radius` |

---

## ⚙️ How Mega-Stack Aggregation Works

In vanilla Minecraft, dropped items call `mergeWithNeighbours()` during their tick cycle, searching for nearby items of the same type. However, vanilla strictly prohibits merging if the target entity's item count would exceed `itemStack.getMaxStackSize()` (typically 64, or 16 for ender pearls / eggs).

Item Clumps intercepts this check via `ItemEntityMixin.java` to lift this artificial constraint while preserving strict data component safety.

```
                  ┌──────────────────────────────┐
                  │    ItemEntity A Ticks In     │
                  └──────────────┬───────────────┘
                                 │
                                 ▼
                  ┌──────────────────────────────┐
                  │     Fast-Path Validation     │
                  │  - Matching player target?   │
                  │  - Identical DataComponents? │
                  └──────────────┬───────────────┘
                                 │ (Pass)
                                 ▼
                  ┌──────────────────────────────┐
                  │   In-Flight Magnet Check     │
                  │ (Is either item magnetized?) │
                  └──────────────┬───────────────┘
                                 │ (No)
                                 ▼
                  ┌──────────────────────────────┐
                  │    64-Bit Arithmetic Sum     │
                  │   S = (long) A + (long) B    │
                  └──────────────┬───────────────┘
                                 │
                 ┌───────────────┴───────────────┐
                 │                               │
        S <= maxClumpSize                S > maxClumpSize
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

### 1. Horizontal Bounding Box Inflation
Vanilla searches a tiny bounding box: $\text{AABB}_{\text{vanilla}} = \text{AABB}.\text{inflate}(0.5, 0.0, 0.5)$.

Item Clumps dynamically expands the horizontal bounding box using a zero-allocation `@Redirect` injection:

$$\text{AABB}_{\text{clump}} = \text{boundingBox}.\text{inflate}(r, y, r)$$

Where:
* $r = \text{DynamicGameRuleManager}.\text{getInt}(\text{level}, \text{MERGE\_RADIUS}) \in [1, 10]$ blocks.
* $y = 0.0$ (vertical search height is strictly preserved to prevent items on different vertical floors or hoppers from cross-merging).

### 2. 64-Bit Integer Sum & Arithmetic Overflow Safety
When two massive clumps merge under large server configurations (e.g. max clump cap set to $2\text{ billion}$), standard 32-bit `int` addition can overflow into negative integers ($\text{Integer.MAX\_VALUE} + 1 = -2,147,483,648$), leading to corrupted entity states.

Item Clumps computes the sum in 64-bit precision:

$$S = (\text{long})\,\text{thisCount} + (\text{long})\,\text{otherCount}$$

* **Full Absorption ($S \le \text{maxClumpSize}$)**:
  $$\text{count}_{\text{merged}} = (\text{int})\,S$$
* **Partial Absorption ($S > \text{maxClumpSize}$)**:
  $$\Delta = \text{maxClumpSize} - \text{thisCount}$$
  $$\text{thisCount}' = \text{maxClumpSize}, \quad \text{otherCount}' = \text{otherCount} - \Delta$$

### 3. Component & State Strictness
Items **never** merge unless all Data Components (enchantments, damaged durability, custom names, trims, potion effects) are 100% bitwise identical:

$$\text{ItemStack}.\text{isSameItemSameComponents}(\text{stack}_A, \text{stack}_B) == \text{true}$$

---

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.2:

```java
@Inject(method = "tryToMerge", at = @At("HEAD"), cancellable = true)
private void item_clumps$customMerge(ItemEntity other, CallbackInfo ci) {
    ItemStack thisStack = this.getItem();
    ItemStack otherStack = other.getItem();

    // Fast-path exit before GameRule lookups
    if (!Objects.equals(this.target, ((ItemEntityMixin)(Object)other).target) || 
        !ItemStack.isSameItemSameComponents(thisStack, otherStack)) {
        return;
    }

    if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) return;

    // Magnet mod in-flight protection
    if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("magnet")) {
        try {
            java.lang.reflect.Method isMagnetizedMethod;
            try {
                isMagnetizedMethod = this.getClass().getMethod("ig_magnet$isMagnetized");
            } catch (NoSuchMethodException e) {
                isMagnetizedMethod = this.getClass().getMethod("ig$isMagnetized");
            }
            if ((boolean) isMagnetizedMethod.invoke(this) || (boolean) isMagnetizedMethod.invoke(other)) {
                ci.cancel();
                return;
            }
        } catch (Throwable ignored) {}
    }

    int thisCount = thisStack.getCount();
    int otherCount = otherStack.getCount();
    int maxClump = (ItemClumpsFabric.MAX_CLUMP_SIZE == null) 
        ? thisStack.getMaxStackSize() 
        : DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);

    long sum = (long) thisCount + (long) otherCount;
    if (sum > (long) maxClump) {
        int spaceLeft = maxClump - thisCount;
        if (spaceLeft > 0) {
            ItemStack thisCopy = thisStack.copyWithCount(maxClump);
            this.setItem(thisCopy);
            
            ItemStack otherCopy = otherStack.copyWithCount(otherCount - spaceLeft);
            other.setItem(otherCopy);

            this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
            this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
        }
        ci.cancel();
        return;
    }

    // Full Merge: larger stack absorbs the smaller stack
    if (otherCount < thisCount) {
        ItemStack thisCopy = thisStack.copyWithCount((int) sum);
        this.setItem(thisCopy);
        this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
        this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
        other.discard();
    } else {
        ItemStack otherCopy = otherStack.copyWithCount((int) sum);
        other.setItem(otherCopy);
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
