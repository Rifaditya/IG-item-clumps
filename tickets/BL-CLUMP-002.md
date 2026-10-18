# 🎫 [BL-CLUMP-002] Clump Despawn Warning Effect

- **Ticket ID**: `[BL-CLUMP-002]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[MEDIUM]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
When clumps sit idle without new items merging, players have no warning when the 5-minute (6000 tick) vanilla despawn timer is about to wipe thousands of gathered items.

Players working near farms or mining sites suddenly lose massive stacks of items with zero visual warning. With warning effects, the holographic label pulses and shifts color during the final 30-60 seconds, alerting players in time to collect their loot.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.client.render`
- Classes to Modify/Create:
  - `ItemEntityRendererMixin.java`: Injects into label rendering pass to calculate remaining entity age.
  - `ClumpDespawnAnimator.java`: Calculates sine-wave color pulsation between yellow (`#FFFF55`) and flashing red (`#FF5555`) when `entity.getItemAge() >= 5400` (final 30 seconds).
  - `ItemClumpsConfig.java`: Config option `despawnWarningEnabled` (default: `true`) and `warningThresholdSeconds` (default: `30`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- If new items merge into the clump, vanilla resets the entity age to 0, immediately restoring the label to its normal calm display. Does not desync client/server entity timers.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Hook `ItemEntityRendererMixin.java` in client package to check item entity age.
2. Author `ClumpDespawnAnimator.java` with sine wave color interpolation.
3. Wire config toggle into YACL client settings.
4. Unit test age threshold math and color clamping.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
