# Changelog

## [1.0.29+26.1.2] - 2026-10-14

### Fixed
- **Knot ClassLoader Safety**: Purged legacy `ModVersionGuard` and string reflection runtime hazards (`Class.forName(...)`), preventing classloader crashes in production client and dedicated server environments.

## [1.0.28+26.1.2]

### Added
- **Minecraft 26.1.2 Modern Sovereign Port**: Established dedicated anchor subproject for Minecraft 26.1.2 with multi-era parity and zero cross-version reflection.
- **DasikLibrary 1.8.39 Integration**: Standardized on DasikLibrary 1.8.39 dynamic GameRule and config subsystems.
- **Full Feature Parity**: Full parity with v26.2/v26.3 features including non-invasive MixinExtras, holographic clump labels, and in-game YACL configuration.
