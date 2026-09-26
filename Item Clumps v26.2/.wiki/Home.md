# Item Clumps Wiki (Minecraft 26.2)

Welcome to the official technical and gameplay documentation for **Item Clumps** on **Minecraft 26.2** (`1.0.23+26.2`), engineered by **Dasik (Rifaditya)**.

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 📊 Version Infobox

| Property | Value |
| :--- | :--- |
| **Target Game Version** | Minecraft `26.2` (`>=26.2-`) |
| **Mod Version** | `1.0.23+26.2` |
| **Mod ID** | `item_clumps` |
| **Release Track** | Instant Gratification (IG) |
| **Environment** | Server-Side Only (`environment: *`) |
| **Java Requirement** | Java 25+ (`release = 25`) |
| **Required Dependencies** | `fabricloader >=0.19.1`, `fabric-api *`, `dasik-library >=1.8.2` |
| **Optional GUI Providers** | `yet-another-config-lib *` (YACL v3), `modmenu *` |
| **Configuration Path** | `config/item-clumps.json` |

---

## ⚡ Quick Feature Navigation

### 🎮 Gameplay & Mechanics
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]] — Aggressive item aggregation, bounding box expansion, 64-bit integer overflow protection, and zero heap allocation math.
* [[Smart Pickup System|Smart-Pickup-and-Inventory-Distribution]] — Single-batch native inventory intake, capacity simulation, and clean vanilla stack creation.
* [[Hopper Integration|Hopper-and-Automation-Integration]] — 1-item drip extraction ($2.5\text{ items/s}$) preserving vanilla automation timings.
* [[Despawn Age Rules|Despawn-Timer-and-Age-Inheritance]] — Youngest age inheritance ($\min(\text{age}_A, \text{age}_B)$) protecting high-yield production farms.
* [[Holographic Labels|Holographic-Labels-and-Thresholds]] — Vanilla custom name tag rendering, count formatting, and `label_min_count` threshold gating.
* [[Mod Compatibility|Compatibility-and-Integrations]] — In-flight Magnet mod protection, Stack Size Adjuster dynamic limit sync, and mob farm support.

### ⚙️ Administration & Configuration
* [[GameRules Table|GameRules]] — Complete reference table of all 5 namespaced GameRules, default values, min/max bounds, and localized titles.
* [[Commands & Admin|Commands-and-Administration]] — Administering via standard `/gamerule` commands and Game Rules UI (Absence Policy verification).
* [[Advancements|Advancements]] — Native Minecraft statistic tracking via `Stats.ITEM_PICKED_UP` (Absence Policy verification).
* [[Configuration|Configuration]] — JSON schema, YACL v3 GUI integration, and new-world baseline defaults.

### 💻 Developer & Engineering
* [[ModVersionGuard|ModVersionGuard-and-Safety]] — Runtime Knot ClassLoader resolution and bytecode safety guards.
* [[Architecture & Mixins|Architecture-and-Mixins]] — Package hierarchy, Mixin targets (`ItemEntityMixin`, `HopperBlockEntityMixin`), and injection points.
* [[Developer Setup|Developer-Setup-and-Building]] — JDK 25 setup, Loom Gradle build commands (`./gradlew build --no-daemon`), and local maven publishing.
* [[API & Addons|API-and-Addon-Integration]] — Extension patterns and DasikLibrary API integration.
* [[FAQ & Diagnostics|Troubleshooting-and-FAQ]] — Diagnostic steps, despawn FAQs, and performance benchmarks.
* [[Version Matrix|Version-Compatibility]] — Multi-era version matrix.
