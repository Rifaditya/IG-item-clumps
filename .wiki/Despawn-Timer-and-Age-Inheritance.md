# Despawn Timer & Age Inheritance (MC 26.1.2)

This document details the despawn lifecycle, tick-to-second conversion math, youngest age inheritance rules, and farm production safeguards in **Item Clumps** for **Minecraft 26.1.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Youngest Age Despawn Preserver |
| **Vanilla Despawn Threshold** | $6,000\text{ ticks}$ ($300\text{ seconds} = 5.0\text{ minutes}$) |
| **Age Inheritance Rule** | $\text{age}_{\text{merged}} = \min(\text{age}_A, \text{age}_B)$ (Youngest item wins) |
| **Pickup Delay Rule** | $\text{pickupDelay}_{\text{merged}} = \max(\text{pickupDelay}_A, \text{pickupDelay}_B)$ (Longest delay kept) |
| **Target Field** | `ItemEntity.age` and `ItemEntity.pickupDelay` |

---

## ⏳ Tick-to-Second Timing Math

In Minecraft, entity timers run at 20 ticks per second:

$$1\text{ second} = 20\text{ ticks}$$
$$1\text{ minute} = 60\text{ seconds} = 1,200\text{ ticks}$$
$$5\text{ minutes} = 300\text{ seconds} = 6,000\text{ ticks}$$

In vanilla Minecraft, an `ItemEntity` increments its internal `age` field by $+1$ every game tick. When $\text{age} \ge 6000$, the item is permanently discarded from the world.

---

## 🛡️ The Youngest Item Safeguard Rule

When two items merge in Item Clumps, the surviving entity executes:

$$\text{age}_{\text{merged}} = \min(\text{age}_{\text{this}}, \text{age}_{\text{other}})$$

Continuous item flow in active mob grinders, automated tree farms, or quarry drill lines repeatedly refreshes the clump age to `0`. As long as the farm is actively generating items, **no items will ever despawn**.

Once the farm stops producing items, the clump sits idle on the ground, and the age timer counts down uninhibited from $0$ to $6000$, safely cleaning up all uncollected items after 5 minutes of inactivity.

---

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.1.2:

```java
// Complete Merge: larger stack absorbs the smaller stack
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
```

---

## 🔗 Related Documentation
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
* [[Hopper Integration|Hopper-and-Automation-Integration]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
