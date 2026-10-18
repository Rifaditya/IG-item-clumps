# 🎫 [BL-CLUMP-015] Holographic Label Wall-Occlusion Culling

- **Ticket ID**: `[BL-CLUMP-015]`
- **Category**: `[PERF]`
- **Priority**: `[HIGH]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Vanilla name tags and text labels render through solid blocks. Players near large mob farms see dozens of floating text labels rendered through solid stone walls, causing visual clutter and GPU overhead.

Holographic clump labels behind solid opaque blocks are culled from rendering, eliminating visual clutter and saving GPU vertex draw calls.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.client.render`
- Classes to Modify/Create:
  - `ClumpOcclusionCuller.java`: Performs fast camera line-of-sight raycast against block collision cache.
  - `ItemEntityRendererMixin.java`: Injects into label rendering to cancel matrix draw pass if occluded.
  - `ItemClumpsConfig.java`: Adds `cullOccludedLabels` (default: `true`).

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Transparent blocks (glass, water, leaves): line-of-sight raycast only treats full opaque collision blocks as occluders.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `ClumpOcclusionCuller.java` with block raycasting.
2. Inject into `ItemEntityRendererMixin.java` before label matrix draw.
3. Unit test raycast performance and accuracy.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
