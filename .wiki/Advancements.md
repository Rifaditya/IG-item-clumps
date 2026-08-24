# Advancements & Statistics Tracking (MC 26.1.2)

This document details advancement integration, statistics triggers, datapack compatibility, and Absence Policy verification for **Item Clumps** on **Minecraft 26.1.2**.

---

## 📊 Feature Infobox

| Property | Value |
| :--- | :--- |
| **Custom Advancements** | **None** (Absence Policy verified: relies on vanilla Minecraft advancements) |
| **Statistics Engine** | `net.minecraft.stats.Stats.ITEM_PICKED_UP` |
| **Trigger Invocation** | `player.awardStat(Stats.ITEM_PICKED_UP.get(item), addedCount)` |
| **Datapack Compatibility** | 100% compatible with criteria triggers (`minecraft:inventory_changed`) |

---

## 📜 Absence Policy & Vanilla Reliance Notice

> 📌 **Absence Policy Verification**: Item Clumps does NOT bundle custom advancement trees (no `data/item_clumps/advancement/*.json` files exist in the mod jar). As a pure performance and quality-of-life optimization mod, it relies entirely on vanilla Minecraft's advancement and statistics infrastructure.

---

## 📈 Native Statistics & Criterion Dispatch

When a player walks over a mega-clump containing hundreds of items, the mod distributes items in chunks and dispatches vanilla statistic awards:

```java
// ItemEntityMixin.java (MC 26.1.2)
player.take(this, added);
player.awardStat(net.minecraft.stats.Stats.ITEM_PICKED_UP.get(baseItem.getItem()), added);
```

---

## 🔗 Related Documentation
* [[Smart Pickup System|Smart-Pickup-and-Inventory-Distribution]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
