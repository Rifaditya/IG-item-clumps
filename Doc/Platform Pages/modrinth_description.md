<p align="center">
    <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 📦 Item Clumps (Server Only): The "Instant Gratification" Update

**Active Version Policy:** I build **1 JAR for 1 Version**. I only update and maintain the latest active Minecraft version (e.g. when 26.3 is released, 26.2 is retired). No backports or legacy version maintenance. Please do not ask.

In vanilla Minecraft, dropped items merge, but they strictly cap at a maximum stack size of 64. If a high-efficiency mob farm, automated quarry, or massive TNT explosion drops thousands of items, the game is forced to tick and render hundreds of individual ground entities. This leads to heavy client-side frame drops, rendering lag, and severe server TPS decay.

**Item Clumps (Server Only)** changes this foundation by breaking the 64-item stack limit for ground entities entirely server-side. It aggressively condenses identical items into single, lightweight virtual mega-stacks of up to 9,999 items by default. Vanilla clients can connect to servers running this mod without having to install it!

---

## 🎥 Showcase Video

<iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/2e9tHTHidfo" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

*Click the player above to watch the mod showcase in action!*

---

## ✨ Features

### 🚀 Server-Side Stack Size Virtualization
Bypasses the 64-item ground stack cap. Identical items merge into single entities containing up to **9,999 items** by default (configurable up the full 32-bit integer limit of **2,147,483,647** both in the GameRules screen and via commands).

> [!NOTE]
> **MegaCount Strategy**: In this server-side only version, the true virtual count is stored directly in the standard vanilla `ItemStack` count. Because Minecraft 26.x serializes stack counts using standard VarInts/integers rather than bytes, vanilla clients natively support, sync, and display these larger counts on item entities.

### 🏷️ Vanilla-Compatible Count Tags
Renders a custom name tag (e.g., `Oak Log x450`) above clumps when the count exceeds a normal stack. Uses vanilla's native custom name tags (`setCustomName` / `setCustomNameVisible`) on the server so that vanilla clients display the count tag without needing any client-side mod.

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


> [!IMPORTANT]
> **Config vs. In-Game GameRules:**
> The global configuration file only defines **default values for new worlds** at creation time.
> If you have **already created/opened a world**, changing the config file will have no effect. You must change the settings in-game using the **Edit Game Rules** UI screen or the /gamerule command.
The mod works out of the box with zero setup. All parameters are managed in-game using the **Native Minecraft Game Rules** system.

* **In-Game**: Use `/gamerule item_clumps:` for core settings.
  * `item_clumps:enable_clumping` (Default: `true`) - Toggles the clumping mechanic. When disabled, items behave exactly like vanilla.
  * `item_clumps:max_clump_size` (Default: `9999`) - The maximum quantity of items a single clumped entity can contain.
  * `item_clumps:render_labels` (Default: `true`) - Toggles the rendering of the floating custom count tag above virtual clumps.
  * `item_clumps:merge_radius` (Default: `1`) - The horizontal block radius items will search to find matching items to merge.

![Item Clumps Game Rules Screenshot](https://raw.githubusercontent.com/Rifaditya/IG-item-clumps/main/Doc/Media/Gamerule%20Screen.png)

> [!IMPORTANT]
> **Recommended Mod**: Since this mod adds several GameRules, we highly recommend using **[Collapsible Game Rules](https://modrinth.com/mod/collapsible-gamerules)** to keep your settings menu clean and organized.

---

## 📦 Installation & Environment

### 🖥️ Environment Support
* [x] **Server-side only**: All functionality is done server-side and is compatible with vanilla clients.
  * [x] Works in singleplayer too

### 📥 Install Instructions
1. Install **[Fabric API](https://modrinth.com/mod/fabric-api)**.
2. Download `item-clumps-*.jar` and place it in your `mods` folder.

---

## 🧩 Compatibility

| Feature | Fabric (26.2+) |
| :--- | :---: |
| Singleplayer | ✅ |
| Multiplayer (LAN/Server) | ✅ |
| Empty Dimensions | ✅ |

### 🎮 Version Compatibility & Support
* **Minecraft 26.2+**: Current public release — Active & Supported.
* **Minecraft 26.1.2**: **Discontinued**

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider fueling the next update!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)

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
