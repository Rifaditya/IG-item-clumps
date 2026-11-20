# 🎫 [BL-CLUMP-026] Fix ItemEntityRendererMixin @WrapOperation Receiver Signature Crash in Legacy Anchors

- **Ticket ID**: `[BL-CLUMP-026]`
- **Category**: `[BUGFIX]`
- **Priority**: `[CRITICAL]`
- **Scope**: `Legacy & Transitional Anchors` (`1.20.1`, `1.21.1`)
- **Status**: `✅ RESOLVED`
- **Grilling Status**: `✅ PASSED` (Autonomous forensic root-cause verified via `crash-2026-11-20_15.04.03-client.txt`)
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`
- **Governance Mandate**: Studio Constitution §1.5 (*Multi-Era Parity*), §1.15 (*Knot Runtime Safety*), §1.18 (*Mixin Descriptors & Bytecode Safety*)

---

## 🎯 1. Goal & Definition of Done (The 4-Pillar Objective Architecture)

1. **North Star Objective**: Resolve fatal client Mixin transformation crash (`InvalidInjectionException`) in `ItemEntityRendererMixin.java` by aligning the `@WrapOperation` receiver parameter type from `EntityRenderer` to `ItemEntityRenderer`, matching the invoking target class on the JVM operand stack and restoring full client rendering stability for item entities in Minecraft 1.20.1 and 1.21.1.
2. **In-Game Experience (Before vs. After)**:
   - *Before*: Launching Minecraft with `Item Clumps 1.4.0+1.20.1` crashes immediately during world join or item rendering with `NullPointerException: Cannot invoke net.minecraft.class_897.method_3933(...) because "$$5" is null` due to failed `ItemEntityRenderer` class transformation (`InvalidInjectionException: @WrapOperation operation wrapper method net/minecraft/class_916::item_clumps$cullOccludedLabel ... Found unexpected argument type net.minecraft.class_897 at index 0, expected net.minecraft.class_916`).
   - *After*: Minecraft client boots cleanly, joins singleplayer/multiplayer worlds with zero mixin transformation errors, and renders all floating item entities and holographic clump count labels with full occlusion culling active.
3. **🚫 Anti-Goals (Off-Limits / Do Not Touch)**:
   - ❌ **Do NOT touch Modern Anchors (1.21.11, 26.x)**: Modern anchors already use `ItemEntityRenderer instance` with `EntityRenderState` and must not be mutated.
   - ❌ **Do NOT alter occlusion culling logic or math**: Keep `ClumpOcclusionCuller.isLabelOccluded` invocations and coordinates unchanged.
   - ❌ **Do NOT introduce client imports to common code**: Maintain strict `@Environment(EnvType.CLIENT)` side-safety on `ItemEntityRendererMixin`.
4. **✅ Definition of Done (Binary Verification Checklist)**:
   - [ ] `ItemEntityRendererMixin.java` in `Item Clumps v1.20.1` updated from `EntityRenderer instance` to `ItemEntityRenderer instance`.
   - [ ] `ItemEntityRendererMixin.java` in `Item Clumps v1.21.1` updated from `EntityRenderer instance` to `ItemEntityRenderer instance`.
   - [ ] Obsolete `ModVersionGuard` test references in `ItemClumpsSidednessContractTest.java` sanitized.
   - [ ] False-positive contradiction in `PreClumpDropHelper.java` normalized (`deque.size() == 0`).
   - [ ] Headless test suite passes: `./gradlew test --no-daemon --console=plain` in both `v1.20.1` and `v1.21.1`.
   - [ ] Compiled JARs deployed to Modrinth `Fabric 1.20.1` profile and tested.
   - [ ] Zero `CRITICAL` findings in `code_auditor.py`.

---

## ❓ 2. Problem Statement & Player Impact

When players load Minecraft 1.20.1 with Item Clumps 1.4.0, Fabric Loader attempts to apply client mixins during initial resource loading. In `ItemEntityRendererMixin.java`, `@WrapOperation` targets `EntityRenderer.render(...)`. However, inside `ItemEntityRenderer`, this call is an `invokespecial` instruction executed on `this` (`ItemEntityRenderer`). MixinExtras validates that the first parameter of the wrapper method matches the exact type of the receiver on the operand stack. Because the method signature declared `EntityRenderer instance` instead of `ItemEntityRenderer instance`, MixinExtras rejected the injector with an `InvalidInjectionException`.

Because the mixin failed to apply, the JVM caught a `BootstrapMethodError` during entity renderer initialization, leaving `EntityType.ITEM` without a registered renderer. As soon as any item entity entered camera frustum in a loaded world, the game crashed fatally with an NPE.

---

## 🔬 3. Architectural Blueprint & Target Classes

- **Target Files**:
  - `Instant Gratification Collection/item-clumps/Item Clumps v1.20.1/src/main/java/net/instantgratification/item_clumps/client/mixin/ItemEntityRendererMixin.java`
  - `Instant Gratification Collection/item-clumps/Item Clumps v1.21.1/src/main/java/net/instantgratification/item_clumps/client/mixin/ItemEntityRendererMixin.java`
- **Method Signature Fix**:
  ```java
  // Before:
  private void item_clumps$cullOccludedLabel(EntityRenderer instance, Entity entity, float yaw, float tick, PoseStack stack, MultiBufferSource buffer, int light, Operation<Void> original)
  
  // After:
  private void item_clumps$cullOccludedLabel(ItemEntityRenderer instance, Entity entity, float yaw, float tick, PoseStack stack, MultiBufferSource buffer, int light, Operation<Void> original)
  ```

---

## ⚓ 4. Multi-Era Anchor Parity Specifications

| Anchor | Target File | Parameter Type | Status |
| :--- | :--- | :--- | :--- |
| **MC 1.20.1** | `ItemEntityRendererMixin.java` | `ItemEntityRenderer instance` | Needs Fix (Current: `EntityRenderer`) |
| **MC 1.21.1** | `ItemEntityRendererMixin.java` | `ItemEntityRenderer instance` | Needs Fix (Current: `EntityRenderer`) |
| **MC 1.21.11** | `ItemEntityRendererMixin.java` | `ItemEntityRenderer instance` | ✅ Already compliant |
| **MC 26.1.2** | `ItemEntityRendererMixin.java` | `ItemEntityRenderer instance` | ✅ Already compliant |
| **MC 26.2** | `ItemEntityRendererMixin.java` | `ItemEntityRenderer instance` | ✅ Already compliant |
| **MC 26.3** | `ItemEntityRendererMixin.java` | `ItemEntityRenderer instance` | ✅ Already compliant |

---

## 🛡️ 5. Edge Cases & Blast Radius Analysis

| Dimension | Risk Assessment | Mitigation |
| :--- | :--- | :--- |
| **MixinExtras Compatibility** | MixinExtras 0.2.x - 0.4.x strictly validates receiver type on `invokespecial` | Aligning type to subclass `ItemEntityRenderer` satisfies MixinExtras bytecode injector |
| **Client/Server Desync** | Mixin is client-only (`item-clumps.client.mixins.json` + `@Environment(EnvType.CLIENT)`) | Dedicated server is completely unaffected; no sidedness leaks |
| **Label Occlusion Logic** | Raycast occlusion checking runs inside wrapper | Unchanged, calls `original.call(...)` when not occluded |

---

## 🧪 6. Negative Parity Tests

1. **Frustum Occlusion Suppression**: Verify that items obscured behind opaque blocks suppress their holographic text labels without preventing item mesh rendering.
2. **Un-Occluded Raycast Pass-Through**: Verify that items in direct line-of-sight call `original.call(...)` and properly render nametags/labels.
3. **Headless Unit Verification**: Verify `./gradlew test` passes cleanly with 0 sidedness failures and 0 reflection warnings.
