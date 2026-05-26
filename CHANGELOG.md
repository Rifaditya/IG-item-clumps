# Changelog

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
