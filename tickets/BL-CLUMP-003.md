# 🎫 [BL-CLUMP-003] Customizable Hex Color for Holographic Labels

- **Ticket ID**: `[BL-CLUMP-003]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[LOW]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
The holographic floating text above clumps currently uses a fixed hardcoded color format, which may clash with player HUD themes or custom resource packs.

Players can customize label colors (e.g. gold, neon cyan, or clean white) to suit their aesthetic preferences or colorblind accessibility needs.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.client`
- Classes to Modify/Create:
  - `ItemClumpsConfig.java`: Adds `labelHexColor` (default: `#FFFFFF`), `labelAlpha` (default: `0.9`), and color preset dropdown.
  - `ItemEntityRendererMixin.java`: Reads parsed ARGB color integer during `renderNameTag` matrix rendering pass.
  - `ColorHexParser.java`: Utility class parsing 6-digit and 8-digit hex color strings with saturation fallback.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Invalid hex strings in JSON configuration fall back gracefully to `#FFFFFF` without throwing NumberFormatException or rendering invisible black text.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Add `labelHexColor` and `labelAlpha` fields to `ItemClumpsConfig.java`.
2. Implement `ColorHexParser.java` with safe validation.
3. Inject parsed ARGB color into `ItemEntityRendererMixin.java`.
4. Unit test hex parsing against malformed strings.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
