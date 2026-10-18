# 🎫 [BL-CLUMP-014] Crosshair / F3 Targeted Clump Stack Size Display

- **Ticket ID**: `[BL-CLUMP-014]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[LOW]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Looking at an item entity with the F3 debug screen active only shows generic entity type without displaying clump stack size or despawn timer metadata.

When targeting an item clump with crosshair in F3 mode, the debug screen surfaces detailed clump telemetry (exact item count, despawn age, merge eligibility).

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.client.debug`
- Classes to Modify/Create:
  - `DebugHudMixin.java`: Injects into `getRightText` to append targeted clump telemetry when crosshair raycasts an `ItemEntity`.
  - `ClumpTelemetryFormatter.java`: Formats debug readout strings with count, age, and tag status.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Only activates when crosshair directly targets an ItemEntity; zero overhead when looking at terrain or other entities.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Hook `DebugHudMixin.java` in client package.
2. Implement `ClumpTelemetryFormatter.java`.
3. Test F3 overlay display headlessly.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
