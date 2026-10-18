# 🎫 [BL-CLUMP-006] Third-Party Modded Item Entity Compatibility

- **Ticket ID**: `[BL-CLUMP-006]`
- **Category**: `[BUGFIX]`
- **Priority**: `[HIGH]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Certain third-party tech and magic mods extend `ItemEntity` with custom entity subclasses (e.g. magnetic items, elemental sparks, thermal fluid capsules). Clumps currently only checks for exact vanilla `ItemEntity` instances.

Custom modded items either fail to clump or crash when custom entity state is improperly merged. This feature ensures universal polymorphism and compatibility across the modded ecosystem.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.compat`
- Classes to Modify/Create:
  - `EntityClassCompatibilityChecker.java`: Inspects entity class hierarchy, checking for custom NBT tags or custom tick methods.
  - `ItemEntityMixin.java`: Broadens injection targets from exact class check to polymorphic instance checks with safety bailouts.
  - `ItemClumpsConfig.java`: Blacklist/whitelist for modded entity class names.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Items with active custom velocity physics (e.g. Botania floating flowers): bypasses clumping if custom entity overrides movement vectors.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `EntityClassCompatibilityChecker.java`.
2. Update `ItemEntityMixin.java` to invoke polymorphic compatibility checks.
3. Unit test merge safety with simulated custom ItemEntity subclasses.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
