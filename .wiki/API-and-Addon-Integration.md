# API & Addon Integration (MC 26.1.2)

This document details third-party mod integration hooks, DasikLibrary API interactions, and programmatic clump queries for **Item Clumps** on **Minecraft 26.1.2**.

---

## 📊 Integration Infobox

| Property | Value |
| :--- | :--- |
| **API Architecture** | Native Vanilla `ItemEntity` and `ItemStack` APIs |
| **Data Storage Protocol** | Direct `ItemStack.getCount()` (`VarInt` / 32-bit Integer) |
| **GameRule API** | `DynamicGameRuleManager` (DasikLibrary API) |

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
ItemStack stack = entity.getItem().copy();
stack.setCount(newCount);
entity.setItem(stack); // Triggers custom name tag update automatically!
```

---

## 🔗 Related Documentation
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[Developer Setup|Developer-Setup-and-Building]]
