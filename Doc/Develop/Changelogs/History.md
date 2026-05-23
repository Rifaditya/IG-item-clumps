# Changelog History

All notable changes to the item clumping mod are documented below.

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
