# Changelog

## [1.3.0+26.1.2] - 2026-10-19

### Added
- Holographic label wall-occlusion culling: hides clump labels behind solid opaque blocks to reduce visual clutter and GPU draw calls.
- Dynamic GameRule `item_clumps:cull_occluded_labels` (default: true).
- Config toggle in Mod Menu / YACL settings.

## [1.2.0+26.1.2] - 2026-10-18

### Added
- Explosion Drop Pre-Clumping: Pre-aggregates block drops during massive explosions (TNT, Wither) to reduce entity allocations by up to 90% and eliminate server tick freeze.
- Dynamic GameRule `item_clumps:pre_clump_explosions` (default: `true`) to toggle explosion drop pre-clumping in-game.
- Added Pre-Clump Explosions toggle in YACL client configuration screen.

## [1.1.0+26.1.2] - 2026-10-14

### Added
- Smooth dynamic visual scaling for merged item clumps: volume expands logarithmically from 1.0x up to 2.5x based on clump item count.
- Dynamic GameRule `item_clumps:enable_visual_scaling`.

## [1.0.29+26.1.2] - 2026-10-14

### Fixed
- **Knot ClassLoader Safety**: Purged legacy `ModVersionGuard` and string reflection runtime hazards (`Class.forName(...)`), preventing classloader crashes in production client and dedicated server environments.

## [1.0.28+26.1.2]

### Added
- **Minecraft 26.1.2 Modern Sovereign Port**: Established dedicated anchor subproject for Minecraft 26.1.2 with multi-era parity and zero cross-version reflection.
- **DasikLibrary 1.8.39 Integration**: Standardized on DasikLibrary 1.8.39 dynamic GameRule and config subsystems.
- **Full Feature Parity**: Full parity with v26.2/v26.3 features including non-invasive MixinExtras, holographic clump labels, and in-game YACL configuration.
