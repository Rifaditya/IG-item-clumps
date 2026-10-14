# Changelog

## [1.1.0+1.21.1] - 2026-10-14

### Added
- Smooth dynamic visual scaling for merged item clumps: volume expands logarithmically from 1.0x up to 2.5x based on clump item count.
- Dynamic GameRule `item_clumps:enable_visual_scaling`.

## [1.0.1+1.21.1] - 2026-10-14

### Fixed
- **Knot ClassLoader Safety**: Purged legacy `ModVersionGuard` and string reflection runtime hazards (`Class.forName(...)`), preventing classloader crashes in production client and dedicated server environments.

## [1.0.0+1.21.1]

### Added
- **Minecraft 1.21.1 Older Anchor Port**: Established dedicated anchor subproject for Minecraft 1.21.1 with multi-era parity and zero cross-version reflection.
- **DasikLibrary 1.1.0 Integration**: Standardized on DasikLibrary 1.1.0 dynamic GameRule and config subsystems.
- **Full Feature Parity**: Full parity with modern features including non-invasive MixinExtras, holographic clump labels, and in-game YACL configuration.
