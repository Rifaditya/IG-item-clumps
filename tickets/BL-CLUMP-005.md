# 🎫 [BL-CLUMP-005] Optional Batch Hopper Siphoning

- **Ticket ID**: `[BL-CLUMP-005]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[MEDIUM]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Hoppers in vanilla only siphon 1 item every 8 game ticks (2.5 items/sec). When hoppers sit underneath massive item clumps of 2,000 items, draining the clump takes over 13 minutes, creating hopper bottlenecks.

Server administrators and technical players can enable batch hopper siphoning, allowing hoppers to pull configurable batch sizes (e.g. 16 or 64 items per transfer cycle) from clumps, eliminating farm lag.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.hopper`
- Classes to Modify/Create:
  - `HopperBlockEntityMixin.java`: Injects into `extract(Inventory, ItemEntity)` to support configurable transfer batching.
  - `BatchHopperHelper.java`: Checks available space in target hopper slot and transfers up to `batchTransferSize` items in a single tick.
  - `ItemClumpsGameRules.java`: GameRule `itemclumps:batchHopperSiphoning` (default: `false` for vanilla parity) and `itemclumps:hopperBatchSize` (default: `16`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Hopper fullness: never transfers more items than available slot space. Respects item stack size limits (e.g. non-stackable items, 16-stackable ender pearls).
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Hook `HopperBlockEntityMixin.java` on item entity siphon.
2. Implement `BatchHopperHelper.java` calculating slot capacity.
3. Register dynamic GameRules in `ItemClumpsGameRules.java`.
4. Unit test hopper transfer bounds with varying stack sizes.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
