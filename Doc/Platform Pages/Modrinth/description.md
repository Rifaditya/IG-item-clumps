<p align="center">
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://modrinth.com/mod/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-8A2BE2?style=for-the-badge" alt="Requires Dasik Library"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 📦 Item Clumps

> **"Stop Ground Item Lag. Merge Endless Drops into Ultra-Smooth Clumps."**

> [!NOTE]
> **1 Jar 1 Version Policy:** I build **1 dedicated JAR for each Minecraft version** (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.
> <br><br>
> **Dependency Requirement:** For modern Minecraft 26.x releases (26.1.2, 26.2, 26.3+), this mod requires both **Fabric API** and **Dasik Library** (`v1.7.4+`).

In vanilla Minecraft, dropped items merge, but they strictly cap out at a standard stack size of 64. When an automated mob farm, quarry, tree harvester, or massive TNT explosion spills thousands of items onto the ground, your game is forced to tick, track, and render dozens—or even hundreds—of individual 3D item entities. The result? Severe client-side frame stuttering, rendering bottlenecks, and catastrophic server TPS drops.

**Item Clumps** shatters the 64-item ground entity barrier server-side. It aggressively merges identical dropped items into single, ultra-lightweight virtual mega-stacks of up to **9,999 items by default** (or up to **2,147,483,647**). Floating holographic count labels render cleanly above clumps, hoppers drip-feed items smoothly without jamming, and vanilla clients can connect to servers without installing any client mod!

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## 🎬 Video Showcase

<p align="center">
  <strong>🎬 Video Showcase: Item Clumps in Action</strong><br>
  <em>Click the video thumbnail or button below to watch the live demonstration on YouTube:</em><br><br>
  <a href="https://youtu.be/2e9tHTHidfo" target="_blank" rel="noopener">
    <img src="https://img.youtube.com/vi/2e9tHTHidfo/maxresdefault.jpg" alt="▶️ Click to Watch Video Showcase on YouTube" width="85%">
  </a><br><br>
  <a href="https://youtu.be/2e9tHTHidfo" target="_blank" rel="noopener">
    <img src="https://img.shields.io/badge/▶️_Watch_Video-Play_on_YouTube-FF0000?style=for-the-badge&logo=youtube&logoColor=white" alt="▶️ Play Video on YouTube">
  </a>
</p>

---

## ✨ Features

### 🚀 Server-Side Stack Aggregation & Virtualization
Bypasses the vanilla 64-item ground entity ceiling entirely. Identical dropped items (sharing matching item types, DataComponents, and targeting) merge into single unified entities containing up to **9,999 items** by default, fully configurable up to **2,147,483,647** (`Integer.MAX_VALUE`).

> [!NOTE]
> **Virtual Count Storage:** The full virtual item count is stored directly in the standard vanilla `ItemStack` count. Because modern Minecraft serializes stack counts using dynamic VarInts rather than legacy bytes, vanilla clients natively receive, synchronize, and display these counts on ground entities.

### 🏷️ Dynamic Holographic Count Labels
Renders a custom floating nameplate (e.g. `Cobblestone x1450` or `Rotten Flesh x420`) above clumps whenever the entity count exceeds a normal stack. Uses Minecraft's native entity nametag rendering (`setCustomName` and `setCustomNameVisible`) on the server so that vanilla clients see the label without needing any client-side mod installed.

### ⏱️ Vanilla Despawn Timer Preservation
To protect your hard-earned resources, merged clumps inherit the age of the **youngest** constituent item in the merge (`Math.min(this.age, other.age)`). This ensures combining newly dropped items with older items never causes premature despawning.

### 📦 Smart Inventory Transfer & Chunked Pickups
Walking over a massive clump never wastes or deletes items. The mod calculates your player inventory's free capacity and transfers only what you can hold in full stack chunks, leaving the remaining items safely on the ground in a smaller clump with immediate visual count updates.

### ⚙️ Hopper Drip-Feed Protection
Hoppers under an Item Clump extract exactly 1 item per normal vanilla transfer cycle via `HopperBlockEntityMixin`, decrementing the clump smoothly. Your sorting systems, storage silos, and redstone item elevators continue to function at standard vanilla transfer cadences without stalling or swallowing full mega-stacks.

### 📐 Horizontal Search Radius & Vertical Layer Guard
Merges identical items within a configurable horizontal radius (`item_clumps:merge_radius`, default: `1` block). Crucially, the merging algorithm enforces a vertical boundary: items resting 1 block above or below will not merge through ceilings, floors, or platforms.

### 🧲 Companion Mod Synergy (Stack Size Adjuster & Magnet)
- **Stack Size Adjuster Integration**: When **Stack Size Adjuster** is present, Item Clumps automatically defers ground entity caps to your custom configured stack sizes, cleanly hiding the redundant `max_clump_size` GameRule from the settings menu.
- **Magnet Flight Compatibility**: Built-in reflection hooks detect items actively traveling through the air towards players via **Magnet**, temporarily exempting them from clumping until flight completes for smooth, fluid motion trajectories.

### 🧩 Compatibility & HUD Integration
- **100% Server-Side Compatible**: Runs entirely on the server. Vanilla clients can join modded dedicated servers without downloading anything.
- **YetAnotherConfigLib (YACL) & ModMenu**: Optional graphical configuration screen in singleplayer to easily customize world defaults.
- **Zero NBT Pollution**: In-memory damage and entity interception guarantees your world saves remain 100% vanilla safe.

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/IG-item-clumps/main/Doc/Media/Gamerule%20Screen.png" alt="Item Clumps Native Game Rules Menu" width="85%"><br>
  <em>Native in-game Edit Game Rules menu showing the dedicated Item Clumps category</em>
</p>

---

## 📊 Quick Reference & Mechanics Matrix

| Gameplay Aspect | Vanilla Minecraft | Item Clumps (Modern 26.2+) |
| :--- | :---: | :---: |
| **Max Ground Stack Size** | 64 items (16 for pearls/eggs, 1 for tools) | **9,999 items** (Configurable up to 2.14 Billion) |
| **Entity Count for 5,000 Items** | 79 separate 3D entity models | **1 single unified entity** |
| **Frame Rate & Tick Impact** | Severe stuttering, FPS drops, TPS decay | **Smooth 60 FPS**, zero entity tick lag |
| **Holographic Count Label** | ❌ None (Indistinguishable pile) | ✅ **Live 3D nametag** (`Item Name xCount`) |
| **Despawn Timer Logic** | Merges reset to oldest item age | ✅ **Inherits youngest age** (Maximum lifespan) |
| **Hopper Extraction** | 1 item pulled per cycle | ✅ **Safe 1-item drip feed** (No redstone breakage) |
| **Magnet Flight Behavior** | Glitchy mid-air merging | ✅ **Aerodynamic flight lock** (No sudden snapping) |
| **Client Requirement** | Required on client | ✅ **100% Server-Side** (Vanilla clients supported) |

---

## 🚀 In-Game Commands & Quick Start

Adjust ground clumping rules live in your active world without restarting using standard Minecraft `/gamerule` commands:

```text
/gamerule item_clumps:enable_clumping true
/gamerule item_clumps:max_clump_size 9999
/gamerule item_clumps:render_labels true
/gamerule item_clumps:label_min_count -1
/gamerule item_clumps:merge_radius 1
```

All modifications made via `/gamerule` take effect immediately and synchronize across all connected players.

---

## ⚙️ Configuration (Native GameRules)

> [!IMPORTANT]
> **💡 Config vs. In-Game GameRules:** The global configuration file (`config/item_clumps.json`) only defines default values for newly created worlds. In existing worlds, change settings in-game via the **Edit Game Rules** UI screen or the `/gamerule` command.

| GameRule Name | Type | Default | Valid Range | Description |
| :--- | :---: | :---: | :---: | :--- |
| `item_clumps:enable_clumping` | `Boolean` | `true` | `true / false` | Toggles the item clumping mechanic on or off. When disabled, vanilla merging applies. |
| `item_clumps:max_clump_size` | `Integer` | `9999` | `64` to `2,147,483,647` | Maximum number of items a single clump can hold. *(Hidden when Stack Size Adjuster is loaded)*. |
| `item_clumps:render_labels` | `Boolean` | `true` | `true / false` | Renders a 3D holographic count label above clumps larger than a normal stack. |
| `item_clumps:label_min_count` | `Integer` | `-1` | `-1` to `2,147,483,647` | Minimum count required before displaying holographic label. Set to `-1` to use vanilla max stack limit. |
| `item_clumps:merge_radius` | `Integer` | `1` | `1` to `10` | Horizontal block radius items will search to find and merge with identical ground items. |

---

## 📖 In-Depth How-To & Operational Playbook

### 1. Drop-In Setup & Baseline Initialization
1. Place `item-clumps-*.jar` along with **Fabric API** and **Dasik Library** into your `mods` folder.
2. Launch Minecraft. The mod will automatically generate `config/item_clumps.json` populated with recommended high-performance defaults.

### 2. Live In-Game Tuning vs. Global Template
- **For New Worlds**: Configure your desired settings in `config/item_clumps.json` or via ModMenu + YACL on the title screen. New worlds copy these values into world GameRules upon creation.
- **For Existing Worlds**: Open the pause menu &rarr; **Edit Game Rules** &rarr; scroll to the **Item Clumps** section, or use `/gamerule item_clumps:<rule> <value>` in chat.

### 3. Industrial Farm Optimization (Mob Farms & Quarries)
- For high-volume automated mob grinders or quarry tunnels, set `item_clumps:merge_radius 2` or `3`. This allows items dropped across multiple hopper channels or water streams to cluster into a single central entity.
- If you notice holographic labels causing visual clutter in farm collection pits, raise `item_clumps:label_min_count` to `256` or `1000` so only massive surplus clumps display floating text.

### 4. Storage & Hopper Sorting Safeguards
- Item Clumps does not require custom collection filters. Standard hopper sorters and item elevators will ingest items smoothly 1 by 1.
- Because clumps shrink progressively as hoppers pull from them, the floating label updates in real time to show the remaining quantity.

### 5. Troubleshooting & Crash Prevention
- **Classloader Guard**: Item Clumps contains Knot ClassLoader startup checks to ensure compatibility across Minecraft versions.
- **Overflow Prevention**: Clump sum calculations utilize 64-bit arithmetic to ensure total item counts never overflow into negative values.

---

## 🧩 Recommended Sister Mods

If you enjoy **Item Clumps**, these companion mods from the **Instant Gratification Collection** plug in seamlessly:

* 📦 [**Stack Size Adjuster**](https://modrinth.com/mod/ig-stack-size-adjuster): Scale inventory slot and container limits from 64 up to 2.14 Billion, perfectly aligning your storage chests with ground clump thresholds.
* 🧲 [**Magnet (Let Me Get That!)**](https://modrinth.com/mod/instant-gratification-magnet,-let-me-get-that!): Automatically vacuum massive item clumps and XP orbs straight into your inventory from up to 64 blocks away.
* ⛏️ [**Ore Amplifier**](https://modrinth.com/mod/instant-gratification-ore-amplifier): Multiply vanilla and modded ore generation in newly generated chunks to feed your high-capacity storage systems.

> 🌟 *Explore the full [**Instant Gratification Collection**](https://modrinth.com/collection/instant-gratification) for more high-convenience enhancements.*

---

## ☕ Support

If you enjoy the **Instant Gratification Collection**, consider fueling future development!

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

> [!NOTE]
> **🇮🇩 Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

> [!TIP]
> **Dedicated Server Hosting Partner:**
> Looking for a reliable server to play with friends? Check out **BisectHosting** for 1-click modpack installations, automated backups, and 24/7 dedicated customer support.

---

## 📜 Credits & Modpack Permissions

| Property | Information |
| :--- | :--- |
| **Creator / Author** | **Dasik** (Rifaditya) |
| **Collection** | Instant Gratification Collection |
| **License** | [GNU General Public License v3.0 (GPLv3)](https://www.gnu.org/licenses/gpl-3.0.html) |
| **Source Code** | [GitHub - Rifaditya/IG-item-clumps](https://github.com/Rifaditya/IG-item-clumps) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/IG-item-clumps/issues) |
| **Documentation / Wiki** | [GitHub Wiki](https://github.com/Rifaditya/IG-item-clumps/wiki) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports—provided your project remains open-source under GPLv3 with proper attribution.<br>
> **However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.**

---

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
