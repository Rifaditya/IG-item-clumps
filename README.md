# 📦 Item Clumps (Server-Side Only)

[![Minecraft Version](https://img.shields.io/badge/Minecraft-26.1%20Snapshot%2011%2B-blue?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![Fabric API](https://img.shields.io/badge/Fabric-Loader-yellow?style=for-the-badge&logo=fabric&logoColor=white)](https://fabricmc.net/)
[![License](https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge)](LICENSE)

**Item Clumps** is an aggressive performance optimization mod designed to solve entity lag caused by massive item drops. It allows thousands of identical ground items to merge into a single entity tracking a virtual count directly inside the standard item stack count.

> [!NOTE]
> **Environment Requirement**: This mod is **server-side only** (runs on the logical server). It is fully compatible with vanilla clients—players do not need to install it to connect to your server. It also runs automatically on the integrated server when playing in singleplayer!

---

## 🎥 Showcase Video

[![Item Clumps Showcase Video](https://img.youtube.com/vi/2e9tHTHidfo/maxresdefault.jpg)](https://youtu.be/2e9tHTHidfo)

*Click the image above to watch the mod showcase in action!*

---

## ✨ Features

* 🚀 **Server-Side Stack Size Virtualization**: Bypasses the 64-item ground stack cap. Identical items merge into single entities containing up to **9,999 items** by default (configurable up the full 32-bit integer limit of **2,147,483,647** both in the GameRules screen and via commands).
* 🏷️ **Vanilla-Compatible Count Tags**: Uses vanilla's built-in entity custom names (`setCustomName`) to render floating text count indicators above clumps larger than a normal stack (e.g. `Oak Log x450`). No client-side mod required!
* ⏱️ **Vanilla Despawn Timer Rules**: Inherits the age of the youngest item in a merge to protect newly dropped items, matching vanilla's despawn window logic.
* 📦 **Smart Inventory Intake**: Transfers items to the player's inventory in clean vanilla stack sizes according to available space, updating the remainder on the ground using standard server-side packets.
* ⚙️ **Hopper Drip-Feeding**: Restricts hopper intake to exactly 1 item per pull from clumps, preserving vanilla sorting and automation balance.
* 📐 **Vanilla Physics Alignment**: Respects vertical spacing. Items will not merge if separated vertically by 1 block or more.

---

## ⚙️ Configuration (Native Game Rules)

No config files are needed. All settings are native GameRules in the **"Item Clumps"** category:

* `item_clumps:enable_clumping` (Default: `true`) - Toggles the clumping mechanic.
* `item_clumps:max_clump_size` (Default: `9999`) - The maximum quantity of items a single clump can contain.
* `item_clumps:render_labels` (Default: `true`) - Toggles rendering of floating custom name tags.
* `item_clumps:merge_radius` (Default: `1`) - The horizontal block radius searched for merging.

---

## 📖 Navigation & Documentation

* 🎮 **[User & Admin Guide](Doc/Players/user_guide.md)**: Configuration reference, gamerules, and commands.
* 🛠_ **[System Architecture](Doc/Develop/Architecture/architecture.md)**: Server-side mechanics and mixin targets.
* 💡 **[Original Design Concept](Doc/Concept/concept_item_clumps.md)**: Philosophy, problem statements, and edge cases.
* 📜 **[Changelog History](Doc/Develop/Changelogs/History.md)**: Detailed version changes.

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider supporting development!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)
