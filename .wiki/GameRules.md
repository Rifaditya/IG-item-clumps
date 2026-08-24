# Namespaced GameRules Reference (MC 26.2)

This document provides a complete reference for all namespaced GameRules registered by **Item Clumps** on **Minecraft 26.2**.

---

## 📊 GameRules Category Infobox

| Property | Value |
| :--- | :--- |
| **Category Identifier** | `item_clumps:item_clumps` |
| **Localized Title** | `"Item Clumps"` (`gamerule.category.item_clumps.item_clumps`) |
| **Registry Method** | `DynamicGameRuleManager.registerCategory(...)` (DasikLibrary API) |
| **Runtime Persistence** | Saved per world in `level.dat` (`GameRules` CompoundTag) |
| **Network Sidedness** | Server-Side Authority (Evaluated dynamically on server tick/mutation) |

---

## 📋 Complete GameRules Reference Table

| GameRule Identifier | Type | Default | Valid Bounds | Description & Gameplay Effect |
| :--- | :---: | :---: | :---: | :--- |
| `item_clumps:enable_clumping` | Boolean | `true` | `true` / `false` | Master toggle. When `true`, ground items merge beyond normal stack limits. When `false`, reverts to vanilla 64-item merging. |
| `item_clumps:max_clump_size` | Integer | `9999` | `64` to `2147483647` | Maximum total item count in a single entity. *(Note: Suppressed if `stack-size-adjuster` is installed).* |
| `item_clumps:render_labels` | Boolean | `true` | `true` / `false` | When `true`, displays a floating count label (e.g. `Stone x500`) above mega-stacks. |
| `item_clumps:merge_radius` | Integer | `1` | `1` to `10` | Horizontal search block radius for finding identical items to merge. |
| `item_clumps:label_min_count` | Integer | `-1` | `-1` to `2147483647` | Minimum count before label renders. `-1` uses vanilla max stack size ($> 64$ for stone, $> 16$ for pearls). |

---

## 💻 In-Game Chat Administration Examples

All GameRules are modified in real-time without restarting the server:

### 1. Adjusting Search Merge Radius
```text
/gamerule item_clumps:merge_radius 3
```

### 2. Setting Ultra-High Clump Capacity
```text
/gamerule item_clumps:max_clump_size 50000
```

### 3. Toggling Floating Count Labels
```text
/gamerule item_clumps:render_labels false
```

### 4. Customizing Minimum Label Threshold
```text
/gamerule item_clumps:label_min_count 100
```

---

## 🔗 Related Documentation
* [[Commands & Administration|Commands-and-Administration]]
* [[Configuration Suite|Configuration]]
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
