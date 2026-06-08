# Changelog

## [1.0.14+R-26.2] - 2026-06-06
### Summary
The production **Release** refactoring the optional GUI loading and adding configuration warning details.
- **ModMenu GUI Refactor**: Migrated config screen initialization to use `GuiHelper.getOptionalFactory` from `DasikLibrary` for safe classloader isolation.
- **New World Warning**: Added a text warning to the top of the General category in the Cloth Config GUI to clearly state that changes only affect new worlds.
- **Dependency Alignment**: Bumped `dasik-library` dependency requirement to `>=1.8.2` to support `GuiHelper`.

## [1.0.12+R-26.2] - 2026-06-04
### Summary
The production **Release** version enabling multi-version compatibility.
- **Multi-Version Compatibility**: Widened dependencies and verified codebase APIs to support both Minecraft **26.1.2** and **26.2** (compiled against **26.2-pre-3**).
- **Core Optimizations Stabilized**: Production-grade verification of `copyWithCount` API optimization, `tryToMerge` fast-path optimizations, and optional `cloth-config` / `modmenu` GUI configurations.

## [1.0.11+A-26.2] - 2026-05-30
### Summary
The **"tryToMerge Fast-Path Checks"** update.
- **tryToMerge Optimization**: Reordered checks in `tryToMerge` inside `ItemEntityMixin.java` to perform cheap item/target compatibility comparisons first. Mismatching items now exit instantly before doing expensive Map-based GameRule lookups or reflection calls.

## [1.0.10+A-26.2] - 2026-05-30
### Summary
The **"copyWithCount API Optimization"** update.
- **copyWithCount Optimization**: Replaced two-step `.copy()` and `.setCount()` calls inside `ItemEntityMixin` (merging/pickups) and `HopperBlockEntityMixin` (hopper extraction) with a single call to the native `copyWithCount(int)` API. This reduces total object allocation overhead in JVM heap memory.

## [1.0.9+A-26.2] - 2026-05-30
### Summary
The **"Magnet Mod Compatibility"** update.
- **Magnet Compatibility**: Added dynamic reflection-based check for the `magnet` mod during item merges (`tryToMerge`). Prevents items from clumping while in-flight under magnet attraction, ensuring smooth physics paths and preventing visual warping or loss of velocity.

## [1.0.8+A-26.2] - 2026-05-30
### Summary
The **"Search Radius Optimization"** update.
- **ModifyArgs Redirect Optimization**: Replaced the `@ModifyArgs` injection targeting `AABB.inflate()` in `ItemEntityMixin` with a `@Redirect` call. This prevents array/object allocations at runtime, improving garbage collection behavior during item clumping checks.

## [1.0.7+A-26.2] - 2026-05-30
### Summary
The **"Pickup Loop Optimization"** update.
- **smartPickup Optimization**: Refactored `smartPickup` in `ItemEntityMixin` to use Minecraft's native single-batch inventory addition. This eliminates chunk-by-chunk iteration, list splitting, and multiple object allocations when picking up mega-clumps.

## [1.0.6+A-26.2] - 2026-05-30
### Summary
The **"Minecraft 26.2 Upgrade & Tick Optimization"** update.
- **Tick Optimization**: Removed the per-tick mixin hook on `ItemEntity.tick()` that updated the vanilla name tag. Moving this update exclusively to `setItem()` eliminates constant queries to the GameRules API and string allocations for non-moving ground items.
- **Minecraft 26.2 Upgrade**: Upgraded the project dependency configurations to target Minecraft `26.2-pre-2` and Fabric API `0.149.2+local`.

## [1.0.5+A-26.1.2] - 2026-05-26
### Summary
The **"ConfigHelper Migration"** update. Refactors configuration loading and saving to use the standard centralized API in `DasikLibrary`.
- **Config Migration**: Refactored `ItemClumpsConfig` to delegate all deserialization, serialization, backup, size limit checking, and atomic swap writes to the library's centralized `ConfigHelper` class.
- **Runtime Dependency Guard**: Added a runtime version verification at startup. If `DasikLibrary` version is less than `1.7.4` (or `ConfigHelper` is missing), the game aborts and throws a Minecraft `ReportedException` wrapping a descriptive `CrashReport`: `"Item Clumps: DasikLibrary version mismatch! Requires version 1.7.4 or higher. Please update your mods."`
- **⚠️ WARNING**: This version requires the newest **`DasikLibrary 1.7.4`** or higher. Older library versions will trigger the crash report.

## [1.0.4+A-26.1.2] - 2026-05-26
### Summary
The **"Library Update & Sync Fix"** release. Bumps DasikLibrary dependency to 1.7.2 and fixes client-server desync.
- **Client-Server Count Desync**: Added stack copying before count modifications in entity merging (`tryToMerge`), player pickups (`playerTouch`), and hopper collections. This ensures that the mutated `ItemStack` has a different object reference, forcing Minecraft's `SyncedEntityData` to register the change and broadcast metadata packets to all tracking clients.
- **Library Version Bump**: Updated project dependencies to require `dasik-library` version `1.7.2` or later.
- **Dual-Side Game Rule Lookup Parity**: Updated integration to support the new dual-side query helpers in `DynamicGameRuleManager` from DasikLibrary.

## [1.0.3+R-26.1.2] - 2026-05-23
### Summary
The production **Release** version of the **"Optional GUI Configuration"** updates.
- **Optional GUI Configuration**: Added optional integration with **Cloth Config** and **ModMenu** for clients that have these mods installed. Allows players to customize baseline defaults for new worlds via a graphical config menu.
- **Metadata Suggestions**: Updated `fabric.mod.json` to suggest `cloth-config` and `modmenu` instead of strictly requiring them.
