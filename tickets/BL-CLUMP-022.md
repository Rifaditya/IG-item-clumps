# 🎫 [BL-CLUMP-022] Purge ModVersionGuard Reflection & String Reflection Runtime Hazards Across All Anchors

- **Category**: `[TECH_DEBT]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Version**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`)
- **Target Component(s)**: `ItemClumpsFabric.java`, `ModVersionGuard.java` across all 6 subprojects
- **Date Added**: 2026-10-14

#### ❓ Problem / Context
In `ItemClumpsFabric.java`, `ModVersionGuard.checkClass("Item Clumps", "net.minecraft.world.entity.item.ItemEntity")` and `Class.forName("net.dasik.social.api.config.ConfigHelper")` execute unremapped string reflection at runtime. Under production Knot ClassLoader environments, unremapped class names throw `ClassNotFoundException`, crashing client and server initializers. Citing the **Anti-Unremapped-Reflection Law** and **Comprehensive Legacy Guard Purge Invariant**, `ModVersionGuard.java` and all runtime reflection checks must be eradicated across all anchors.

#### 💡 Proposed Solution & Technical Specifications
1. Purge `net.instantgratification.item_clumps.util.ModVersionGuard.java` completely from all 6 subprojects.
2. Remove `ModVersionGuard.checkClass(...)` and `Class.forName(...)` invocations from `ItemClumpsFabric.java`.
3. Rely strictly on `fabric.mod.json` dependency declarations for runtime version gating.

#### 🧪 Verification & Acceptance Criteria
- [x] `ModVersionGuard.java` deleted from all 6 subprojects.
- [x] `ItemClumpsFabric.java` initializes cleanly without string reflection.
- [x] `./gradlew test --console=plain` passes cleanly across all 6 anchors.
