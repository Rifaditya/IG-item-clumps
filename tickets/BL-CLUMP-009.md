# 🎫 [BL-CLUMP-009] Merge Particle Burst on Large Fusions

- **Ticket ID**: `[BL-CLUMP-009]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[LOW]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
When dozens or hundreds of items merge together into a single entity, the consolidation is completely silent and visually invisible, lacking visual punch.

Large merges emit a modest, satisfying cloud particle burst (`ParticleTypes.POOF`) and subtle sound cue, providing clear visual confirmation of consolidation.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.particles`
- Classes to Modify/Create:
  - `ClumpMergeParticleHelper.java`: Emits single particle poof at entity centroid when fusion count $\Delta N \ge 64$.
  - `ItemEntityMixin.java`: Injects into `tryToMerge` success branch to trigger particle packet to nearby tracking players.
  - `ItemClumpsConfig.java`: Adds `mergeParticlesEnabled` (default: `true`) and `minMergeParticleThreshold` (default: `64`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Single-item drops or small merges (< 64 items) produce zero particles to prevent visual spam in farms.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `ClumpMergeParticleHelper.java`.
2. Hook merge completion in `ItemEntityMixin.java`.
3. Unit test threshold gating.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
