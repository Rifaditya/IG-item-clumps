# Changelog

## [1.4.0+26.3] - 2026-11-01

### Added
- **Optional Batch Hopper Siphoning**: Opt-in redstone-safe batch item transfer for hoppers under oversized item clumps (default: 16 items per 8 ticks).
- **Dynamic GameRules**:
  - `item_clumps:batch_hopper_siphoning` (default: `false`): Toggles batch hopper siphoning.
  - `item_clumps:hopper_batch_size` (default: `16`, range `[1, Integer.MAX_VALUE]`): Maximum items pulled per cycle, saturated by slot capacity.
  - `item_clumps:batch_siphon_min_threshold` (default: `64`, range `[1, Integer.MAX_VALUE]`): Minimum clump count required to engage batch siphoning. Clumps below this threshold siphon at vanilla rate (strictly 1 item) to protect redstone clocks and shops.
- **Client Configuration Screen**: Added batch siphoning toggles and integer fields in YACL / ModMenu settings.

## [1.3.0+26.3] - 2026-10-19

### Added
- Holographic label wall-occlusion culling: hides clump labels behind solid opaque blocks to reduce visual clutter and GPU draw calls.
- Dynamic GameRule `item_clumps:cull_occluded_labels` (default: true).
- Config toggle in Mod Menu / YACL settings.

## [1.2.0+26.3] - 2026-10-18

### Added
- Explosion Drop Pre-Clumping: Pre-aggregates block drops during massive explosions (TNT, Wither) to reduce entity allocations by up to 90% and eliminate server tick freeze.
- Dynamic GameRule `item_clumps:pre_clump_explosions` (default: `true`) to toggle explosion drop pre-clumping in-game.
- Added Pre-Clump Explosions toggle in YACL client configuration screen.

## [1.1.0+26.3] - 2026-10-14

### Added
- Smooth dynamic visual scaling for merged item clumps: volume expands logarithmically from 1.0x up to 2.5x based on clump item count.
- Dynamic GameRule `item_clumps:enable_visual_scaling`.

## [1.0.30+26.3] - 2026-10-14

### Fixed
- **Knot ClassLoader Safety**: Purged legacy `ModVersionGuard` and string reflection runtime hazards (`Class.forName(...)`), preventing classloader crashes in production client and dedicated server environments.

## [1.0.29+26.3]

### Added
- **Minecraft 26.3 Release Support**: Fully upgraded and verified for the stable Minecraft 26.3 release with Fabric Loader 0.19.5 and Fabric API 0.161.0+26.3.
- **DasikLibrary 1.9.2 Integration**: Aligned with the latest DasikLibrary 1.9.2 runtime for robust dynamic GameRule handling and centralized configuration management.

### Changed
- **Toolchain Modernization**: Migrated to Fabric Loom 1.15.2 and streamlined build pipeline for optimal compatibility.
