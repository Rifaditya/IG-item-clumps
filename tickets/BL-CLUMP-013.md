# 🎫 [BL-CLUMP-013] Chunk Item Entity Density Console Warning Alert

- **Ticket ID**: `[BL-CLUMP-013]`
- **Category**: `[PERF]`
- **Priority**: `[MEDIUM]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Runaway mob farms or broken dropper clocks can flood a single chunk with thousands of uncollected items, tanking server TPS before admins notice.

The server monitors chunk item density and throttles a diagnostic warning to the server console citing exact chunk coordinates when density exceeds threshold.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.server`
- Classes to Modify/Create:
  - `ChunkDensityMonitor.java`: Tracks item entity count per chunk during server world ticks.
  - `ServerWorldMixin.java`: Injects into entity tracking to sample chunk density.
  - `ItemClumpsConfig.java`: Adds `chunkDensityAlertThreshold` (default: `300`, `0` = disabled) and `alertCooldownSeconds` (default: `60`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Throttling: alerts are rate-limited to at most once per 60 seconds per chunk to prevent console log spam.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `ChunkDensityMonitor.java` with rate-limited logging.
2. Hook `ServerWorldMixin.java`.
3. Wire config options into `ItemClumpsConfig.java`.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
