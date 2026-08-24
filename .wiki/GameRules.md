# Namespaced GameRules Reference (MC 26.1.2)

This document provides a complete reference for all namespaced GameRules registered by **Item Clumps** on **Minecraft 26.1.2**.

---

## 📊 GameRules Category Infobox

| Property | Value |
| :--- | :--- |
| **Category Identifier** | `item_clumps:item_clumps` |
| **Localized Title** | `"Item Clumps"` (`gamerule.category.item_clumps.item_clumps`) |
| **Registry Method** | `DynamicGameRuleManager.registerCategory(...)` (DasikLibrary API) |
| **Runtime Persistence** | Saved per world in `level.dat` (`GameRules` CompoundTag) |

---

## 📋 Complete GameRules Reference Table

| GameRule Identifier | Type | Default | Valid Bounds | Description & Gameplay Effect |
| :--- | :---: | :---: | :---: | :--- |
| `item_clumps:enable_clumping` | Boolean | `true` | `true` / `false` | Master toggle. When `true`, ground items merge beyond normal stack limits. |
| `item_clumps:max_clump_size` | Integer | `9999` | `64` to `2147483647` | Maximum total item count in a single entity. |
| `item_clumps:render_labels` | Boolean | `true` | `true` / `false` | When `true`, displays a floating count label (e.g. `Stone x500`) above mega-stacks. |
| `item_clumps:merge_radius` | Integer | `1` | `1` to `10` | Horizontal search block radius for finding identical items to merge. |

---

## 💻 In-Game Chat Administration Examples

### 1. Adjusting Search Merge Radius
```text
/gamerule item_clumps:merge_radius 3
```

### 2. Setting Clump Capacity
```text
/gamerule item_clumps:max_clump_size 50000
```

### 3. Toggling Floating Count Labels
```text
/gamerule item_clumps:render_labels false
```

---

## 🔗 Related Documentation
* [[Commands & Administration|Commands-and-Administration]]
* [[Configuration Suite|Configuration]]
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
