# 🎫 [BL-CLUMP-012] Client Glowing Outline Accessibility Toggle

- **Ticket ID**: `[BL-CLUMP-012]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[LOW]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Locating dark dropped items (e.g. coal, obsidian) in deep dark caves or murky waters can be challenging for visually impaired players.

An optional client-side accessibility toggle renders a subtle glowing outline around large item clumps, making valuable lost items easy to spot.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.client.accessibility`
- Classes to Modify/Create:
  - `ClumpOutlineRenderer.java`: Applies glowing shader outline to item entities meeting threshold count.
  - `ItemEntityRendererMixin.java`: Client-side check for outline shader pass.
  - `ItemClumpsConfig.java`: Adds `glowingOutlineEnabled` (default: `false`) and `glowingThreshold` (default: `64`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Strictly client-side: does not apply vanilla glowing status effect to server entity or broadcast glowing packets to other players.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `ClumpOutlineRenderer.java`.
2. Hook client render pass in `ItemEntityRendererMixin.java`.
3. Add accessibility toggle in YACL client config.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
