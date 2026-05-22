# 📦 Item Clumps (Instant Gratification Mod)

[![Minecraft Version](https://img.shields.io/badge/Minecraft-26.1%20Snapshot%2011%2B-blue?style=for-the-badge&logo=minecraft&logoColor=white)](https://www.minecraft.net/)
[![Fabric API](https://img.shields.io/badge/Fabric-Loader-yellow?style=for-the-badge&logo=fabric&logoColor=white)](https://fabricmc.net/)
[![License](https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge)](LICENSE)
[![GitHub Repository](https://img.shields.io/badge/GitHub-Repository-black?style=for-the-badge&logo=github)](https://github.com/Rifaditya/IG-item-clumps)

**Item Clumps** is an aggressive performance optimization mod designed to solve entity lag caused by massive item drops (from mob farms, large explosions, or quarries). It removes the vanilla 64-item stack limit for ground entities, allowing thousands of identical items to merge into a single entity tracking a virtual count.

> [!IMPORTANT]
> **Environment Requirement**: This mod is required on **both the client and the server**. Mismatched installations will result in connection errors or desynchronization due to the custom data tracker registrations.

---

## 🎥 Showcase Video

[![Item Clumps Showcase Video](https://img.youtube.com/vi/2e9tHTHidfo/maxresdefault.jpg)](https://youtu.be/2e9tHTHidfo)

*Click the image above to watch the mod showcase in action!*

---

## ✨ Features

* 🚀 **Infinite Stack Size Virtualization**: Ground items merge into a single entity tracking a virtual count of up to **9,999 items** by default (configurable up to **999,999,999** in the GameRules screen due to GUI limits, or the full 32-bit integer limit of **2,147,483,647** via commands).
* 🏷️ **Real-Time Synced Count Tags**: Floating client-side custom name tags (e.g., `Oak Log x450`) appear when clump sizes exceed a normal stack. Synced dynamically via packet listeners to update instantly.
* ⏱️ **Vanilla Despawn Timer Rules**: Matches vanilla merging logic by inheriting the age of the **youngest** item (taking the smaller age value, since age ticks upwards). This extends the despawn window safely.
* 📦 **Smart Inventory Integration**: Transfers items to your inventory in max stack chunks depending on available space. Keeps the remainder safely on the ground instead of dropping or losing it.
* ⚙️ **Hopper Drip-Feeding**: Hoppers extract items from virtual clumps one by one at standard vanilla transfer speeds, keeping redstone and sorting automation functional.
* 📐 **Vanilla Physics Alignment**: Respects vertical space. Items will **not** merge if they are separated vertically by 1 block or more.

---

## ⚙️ Configuration (Native Game Rules)

No config files are needed. All settings are native GameRules in the **"Item Clumps"** category:

* `item_clumps:enable_clumping` (Default: `true`) - Toggles the clumping mechanic.
* `item_clumps:max_clump_size` (Default: `9999`) - The maximum quantity of items a single clump can contain.
* `item_clumps:render_labels` (Default: `true`) - Toggles rendering of floating custom count tags.
* `item_clumps:merge_radius` (Default: `1`) - The horizontal block radius searched for merging.

![Item Clumps Game Rules Screenshot](Doc/Media/Gamerule%20Screen.png)

---

## 📖 Navigation & Documentation

* 🎮 **[User & Admin Guide](Doc/Players/user_guide.md)**: Configuration reference, gamerules, and commands.
* 🛠️ **[System Architecture](Doc/Develop/Architecture/architecture.md)**: Network protocols, sequence diagrams, and mixin targets.
* 💡 **[Original Design Concept](Doc/Concept/concept_item_clumps.md)**: Philosophy, problem statements, and edge cases.
* 📜 **[Changelog History](Doc/Develop/Changelogs/History.md)**: Detailed version changes.

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider supporting development!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)
