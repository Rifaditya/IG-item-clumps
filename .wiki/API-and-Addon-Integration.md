# API & Addon Integration (MC 26.2)

This document details third-party mod integration hooks, DasikLibrary API interactions, and programmatic clump queries for **Item Clumps** on **Minecraft 26.2**.

---

## 📊 Integration Infobox

| Property | Value |
| :--- | :--- |
| **API Architecture** | Native Vanilla `ItemEntity` and `ItemStack` APIs |
| **Data Storage Protocol** | Direct `ItemStack.getCount()` (`VarInt` / 32-bit Integer) |
| **GameRule API** | `DynamicGameRuleManager` (DasikLibrary API) |
| **Duck-Typing Interfaces** | Compatible with custom magnet / item entity interfaces |

---

## 🔌 Programmatic Clump Queries for Addon Developers

Because Item Clumps stores virtual mega-stack counts directly inside the standard Minecraft `ItemStack`, addon developers do not need custom API facades to read or manipulate clump sizes:

### 1. Reading Clump Count
```java
// Standard ItemEntity instance
ItemEntity entity = ...;

// Native vanilla getter returns true mega-count (e.g. 5000)
int trueCount = entity.getItem().getCount();
```

### 2. Checking Mergability
```java
// Queries Item Clumps customIsMergable logic
boolean canMerge = entity.isMergable();
```

### 3. Modifying Clump Counts Safely
```java
// When mutating item entity counts, always update the stack object:
ItemStack stack = entity.getItem().copyWithCount(newCount);
entity.setItem(stack); // Triggers custom name tag update automatically!
```

---

## 🧲 Magnet & Flight Duck-Typing Hook

If you are developing an item magnet, telekinesis, or vacuum mod, you can prevent Item Clumps from prematurely merging items while in-flight by adding either of the following duck-typed methods to your `ItemEntity` mixin:

```java
public boolean ig_magnet$isMagnetized() {
    return this.isFlyingTowardsPlayer;
}

// Or legacy alternative:
public boolean ig$isMagnetized() {
    return this.isFlyingTowardsPlayer;
}
```
Item Clumps automatically invokes this method via reflection on merge ticks.

---

## 🔗 Related Documentation
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Developer Setup|Developer-Setup-and-Building]]
* [[Mod Compatibility|Compatibility-and-Integrations]]
