# User Guide - Item Clumping

This guide covers everything you need to know about item clumping, config rules, and how to manage clumps as a player or administrator.

---

## 📖 Overview

By default, Minecraft merges items of the same type in the world only if the stack size is below its max stack size (typically 64). When many items are dropped (e.g., from mob farms, quarries, or explosions), hundreds of individual item entities float in the world, causing severe client-side and server-side lag.

> [!IMPORTANT]
> **Environment Requirement**: This mod is required on **both the client and the server**. Because the mod registers custom network synced data trackers (mismatching trackers will cause desynchronization or join connection issues), clients will not be able to join servers without this mod installed.

This mod solves this issue by allowing item entities to merge **infinitely** (up to a configurable limit) into a single entity. The single entity renders as one item, but tracks a virtual count (e.g., `x500`). 

---

## ⚙️ Configuration & GameRules

Administrators can control all clumping behavior in real-time using in-game GameRules.

| GameRule | Default Value | Description |
|---|---|---|
| `item_clumps:enable_clumping` | `true` | When true, item clumping is enabled. When false, the mod reverts to vanilla merging behavior. |
| `item_clumps:max_clump_size` | `9999` | The maximum virtual count of items allowed in a single clump. |
| `item_clumps:render_labels` | `true` | When true, renders a holographic count label above item clumps. |
| `item_clumps:merge_radius` | `1` | The horizontal block radius items will search to merge with identical items. |

To change a value, use the standard command:
```text
/gamerule <rule_name> <value>
```
For example, to change the merge radius:
```text
/gamerule item_clumps:merge_radius 2
```
To hide holographic labels:
```text
/gamerule item_clumps:render_labels false
```

---

## 🛠️ Mechanics in Detail

### 1. The 5-Minute Despawn Timer
* **Concept**: In vanilla Minecraft, items despawn after 5 minutes (6000 ticks) of existence.
* **Clump Behavior**: When items merge, the mod inherits the age of the **youngest** item (the smallest age value, since Minecraft's age ticks upwards from 0 to 6000). 
* **Rationale**: This extends the lifetime of the clump to match the newest item, exactly mimicking vanilla merging behavior. This guarantees you do not lose newly dropped items prematurely when they merge into an older stack.

### 2. Player Pickups
* **Smart Intake**: When you walk over a clump containing hundreds of items, the mod calculates how much space you have in your inventory.
* **Auto-Stacking**: It automatically gives you items in full stacks (e.g., 64 at a time) or smaller chunks depending on your inventory space. 
* **Partial Pickups**: If your inventory fills up halfway through, you will pick up only what fits, and the remaining count will stay in the clump on the ground.
* **Perfect Stacking (NBT Clean)**: Picked-up items are completely clean vanilla items. The mod does NOT write any custom NBT, Data Components, or custom names to the items, ensuring they stack perfectly with normal items in your inventory and chests.

### 3. Hopper & Automation Integration
* **Drip Extraction**: Hoppers extract items from clumps one by one, matching the speed of vanilla hopper intake. A clump containing 1000 items will sit on top of a hopper and be absorbed gradually without clogging or bypassing normal container speeds.

### 4. Visuals (Name Tags)
* **Count Indicators**: To help you see how many items are on the ground without opening your inventory, a custom name tag (e.g., `Stone x450`) will appear above any clump whose count exceeds its maximum native stack size (typically 64).
* **Client-Side Only Rendering**: This label is rendered dynamically on the client utilizing `ItemEntityRenderState` and does **NOT** write to the item's display name or components. The item entity remains a standard item.
* **Toggleable Display**: Renders dynamically on the client, and can be completely toggled off or back on via `/gamerule item_clumps:render_labels`.
* **Render Range**: Name tags are only displayed when you are within 64 blocks of the clump to minimize visual clutter.
