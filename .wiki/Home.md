# Item Clumps Wiki (Minecraft 26.1.2)

Welcome to the official technical and gameplay documentation for **Item Clumps** on **Minecraft 26.1.2** (`1.0.4+26.1.2`), engineered by **Dasik (Rifaditya)**.

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 📊 Version Infobox

| Property | Value |
| :--- | :--- |
| **Target Game Version** | Minecraft `26.1.2` (`>=26.1.2`) |
| **Mod Version** | `1.0.4+26.1.2` |
| **Mod ID** | `item_clumps` |
| **Release Track** | Instant Gratification (IG) |
| **Environment** | Server-Side Only (`environment: *`) |
| **Java Requirement** | Java 25+ (`release = 25`) |
| **Required Dependencies** | `fabricloader >=0.19.1`, `fabric-api >=0.145.4+26.1.2`, `dasik-library >=1.6.9+build.22` |
| **Optional GUI Providers** | `cloth-config *` (Cloth Config v2), `modmenu *` |
| **Configuration Path** | `config/item-clumps.json` |

---

## ⚡ Quick Feature Navigation

### 🎮 Gameplay & Mechanics
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]] — Ground item aggregation, bounding box expansion, and merge math.
* [[Smart Pickup System|Smart-Pickup-and-Inventory-Distribution]] — Chunked iterative inventory intake, capacity simulation, and clean vanilla stack creation.
* [[Hopper Integration|Hopper-and-Automation-Integration]] — 1-item drip extraction ($2.5\text{ items/s}$) preserving vanilla automation timings.
* [[Despawn Age Rules|Despawn-Timer-and-Age-Inheritance]] — Youngest age inheritance ($\min(\text{age}_A, \text{age}_B)$) protecting high-yield production farms.
* [[Holographic Labels|Holographic-Labels]] — Vanilla custom name tag rendering and count formatting.

### ⚙️ Administration & Configuration
* [[GameRules Table|GameRules]] — Complete reference table of all 4 namespaced GameRules, default values, min/max bounds, and localized titles.
* [[Commands & Admin|Commands-and-Administration]] — Administering via standard `/gamerule` commands and Game Rules UI (Absence Policy verification).
* [[Advancements|Advancements]] — Native Minecraft statistic tracking via `Stats.ITEM_PICKED_UP` (Absence Policy verification).
* [[Configuration|Configuration]] — JSON schema, Cloth Config v2 GUI integration, and new-world baseline defaults.

### 💻 Developer & Engineering
* [[Architecture & Mixins|Architecture-and-Mixins]] — Package hierarchy, Mixin targets (`ItemEntityMixin`, `HopperBlockEntityMixin`), and injection points.
* [[Developer Setup|Developer-Setup-and-Building]] — JDK 25 setup, Loom Gradle build commands (`./gradlew build --no-daemon`), and local maven publishing.
* [[API & Addons|API-and-Addon-Integration]] — Extension patterns and DasikLibrary API integration.
* [[FAQ & Diagnostics|Troubleshooting-and-FAQ]] — Diagnostic steps, despawn FAQs, and performance benchmarks.
* [[Version Matrix|Version-Compatibility]] — Multi-era version matrix.
