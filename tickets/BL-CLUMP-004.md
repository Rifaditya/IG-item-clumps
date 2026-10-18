# 🎫 [BL-CLUMP-004] Count-Scaled Pickup Audio Feedback

- **Ticket ID**: `[BL-CLUMP-004]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[LOW]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Picking up a massive consolidated clump of 500 items plays the exact same single vanilla pitch click as picking up a single dandelion seed.

Players collecting massive clumps receive deeply satisfying acoustic feedback where audio pitch and resonance dynamically scale with the collected item count, delivering punchy tactile satisfaction.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.audio`
- Classes to Modify/Create:
  - `ClumpAudioHelper.java`: Maps clump count $N$ to sound pitch and layered chimes:
    $$\text{Pitch} = \max(0.5, 1.2 - \log_{10}(N) \cdot 0.25)$$
  - `PlayerMixin.java` / `ItemEntityMixin.java`: Intercepts pickup event to play custom scaled `SoundEvents.ENTITY_ITEM_PICKUP` or multi-tone harp chimes.
  - `ItemClumpsConfig.java`: Toggle `scaledPickupSoundEnabled` (default: `true`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Rapid successive pickups: pitch shifts clamped between `0.5` and `2.0` to avoid ultrasonic screeching or subsonic clicks.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `ClumpAudioHelper.java` implementing dynamic logarithmic pitch curve.
2. Hook `ItemEntityMixin.java` on player collision pickup.
3. Wire config option into `ItemClumpsConfig.java`.
4. Unit test pitch curve saturation bounds.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
