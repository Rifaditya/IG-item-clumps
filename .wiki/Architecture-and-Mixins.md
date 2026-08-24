# Architecture & Mixins Breakdown (MC 26.2)

This document details the internal package layout, class hierarchy, Mixin target breakdown, shadow members, and injection points for **Item Clumps** on **Minecraft 26.2**.

---

## 🌳 Package Hierarchy & Class Diagram

```
net.instantgratification.item_clumps
│
├── ItemClumpsFabric.java                   [Main Entrypoint / GameRules Registration]
│
├── config/
│   ├── ItemClumpsConfig.java               [Configuration POJO / ConfigHelper Persistence]
│   ├── ModMenuIntegration.java             [ModMenuApi Provider / Safe GUI Factory]
│   └── YaclScreenHelper.java               [YetAnotherConfigLib v3 Screen Factory]
│
├── mixin/
│   ├── HopperBlockEntityMixin.java         [Hopper Drip Extraction Mixin]
│   └── ItemEntityMixin.java                [Mega-Stack Aggregation & Smart Pickup Mixin]
│
└── util/
    └── ModVersionGuard.java                [Knot ClassLoader Runtime Safety Guard]
```

---

## 🛠️ Complete Mixin Targets & Injection Breakdown

### 1. `ItemEntityMixin.java`
* **Target Class**: `net.minecraft.world.entity.item.ItemEntity`
* **Superclass Extension**: `extends net.minecraft.world.entity.Entity`

| Member / Method | Annotation | Injection Point | Purpose |
| :--- | :---: | :---: | :--- |
| `getItem()` | `@Shadow` | N/A | Accesses the item stack. |
| `setItem(ItemStack)` | `@Shadow` | N/A | Mutates the item stack and updates tracker data. |
| `target` | `@Shadow` | N/A | UUID of the targeting player (for throw safety). |
| `pickupDelay` | `@Shadow` | N/A | Pickup cooldown timer ticks. |
| `age` | `@Shadow` | N/A | Despawn age timer ticks ($0\text{ to }6000$). |
| `setItem` | `@Inject` | `@At("TAIL")` | Triggers custom name tag update when stack count changes. |
| `isMergable` | `@Inject` | `@At("HEAD")`, `cancellable = true` | Overrides max stack check with `max_clump_size` GameRule. |
| `mergeWithNeighbours` | `@Redirect` | `INVOKE -> AABB.inflate(DDD)` | Zero-allocation expansion of horizontal search radius ($r \in [1, 10]$). |
| `tryToMerge` | `@Inject` | `@At("HEAD")`, `cancellable = true` | Executes 64-bit sum math, youngest age inheritance, and magnet check. |
| `playerTouch` | `@Inject` | `@At("HEAD")`, `cancellable = true` | Dispatches single-batch inventory intake in clean vanilla stacks. |

---

### 2. `HopperBlockEntityMixin.java`
* **Target Class**: `net.minecraft.world.level.block.entity.HopperBlockEntity`

| Method | Annotation | Injection Point | Purpose |
| :--- | :---: | :---: | :--- |
| `addItem(Container, ItemEntity)` | `@Inject` | `@At("HEAD")`, `cancellable = true` | Extracts 1-item slices from mega-clumps ($2.5\text{ items/s}$) into hopper containers. |

---

## 📄 Mixin Configuration (`item-clumps.mixins.json`)

```json
{
    "required": true,
    "package": "net.instantgratification.item_clumps.mixin",
    "compatibilityLevel": "JAVA_25",
    "refmap": "item-clumps-refmap.json",
    "mixins": [
        "HopperBlockEntityMixin",
        "ItemEntityMixin"
    ],
    "injectors": {
        "defaultRequire": 1
    }
}
```

---

## 🔗 Related Documentation
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
* [[Hopper Integration|Hopper-and-Automation-Integration]]
* [[Developer Setup|Developer-Setup-and-Building]]
