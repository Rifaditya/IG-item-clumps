# Changelog History

All notable changes to the item clumping mod are documented below.

## [BL-CLUMP-010] Explosion Drop Pre-Clumping Across All Anchors (2026-10-18)
*Released: October 18, 2026 (All Anchors: 1.20.1, 1.21.1, 1.21.11, 26.1.2, 26.2, 26.3)*

### Summary
Pre-aggregates block drops during massive explosions (TNT, Wither) at spatial centroids to reduce entity allocations by up to 90% and prevent server tick freezes.

### Anchors
`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3` (All bumped to `1.2.0+<MC>`).

### Technical Architecture
- **Drop Aggregator**: `PreClumpDropHelper.java` aggregates item drops during explosion block decay directly at explosion cluster centroids, bypassing hundreds of individual `ItemEntity` instantiations in the same server tick.
- **Era-Tailored Mixins**:
  - `ServerExplosionMixin.java` for MC 26.3, 26.2, 26.1.2, and 1.21.11: hooks modern server-side explosion block drop resolution.
  - `ExplosionMixin.java` for MC 1.21.1 and 1.20.1: hooks legacy explosion block processing.
- **Dynamic Configuration & GameRules**: Dynamic GameRule `item_clumps:pre_clump_explosions` (boolean, default true) synchronized across Brigadier commands and optional YACL client configuration screen toggle.
- **Verification & Test Harness**: Comprehensive headless unit test suites verifying drop accumulation, 100% loot table parity preservation, saturation arithmetic safety, and multi-era Knot ClassLoader side-safety.

---

## 1.1.0 (All Anchors: 1.20.1, 1.21.1, 1.21.11, 26.1.2, 26.2, 26.3)
*Released: October 14, 2026*

### [BL-CLUMP-001] Smooth Dynamic Visual Scaling
* **What**: Added dynamic logarithmic visual scaling for ground item clumps ($1.0\times$ up to $2.5\times$ scale) proportional to item count: $\text{scale} = \text{clamp}(1.0 + 0.3 \cdot \log_{10}(\text{count}), 1.0, 2.5)$. Added `item_clumps:enable_visual_scaling` dynamic GameRule, YACL client config toggle, and translatable strings.
* **Why**: Large merged clumps previously rendered at uniform vanilla scale with no visual sense of volume.
* **How**: Established client package and mixin infrastructure (`item-clumps.client.mixins.json`) across all 6 anchors. On modern Renderpearl anchors (`26.1.2`, `26.2`, `26.3`, `1.21.11`), ducked `ItemEntityRenderState` to capture stack count in `extractRenderState` and scaled `PoseStack` inside `ItemEntityRenderer.submit`. On pre-RenderState anchors (`1.20.1`, `1.21.1`), injected into `ItemEntityRenderer.render` directly after `pushPose()`. Enforced 0B/frame heap allocation on render loop with strict `@Environment(EnvType.CLIENT)` isolation.

### [BL-CLUMP-021] 5-Pillar Testing & Sidedness Verification Harness Adoption
* **What**: Deployed comprehensive automated test suites across all 6 anchors (3 suites × 6 anchors = 18 test files, 48 total assertions passed with zero failures).
* **Why**: Guard against the 5 Core Failure Modes (Headless Sidedness, Mixin Drift, Emergent State, Concurrency, and Test Oracle limitations) per `Testing & Runtime Verification Policy.md`.
* **How**: Implemented Pillar 1 Chaos Property Fuzzing (`ItemClumpsStressFuzzTest.java`, 20,000 iterations against negative values, NaN, Infinity, and 64-bit merge sum saturation), Pillar 2 Concurrency Load (`ItemClumpsConcurrencyLoadTest.java`, 50 worker threads simulating concurrent merges and atomic quotas), and Pillar 5 Headless Sidedness Contract Verification (`ItemClumpsSidednessContractTest.java`, scanning compiled bytecode constant pools to assert 0 references to `net.minecraft.client.*` or `com.mojang.blaze3d.*` in common classes).

### [BL-CLUMP-022] Knot ClassLoader Safety & Legacy Guard Purge
* **What**: Completely eradicated `ModVersionGuard.java` and unremapped string reflection `Class.forName("net.dasik.social.api.config.ConfigHelper")` across all 6 subprojects.
* **Why**: Calling unremapped Mojang names at runtime via `Class.forName` throws `ClassNotFoundException` under production Knot ClassLoader environments, crashing client and server initialization.
* **How**: Purged `ModVersionGuard.java` and reflection blocks in `ItemClumpsFabric.java`, relying strictly on `fabric.mod.json` dependency declarations per the Anti-Unremapped-Reflection Law and Comprehensive Legacy Guard Purge Invariant. Bumped patch versions (`1.0.30+26.3`, `1.0.29+26.2`, `1.0.29+26.1.2`, `1.0.1+1.21.11`, `1.0.1+1.21.1`, `1.0.1+1.20.1`).

## Docs-Only Maintenance (No Build)
*Recorded: October 4, 2026*

### [BL-CLUMP-020] BACKLOG.md Control-Character Corruption Repair
* **What**: Restored 7 corrupted bytes (5× TAB `0x09`, 2× NUL `0x00`) across tickets BL-CLUMP-001, -010, -011, -015 and wrapped the BL-CLUMP-001 scaling formula in `$$` delimiters.
* **Why**: A prior write pipeline converted backslash-t / backslash-0 sequences into control characters, corrupting specs (`true` → TAB+`rue`, `0.1x` → NUL+`.1x`). NUL bytes made Git classify `BACKLOG.md` as binary, hiding all diffs from review.
* **How**: Byte-level PowerShell repair with exact-once match asserts, UTF-8 no-BOM write, CRLF preserved. Verified zero control chars remain; workspace-wide scan of 117 `.md` files found no other occurrences. First text commit triggers one-time `core.autocrlf` line-ending normalization of this file. No SemVer bump, no JAR, no release queue impact.

## 1.0.21+26.2
*Released: July 15, 2026*

### Magnet Mod Compatibility & Partial Merge Delay Fix
* **What**: Fixed compatibility with Magnet mod version 26.2 by checking the new `ig_magnet$isMagnetized` method signature. Additionally, copied the `pickupDelay` and `age` fields during partial item merging.
* **Why**: The Magnet mod renamed its helper method from `ig$isMagnetized` to `ig_magnet$isMagnetized` in 26.2, breaking reflection detection. The partial merge did not sync entity age/pickup delay, allowing players to bypass pickup delay or cause despawn desync.
* **How**: Updated reflection in `ItemEntityMixin.java` to search for `ig_magnet$isMagnetized` and fall back to `ig$isMagnetized`. Added assignment statements (`this.pickupDelay = Math.max(...)`, `this.age = Math.min(...)`) inside the partial merge check block.

## 1.0.20+26.2
*Released: July 14, 2026*

### ModMenu YACL Screen Factory Fix
* **What**: Restored ModMenu configuration screen registration by updating the screen factory helper class and method references.
* **Why**: The YACL migration in version 1.0.18 deleted the old `ClothConfigScreenHelper` but did not update the `ModMenuIntegration` entrypoint, leaving it referencing a non-existent class and failing to load the configuration screen.
* **How**: Modified `ModMenuIntegration.java` to use `GuiHelper.getOptionalYaclFactory` pointing to `YaclScreenHelper` and its `createScreen` method, resolving the reflection-based classloading failure.

## 1.0.19+26.2
*Released: July 11, 2026*

### Remove Live Config Sync & Standardized Warning Notice
* **What**: Removed live GameRule sync block from the YACL configuration save handler. Appended `§6Notice:§r` warning text into new config-specific option descriptions.
* **Why**: To keep GameRule settings isolated on a per-world basis so each world has different settings, and clearly warn the user to use `/gamerule` for existing worlds.
* **How**: Updated `YaclScreenHelper.java` save block to only save config files. Created config option descriptions in `en_us.json` containing the notice.

## 1.0.18+26.2
*Released: July 7, 2026*

### YACL Migration & Dynamic Limit Sync
* **What**: Migrated client configuration GUI screen from Cloth Config to YetAnotherConfigLib (YACL). Automatically align merging limits with Stack Size Adjuster's custom limits when both are installed.
* **Why**: Modernize user GUI config and prevent ground clumping limit conflicts with custom stack sizes.
* **How**: Replaced Cloth Config helper with `YaclScreenHelper.java`. Added reflection-based checks to hide the redundant "Max Clump Size" setting if Stack Size Adjuster is loaded.

## 1.0.17+26.2
*Released: July 7, 2026*

### Math Integer Overflow Fix
* **What**: Fixed integer overflow when merging ground item entities under massive stack sizes.
* **Why**: Prevents infinite merging loops and game crashes when ground item counts reach near 2.14B.
* **How**: Implemented bounds checks and safe additions using long arithmetic inside `tryToMerge`.

## 1.0.16+26.2
*Released: July 6, 2026*

### Added
* **What**: Added `label_min_count` Config setting and `item_clumps:label_min_count` dynamic GameRule.
* **Why**: The player wants to specify exactly at what count threshold the ground item clump label pops up. By default, it falls back to the vanilla max stack size, but can be set to any positive integer value.
* **How**: Mapped the config variable `labelMinCount` (default `-1`) to Cloth Config GUI and the `item_clumps:label_min_count` GameRule. In `ItemEntityMixin.java`, if the rule value is `-1` it falls back to checking the vanilla `MAX_STACK_SIZE` component, otherwise it checks if count exceeds the custom configured value.

## 1.0.15+26.2
*Released: July 6, 2026*

### Fixed
* **What**: Fixed missing ground item count labels when used alongside Stack Size Adjuster.
* **Why**: The Stack Size Adjuster mod overrides `getMaxStackSize()` to return custom larger limits (e.g. 640). Item Clumps was checking `stack.getMaxStackSize()` to determine if count was above limit, which failed because the count (e.g., 100) was less than 640.
* **How**: Modified `ItemEntityMixin.java` to fetch default max stack size from components (`stack.getOrDefault(DataComponents.MAX_STACK_SIZE, 1)`) bypassing the overridden `getMaxStackSize()`.

## 1.0.14+R-26.2
*Released: June 6, 2026*

### Changed
- **ModMenu GUI Refactor**: Migrated configuration screen loading in `ModMenuIntegration` to use the reflection-based `GuiHelper.getOptionalFactory` from `DasikLibrary` for safe server/client classloader isolation.
- **New World Warning**: Added a warning text description at the top of the general Cloth Config category to alert users that options only adjust new world defaults.
- **Dependencies**: Bumped `dasik-library` minimum dependency to version `1.8.2`.

---

## 1.0.12+R-26.2
*Released: June 4, 2026*

### Changed
- **Multi-Version Compatibility**: Configured compatibility to support both Minecraft **26.1.2** and **26.2** in a single unified release build.
- **Build Alignment**: Upgraded target compilation to Minecraft **26.2-pre-3** to align with workspace projects.
- **Optimizations Confirmed**: Consolidated and locked in memory and execution optimizations for ground item entity merging, player pickups, and hopper interaction.

---

## 1.0.11+A-26.2
*Released: May 30, 2026*

### Changed
- **tryToMerge Optimization**: Reordered checks in `tryToMerge` inside `ItemEntityMixin.java` to perform cheap item/target compatibility comparisons first. Mismatching items now exit instantly before doing expensive Map-based GameRule lookups or reflection calls.

---

## 1.0.10+A-26.2
*Released: May 30, 2026*

### Changed
- **copyWithCount Optimization**: Replaced two-step `.copy()` and `.setCount()` calls inside `ItemEntityMixin` (merging/pickups) and `HopperBlockEntityMixin` (hopper extraction) with a single call to the native `copyWithCount(int)` API. This reduces total object allocation overhead in JVM heap memory.

---

## 1.0.9+A-26.2
*Released: May 30, 2026*

### Changed
- **Magnet Compatibility**: Added dynamic reflection-based check for the `magnet` mod during item merges (`tryToMerge`). Prevents items from clumping while in-flight under magnet attraction, ensuring smooth physics paths and preventing visual warping or loss of velocity.

---

## 1.0.8+A-26.2
*Released: May 30, 2026*

### Changed
- **ModifyArgs Redirect Optimization**: Replaced the `@ModifyArgs` injection targeting `AABB.inflate()` in `ItemEntityMixin` with a `@Redirect` call. This prevents array/object allocations at runtime, improving garbage collection behavior during item clumping checks.

---

## 1.0.7+A-26.2
*Released: May 30, 2026*

### Changed
- **smartPickup Optimization**: Refactored `smartPickup` in `ItemEntityMixin` to use Minecraft's native single-batch inventory addition. This eliminates chunk-by-chunk iteration, list splitting, and multiple object allocations when picking up mega-clumps.

---

## 1.0.6+A-26.2
*Released: May 30, 2026*

### Changed
- **Tick Optimization**: Removed the per-tick mixin hook on `ItemEntity.tick()` that updated the vanilla name tag. Moving this update exclusively to `setItem()` eliminates constant queries to the GameRules API and string allocations for non-moving ground items.
- **Minecraft 26.2 Upgrade**: Upgraded the project dependency configurations to target Minecraft `26.2-pre-2` and Fabric API `0.149.2+local`.

---

## 1.0.5+A-26.1.2
*Released: May 26, 2026*

### Changed
- **ConfigHelper Migration**: Refactored `ItemClumpsConfig` to delegate all deserialization, serialization, backup, size limit checking, and atomic swap writes to the library's centralized `ConfigHelper` class.
- **Runtime Dependency Guard**: Added a runtime version verification at startup. If `DasikLibrary` version is less than `1.7.4` (or `ConfigHelper` is missing), the game aborts and throws a Minecraft `ReportedException` wrapping a descriptive `CrashReport`: `"Item Clumps: DasikLibrary version mismatch! Requires version 1.7.4 or higher. Please update your mods."`
- **⚠️ WARNING**: This version requires the newest **`DasikLibrary 1.7.4`** or higher. Older library versions will trigger the crash report.

---

## 1.0.4+A-26.1.2
*Released: May 26, 2026*

### Fixed
- **Client-Server Count Desync**: Added stack copying before count modifications in entity merging (`tryToMerge`), player pickups (`playerTouch`), and hopper collections. This ensures that the mutated `ItemStack` has a different object reference, forcing Minecraft's `SyncedEntityData` to register the change and broadcast metadata packets to all tracking clients.

### Changed
- **Library Version Bump**: Updated project dependencies to require `dasik-library` version `1.7.2` or later.
- **Dual-Side Game Rule Lookup Parity**: Updated integration to support the new dual-side query helpers in `DynamicGameRuleManager` from DasikLibrary.

---

## 1.0.3+R-26.1.2
*Released: May 23, 2026*

### Added
- **Optional GUI Configuration**: Added optional integration with **Cloth Config** and **ModMenu** for clients that have these mods installed. Allows players to customize baseline defaults for new worlds via a graphical config menu.

### Changed
- **Metadata Suggestions**: Updated `fabric.mod.json` to suggest `cloth-config` and `modmenu` instead of strictly requiring them.
- **Production Release**: Released the optional GUI configuration alongside standard namespaced GameRules in a stable production build.

---

## 1.0.2+A-26.1.2
*Released: May 23, 2026*

### Changed
- **Unified Server-Side Codebase**: Consolidated the client/server split architecture into a single, 100% server-side only mod. Standardized on the single `item_clumps` mod ID.
- **Removed Client Files**: Deleted client entrypoint, client packet listener mixin, client renderer mixin, and the `MegaCountData` interface.
- **Consolidated Merging Logic**: Refactored `ItemEntityMixin` and `HopperBlockEntityMixin` to manipulate and query standard `ItemStack` counts directly, utilizing 26.x VarInt count serialization for client-side syncing without custom packets.
- **Custom Name Tag Display**: Implemented server-side formatting of vanilla custom name tags (`setCustomName`/`setCustomNameVisible`) to render floating item count labels above virtual clumps.

### Fixed
- **Identifier Compliance**: Migrated namespace registrations to use `Identifier.fromNamespaceAndPath` to strictly match updated platforms API requirements.

---

## 1.0.1+A-26.1.2
*Released: May 23, 2026*

### Fixed
- **Server-Side Compatibility**: Added dynamic compatibility checks in `ClientPacketListenerMixin` to bypass client-side count overrides when connected to a server-side only mod host (`item_clumps_server`) or vanilla host. This resolves entity display visual jittering and flickering for modded clients connecting to server-only networks.

---

## 1.0.0+build.11
*Released: May 20, 2026*

### Changed
- **Modrinth Showcase Video**: Updated `modrinth_description.md` to embed YouTube video using iframe HTML block code recommended by Modrinth.

---

## 1.0.0+build.10
*Released: May 20, 2026*

### Added
- **Root README & License**: Created missing GPLv3 license and comprehensive project README at root featuring badges, installation, configuration reference, and guide indexes.

### Changed
- **Documentation Parity**: Updated `concept_item_clumps.md` config type definitions and `architecture.md` sequence diagrams to match actual codebase implementations (optimized larger-absorbs-smaller clumping, age inheritance, and integer merge radius).

### Fixed
- **Source Verification & Imports**: Cleaned up unused imports in all Mixin classes and added required Source Verification Protocol citations referencing decompiled Mojang-mapped classes (`ItemEntity.java`, `HopperBlockEntity.java`, and `ItemEntityRenderer.java`).

---

## 1.0.0+build.9
*Released: May 20, 2026*

### Fixed
- **Client-Side Item Disappearance**: Implemented client packet interception for partial pickups in `ClientPacketListener`. Temporarily scales the client-side item stack count to match the logical clump's size during packet processing, preventing the client from incorrectly shrinking the base stack below 1 and discarding the item entity from the screen.

---

## 1.0.0+build.8
*Released: May 20, 2026*

### Fixed
- **Player Pickup Item Deletion**: Corrected player inventory pickup logic to correctly calculate the quantity of items successfully added during partial inventory slots intake, preventing unabsorbed items from being silently deleted when the player's inventory becomes full mid-pickup.

---

## 1.0.0+build.7
*Released: May 20, 2026*

### Changed
- **Merge Radius Description**: Updated `item_clumps:merge_radius` GameRule description (in both registration code and `en_us.json` language files) to explicitly clarify that it operates as a horizontal block search radius, and that items will not clump if one is 1 block above or below the other, preserving vanilla vertical merging constraints.

---

## 1.0.0+build.6
*Released: May 20, 2026*

### Fixed
- **Name Tag Visibility Toggle**: Synced the `item_clumps:render_labels` GameRule from the server to client entities via `EntityDataAccessor`, ensuring floating count labels disappear immediately when the GameRule is toggled off and reappear when toggled on.

---

## 1.0.0+build.5
*Released: May 20, 2026*

### Added
- **Optimized Mod Icon**: Re-designed and scaled up the 3D grass block icon and the accompanying "x129" text label to ensure pixel-perfect legibility and prevent smudging when rendered at small icon scales (e.g. 64x64 or 32x32 pixels).

---

## 1.0.0+build.4
*Released: May 20, 2026*

### Added
- **Mod Icon**: Configured the custom generated 3D isometric grass block mod icon for project visibility in the Fabric mod list and launch environments.

---

## 1.0.0+build.3
*Released: May 20, 2026*

### Fixed
- **Localization**: Added missing language translation file (`en_us.json`) to localize the custom GameRuleCategory display name as "Item Clumps" in the Game Rules settings screen.

---

## 1.0.0+build.2
*Released: May 20, 2026*

### Fixed
- **Client Crash**: Removed non-existent client entrypoint registration from `fabric.mod.json` which was causing crashes upon client initialization.
- **Mixin Refmap**: Added the `refmap` property to the mixin configuration (`item-clumps.mixins.json`) to prevent production/remapped environment initialization failures.

---

## 1.0.0+build.1
*Released: May 19, 2026*

### Added
- **Infinite Merging**: Merges identical item entities in the world into a single entity tracking a virtual count.
- **Hopper Integration**: Drip-feeds items from virtual clumps into hoppers one item at a time.
- **Name Tags**: Client-side name tag display above virtual clumps showing item count.
- **Configuration**: Added three customizable GameRules (`enable_clumping`, `merge_radius`, and `max_clump_size`).
