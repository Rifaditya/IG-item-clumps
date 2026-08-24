# 📋 Item Clumps Release Queue & Backlog

This file tracks which built versions (from the central archive folder "E:\Minecraft Project\Instant Gratification Collection\Item Clumps all version\Archive Jar of all versions") have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [x] **`1.0.1+A`** (26) - - **Server-Side Compatibility**: Added dynamic compatibility checks in `ClientPacketListenerMixin` to bypass client-side count overrides when connected to a server-side only mod host (`item_clumps_server`) or vanilla host. This resolves entity display visual jittering and flickering for modded clients connecting to server-only networks.
- [x] **`1.0.2+A`** (26) - - **Unified Server-Side Codebase**: Consolidated the client/server split architecture into a single, 100% server-side only mod. Standardized on the single `item_clumps` mod ID. - - **Removed Client Files**: Deleted client entrypoint, client packet listener mixin, client renderer mixin, and the `MegaCountData` interface.
- [x] **`1.0.3+R`** (26) - - **Optional GUI Configuration**: Added optional integration with **Cloth Config** and **ModMenu** for clients that have these mods installed. Allows players to customize baseline defaults for new worlds via a graphical config menu. - - **Metadata Suggestions**: Updated `fabric.mod.json` to suggest `cloth-config` and `modmenu` instead of strictly requiring them.
- [x] **`1.0.4+26.1.2`** (2026-07-11) - - Standardized Config Warning. - - Appended gold warning notice to option descriptions to clarify that changing config values only defines default settings for newly created worlds.

