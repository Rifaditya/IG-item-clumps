# 🎫 [BL-CLUMP-015] Holographic Label Wall-Occlusion Culling

- **Ticket ID**: `[BL-CLUMP-015]`
- **Category**: `[PERF]`
- **Priority**: `[HIGH]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `✅ RESOLVED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Vanilla name tags and text labels render through solid blocks. Players near large mob farms see dozens of floating text labels rendered through solid stone walls, causing visual clutter and GPU overhead.

Holographic clump labels behind solid opaque blocks are culled from rendering, eliminating visual clutter and saving GPU vertex draw calls.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.item_clumps.client.render`
- Classes Modified/Created:
  - `ClumpOcclusionCuller.java`: Fast camera line-of-sight raycasting with 1.5m proximity safety bubble and solid opaque occluder checks.
  - `ItemEntityRendererMixin.java`: MixinExtras `@WrapOperation` intercepting label rendering in `ItemEntityRenderer.submit` / `ItemEntityRenderer.render`.
  - `ItemClumpsConfig.java`: Adds `cullOccludedLabels = true`.
  - `ItemClumpsFabric.java`: Registers dynamic GameRule `item_clumps:cull_occluded_labels`.
  - `YaclScreenHelper.java`: Client GUI toggle integration.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: `ItemEntityRenderer.render` `@WrapOperation` on `EntityRenderer.render`, `ClipContext` with camera observer entity, `isSolidRender(level, pos)`.
- **MC 1.21.1 (Java 21)**: `ItemEntityRenderer.render` `@WrapOperation`, `isSolidRender(level, pos)`.
- **MC 1.21.11 (Java 21)**: `ItemEntityRenderer.submit` `@WrapOperation` on `EntityRenderer.submit` with generic erasure `EntityRenderState`, 0-arg `isSolidRender()`.
- **MC 26.1.2 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl `ItemEntityRenderer.submit` `@WrapOperation`, 0-arg `isSolidRender()`, saturated arithmetic safety.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Transparent blocks (glass, water, leaves): line-of-sight raycast checks `state.canOcclude() && state.isSolidRender()` so transparent blocks never occlude.
- Dedicated Server Safety: client rendering logic strictly isolated to client package with `@Environment(EnvType.CLIENT)`; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `ClumpOcclusionCuller.java` with block raycasting across all 6 anchors.
2. Inject into `ItemEntityRendererMixin.java` before label matrix draw via `@WrapOperation`.
3. Unit test raycast performance and safety bubble boundaries.
4. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [x] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [x] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [x] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [x] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [x] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [x] **Anchor Compilation**: Clean `./gradlew test build remapJar` across all 6 active anchors (`26.3`, `26.2`, `26.1.2`, `1.21.11`, `1.21.1`, `1.20.1`).
