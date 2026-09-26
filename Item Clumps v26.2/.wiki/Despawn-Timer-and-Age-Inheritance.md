# Despawn Timer & Age Inheritance (MC 26.2)

This document details the despawn lifecycle, tick-to-second conversion math, youngest age inheritance rules, and farm production safeguards in **Item Clumps** for **Minecraft 26.2**.

---

## 📊 Feature Infobox

| Property | Specification |
| :--- | :--- |
| **System Name** | Youngest Age Despawn Preserver |
| **Vanilla Despawn Threshold** | $6,000\text{ ticks}$ ($300\text{ seconds} = 5.0\text{ minutes}$) |
| **Age Inheritance Rule** | $\text{age}_{\text{merged}} = \min(\text{age}_A, \text{age}_B)$ (Youngest item wins) |
| **Pickup Delay Rule** | $\text{pickupDelay}_{\text{merged}} = \max(\text{pickupDelay}_A, \text{pickupDelay}_B)$ (Longest delay kept) |
| **Target Field** | `ItemEntity.age` and `ItemEntity.pickupDelay` |
| **Idle Despawn Behavior** | Standard 5-minute timer counts down when no new items merge |

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

```
  Existing Ground Clump                           Newly Dropped Item
  - Count: 500 Cobblestone                       - Count: 1 Cobblestone
  - Age: 5,800 ticks (4m 50s old)                - Age: 0 ticks (Brand new)
                 │                                              │
                 └──────────────────────┬───────────────────────┘
                                        │ (Item Clumps Merging)
                                        ▼
                           Merged Mega-Stack Entity
                           - Count: 501 Cobblestone
                           - Age: min(5800, 0) = 0 ticks (Fresh 5-minute timer)
```

### Why This Protects High-Yield Farms
* **The Problem with Vanilla Lifespans**: In vanilla, if you have a slow collector, newly dropped items merging into older items could be deleted 10 seconds later if the host entity was already 4 minutes and 50 seconds old.
* **The Item Clumps Solution**: Continuous item flow in active mob grinders, automated tree farms, or quarry drill lines repeatedly refreshes the clump age to `0`. As long as the farm is actively generating items, **no items will ever despawn**.
* **Idle Cleanup**: Once the farm stops producing items, the clump sits idle on the ground. The age timer ticks up uninhibited from $0$ to $6000$, safely cleaning up all uncollected items after 5 minutes of inactivity to prevent long-term world save bloat.

---

## 🛑 Pickup Delay Safety

To prevent players from instantly re-absorbing items they just dropped from their hand, Minecraft assigns a `pickupDelay` (typically 40 ticks = 2 seconds).

When items merge:

$$\text{pickupDelay}_{\text{merged}} = \max(\text{pickupDelay}_{\text{this}}, \text{pickupDelay}_{\text{other}})$$

This ensures that if a player deliberately throws an item on top of an existing clump, the clump will not instantly vacuum back into their inventory until the full pickup delay expires.

---

## 💻 Ground-Truth Java Source Implementation

From `ItemEntityMixin.java` in Minecraft 26.2:

```java
// During Complete or Partial Merging:
this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
```

---

## 🔗 Related Documentation
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
* [[Hopper Integration|Hopper-and-Automation-Integration]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
