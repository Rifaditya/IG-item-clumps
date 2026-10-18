# 🎫 [BL-CLUMP-019] Downstream Ecosystem & Toolchain Alignment: watched_projects, fabric.mod.json, Queue Segregation & Archive Hierarchy

- **Category**: `[TECH_DEBT]`
- **Priority**: `[HIGH]`
- **Target Version**: All Anchors
- **Status**: `✅ RESOLVED`
- **Date Added**: 2026-10-01
- **Problem / Context**:
  Across the studio release pipeline and downstream automation tools, systemic inconsistencies exist:
  1. `dasik-mod-sync/watched_projects.json`: Many mods contain broken `icon_path` mappings (non-existent nested folder references) or stale `versions` arrays that omit active anchors.
  2. `fabric.mod.json`: Platform metadata (`custom.modrinth.projectId`, `slug`, repository URLs) occasionally drifts from authoritative entries in `minecraft-mod-release-hub/config/platform_projects.json`.
  3. `RELEASE_QUEUE.md`: Multi-version subproject directories often retain predecessor anchor changelog headers, violating the Subproject Queue Segregation Law.
  4. Archive Structure: Collection roots lack standardized `Archive Jar of all versions/` hierarchies (`MC <Version>/`), preventing `sync_archives.py` from auto-discovering compiled builds for release hub and external vault distribution.
- **Proposed Solution & Technical Specifications**:
  1. Audit and correct `icon_path` and `versions` array in `dasik-mod-sync/watched_projects.json` for item-clumps.
  2. Align `fabric.mod.json` across all active subprojects with official `platform_projects.json` metadata.
  3. Purge predecessor version headers from subproject `RELEASE_QUEUE.md` and `CHANGELOG.md` files (strict Version Anchor Exclusivity).
  4. Scaffold/verify collection root `Archive Jar of all versions/` containing dedicated `MC <Version>/` folders with existing release JARs mirrored.
  5. Scaffold root `MASTER_RELEASE_QUEUE.md` multi-anchor dashboard where absent.
- **Verification & Acceptance Criteria**:
  - [x] `watched_projects.json` icon path physically exists on disk and `versions` matches active anchors.
  - [x] `fabric.mod.json` metadata strictly aligns with `platform_projects.json`.
  - [x] Subproject `RELEASE_QUEUE.md` files contain strictly target-anchor release entries.
  - [x] Root `Archive Jar of all versions/` exists and contains release JARs discoverable by `sync_archives.py`.
