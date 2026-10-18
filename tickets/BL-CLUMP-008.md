# 🎫 [BL-CLUMP-008] Debug Clump Label Mode (Target Max Display)

- **Ticket ID**: `[BL-CLUMP-008]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[LOW]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Players and modders debugging clumping behavior only see standard counts (e.g. `Cobblestone x1,500`), without knowing the configured saturation ceiling.

Enabling debug mode displays the maximum stack ceiling (e.g. `Cobblestone 1,500/9,999`), showing how close a clump is to its merge saturation cap.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.client.render`
- Classes to Modify/Create:
  - `ItemEntityRendererMixin.java`: Injects formatting logic into label string generation.
  - `ItemClumpsConfig.java`: Adds `debugMaxDisplay` boolean option (default: `false`).
  - `ClumpLabelFormatter.java`: Formats string as `[ItemName] [Current]/[Max]` when debug is active.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Reverts cleanly to `[ItemName] x[Current]` when debug mode is disabled.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Add `debugMaxDisplay` to `ItemClumpsConfig.java`.
2. Update `ClumpLabelFormatter.java` to support debug formatting.
3. Unit test string formatting outputs.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
