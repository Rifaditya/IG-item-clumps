# 🎫 [BL-CLUMP-007] Server Admin Command Suite (/clumps)

- **Ticket ID**: `[BL-CLUMP-007]`
- **Category**: `[FEATURE]`
- **Priority**: `[HIGH]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Server administrators currently have no in-game diagnostic or management tools to inspect clump statistics, force-merge items in a radius, or clear excessive entity drops during lag spikes.

Admins can run `/<cmd>` to monitor server drop health, execute emergency merges, reload configs on the fly, and inspect entity density per chunk.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.command`
- Classes to Modify/Create:
  - `ClumpsCommand.java`: Registers Brigadier command tree `/clumps`:
    - `/clumps count [radius]`: Displays total item entities and cumulative clumped items.
    - `/clumps merge <radius>`: Forces immediate consolidation of all compatible items within radius.
    - `/clumps purge <radius>`: Emergency removal of dropped items with confirmation.
    - `/clumps reload`: Reloads sparse delta JSON config and resets caches.
  - `ClumpsCommandFeedback.java`: Formats chat output with clickable links and color-coded telemetry.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Operator permission checks: level 2 required for `merge`, `purge`, and `reload`. Read-only commands (`count`) accessible with level 0 if configured.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Implement `ClumpsCommand.java` using Fabric `CommandRegistrationCallback`.
2. Wire subcommands (`count`, `merge`, `purge`, `reload`).
3. Add translation keys in `en_us.json`.
4. Unit test command tree AST and execution headlessly.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
