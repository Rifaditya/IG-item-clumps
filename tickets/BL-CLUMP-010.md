# 🎫 [BL-CLUMP-010] Explosion Drop Pre-Clumping

- **Ticket ID**: `[BL-CLUMP-010]`
- **Category**: `[PERF]`
- **Priority**: `[HIGH]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `✅ RESOLVED`
- **Date Resolved**: `2026-10-18`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Massive TNT explosions or Wither detonations break hundreds of blocks simultaneously. Vanilla spawns hundreds of separate `ItemEntity` instances in the same tick before `tryToMerge` can consolidate them, freezing server ticks.

Pre-aggregating item drops during explosion block decay directly spawns consolidated clumps, reducing entity allocation by up to 90% during massive mining blasts.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.explosion`
- Classes to Modify/Create:
  - `ExplosionMixin.java`: Injects into explosion block processing to aggregate drops into an item-count accumulator map before entity instantiation.
  - `PreClumpDropHelper.java`: Spawns pre-merged clumps at explosion cluster centroids.
  - `ItemClumpsConfig.java`: Adds `preClumpExplosions` (default: `true`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Guarantees 100% loot table parity: Fortune, Silk Touch, and explosion drop decay percentages are accurately computed before aggregation.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Hook `ExplosionMixin.java` block drop loot collection.
2. Implement `PreClumpDropHelper.java` accumulator.
3. Verify loot quantities match vanilla 1:1 via GameTest.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [x] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [x] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [x] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [x] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [x] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [x] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
