<p align="center">
    <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.1+-brightgreen?style=for-the-badge" alt="Minecraft 26.1+">
</p>

# 📦 Item Clumps

**No Backports:** This mod targets **Minecraft 26.1+**. Older versions are unsupported.

> **Aggressive ground item optimization. Combines dropped items into mega-stacks.**

Tired of frame drops when mining huge ore veins, detonating large areas, or running high-efficiency mob farms? **Item Clumps** completely redesigns how dropped item entities behave in the world, combining them into lightweight, virtual mega-stacks to maximize both server performance and client-side frame rates.

Part of the **Instant Gratification Collection** — mods that refine the vanilla experience with modern standards.

---

## 🎥 Showcase Video

[![Item Clumps Showcase Video](https://img.youtube.com/vi/2e9tHTHidfo/maxresdefault.jpg)](https://youtu.be/2e9tHTHidfo)

*Click the image above to watch the mod showcase in action!*

---

## ✨ Features

* 🚀 **Infinite Stack Size Virtualization**: Minecraft normally limits ground items to a stack size of 64. When hundreds of items are dropped, the game has to process physics, collision checks, and rendering for each individual entity, causing severe lag. This mod bypasses the 64-stack limit, allowing identical ground items to merge into a single entity tracking a virtual count of up to **9,999 items**.
* 🏷️ **Real-Time Synced Holographic Labels**: When a clump's count exceeds a normal stack, it renders a floating, client-side holographic name tag (e.g., `Oak Log x450`). This label is synced dynamically using network packet listeners, ensuring that the count changes instantly on your screen when you pick up a portion of the stack.
* ⏱️ **Fair Despawn Timer Sync**: To prevent items from living forever, when two stacks merge, the resulting clump inherits the despawn age of the *oldest* item in the merge. When the vanilla 5-minute timer expires, the entire clump despawns, keeping your world clean and leak-free.
* 📦 **Smart Inventory Integration**: Walking over a clump smoothly transfers items to your inventory in max stack chunks. The mod dynamically calculates the exact amount of free space in your inventory and takes only what fits, keeping the remainder safely in the ground clump instead of deleting or de-syncing them.
* ⚙️ **Hopper Drip-Feeding**: Hoppers extract items from virtual clumps one by one at standard vanilla transfer speeds. This ensures your redstone automation, sorting systems, and item-elevator pipelines function exactly as they did in vanilla, keeping the game balanced.
* 📐 **Vanilla Physics Alignment**: To match vanilla merging rules and prevent glitches, clumping operates on a configurable horizontal block radius, but respects vertical space. Items will **not** merge if they are separated vertically by 1 block or more (e.g., items resting on top of a block won't merge with items below it).

---

## ⚙️ Configuration (Native Game Rules)

No messy config files. Item Clumps uses the **Native Minecraft Game Rules** system. All mod parameters are grouped into a dedicated **"Item Clumps"** category in the official UI.

> [!TIP]
> **Too many rules?** If the game rule screen feels cluttered, we highly recommend installing **[Collapsible Game Rules](https://modrinth.com/mod/collapsible-gamerules)**. It will automatically group the settings into clean, expandable folders!

### Registered Rules:
* `item_clumps:enable_clumping` (Default: `true`) - Toggles the clumping mechanic. When disabled, items behave exactly like vanilla.
* `item_clumps:max_clump_size` (Default: `9999`) - The maximum quantity of items a single clumped entity can contain before spawning a new stack.
* `item_clumps:render_labels` (Default: `true`) - Toggles the rendering of the floating holographic count label above virtual clumps.
* `item_clumps:merge_radius` (Default: `1`) - The horizontal block radius items will search to find matching items to merge.

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
| **Creator** | **Rifaditya** (Dasik) |
| **Collection** | Instant Gratification |
| **License** | GPLv3 |

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
