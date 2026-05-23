<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.1.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.1.2+">
</p>

# 📦 Item Clumps: The "Instant Gratification" Update (Build 11)

**No Backports:** I will **NOT** backport this mod to older Minecraft versions (1.21, 1.20, etc.). Please do not ask.

In vanilla Minecraft, dropped items merge, but they strictly cap at a maximum stack size of 64. If a high-efficiency mob farm, automated quarry, or massive TNT explosion drops thousands of items, the game is forced to tick and render hundreds of individual ground entities. This leads to heavy client-side frame drops, rendering lag, and severe server TPS decay.

**Item Clumps** changes this foundation by breaking the 64-item stack limit for ground entities. It aggressively condenses identical items into single, lightweight virtual mega-stacks of up to 9,999 items by default. No more frame-rate slide-shows or server lag—just smooth, instant item pickup and high-efficiency performance.

---

## 🎥 Showcase Video

<p align="center">
    <a href="https://youtu.be/2e9tHTHidfo"><img src="https://img.youtube.com/vi/2e9tHTHidfo/maxresdefault.jpg" alt="Item Clumps Showcase Video" width="560"></a>
</p>

*Click the image above to watch the mod showcase in action! (CurseForge does not support embeds)*

---

## ✨ Features

### 🚀 Infinite Stack Size Virtualization
Bypasses the 64-item ground stack cap. Identical items merge into single entities containing up to **9,999 items** by default (configurable up to the full 32-bit integer limit of **2,147,483,647** both in the GameRules screen and via commands).

> [!NOTE]
> **MegaCount Strategy**: Setting an ItemStack count above 99 crashes vanilla serialization. To prevent this, Item Clumps keeps the physical stack count at 1, storing the true quantity in custom Entity Tracked Data and serialization NBT tags.

### 🏷️ Real-Time Synced Count Tags
Renders a floating count tag (e.g., `Oak Log x450`) above clumps when the count exceeds a normal stack. Tags update instantly via dynamic server-to-client network synchronization.

### ⏱️ Vanilla Despawn Timer Rules
To match vanilla merging logic, when two stacks merge, the resulting clump inherits the age of the **youngest** item in the merge (taking the smaller age value, as age ticks upwards). This extends/resets the despawn window for the combined clump exactly like vanilla, ensuring you don't lose items prematurely.

### 📦 Smart Inventory Integration
Walking over a clump smoothly transfers items to your inventory in max stack chunks. The mod dynamically calculates the exact amount of free space in your inventory and takes only what fits, keeping the remainder safely in the ground clump instead of deleting or de-syncing them.

### ⚙️ Hopper Drip-Feeding
Hoppers extract items from virtual clumps one by one at standard vanilla transfer speeds. This ensures your redstone automation, sorting systems, and item-elevator pipelines function exactly as they did in vanilla, keeping the game balanced.

### 📐 Vanilla Physics Alignment
To match vanilla merging rules and prevent glitches, clumping operates on a configurable horizontal block radius, but respects vertical space. Items will **not** merge if they are separated vertically by 1 block or more (e.g., items resting on top of a block won't merge with items below it).

---

## ⚙️ Config

The mod works out of the box with zero setup. All parameters are managed in-game using the **Native Minecraft Game Rules** system.

* **In-Game**: Use `/gamerule item_clumps:` for core settings.
  * `item_clumps:enable_clumping` (Default: `true`) - Toggles the clumping mechanic. When disabled, items behave exactly like vanilla.
  * `item_clumps:max_clump_size` (Default: `9999`) - The maximum quantity of items a single clumped entity can contain.
  * `item_clumps:render_labels` (Default: `true`) - Toggles the rendering of the floating custom count tag above virtual clumps.
  * `item_clumps:merge_radius` (Default: `1`) - The horizontal block radius items will search to find matching items to merge.

![Item Clumps Game Rules Screenshot](https://raw.githubusercontent.com/Rifaditya/IG-item-clumps/main/Doc/Media/Gamerule%20Screen.png)

> [!IMPORTANT]
> **Recommended Mod**: Since this mod adds several GameRules, we highly recommend using **[Collapsible Game Rules](https://www.curseforge.com/minecraft/mc-mods/collapsible-gamerules)** to keep your settings menu clean and organized.

---

## 📦 Installation & Environment

### 🖥️ Environment Support
* [x] **Client and server**: Has some functionality on both the client and server.
  * [x] **Required on both** (Mismatched installations will result in connection desync/errors due to the custom data tracker registrations)

### 📥 Install Instructions
1. Install **[Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)**.
2. Download `item-clumps-1.0.0+build.11.jar` and place it in your `mods` folder.

---

## 🧩 Compatibility

| Feature | Fabric (26.1+) |
| :--- | :---: |
| Singleplayer | ✅ |
| Multiplayer (LAN/Server) | ✅ |
| Empty Dimensions | ✅ |

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider fueling the next update!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)

> [!NOTE]
> **Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Creator** | Rifaditya (Dasik) |
| **Collection** | Instant Gratification |
| **License** | GNU GPLv3 |

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
