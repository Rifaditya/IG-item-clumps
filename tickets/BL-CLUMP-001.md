# 🎫 [BL-CLUMP-001] Smooth Dynamic Visual Scaling

- **Category**: [FEATURE]
- **Priority**: [MEDIUM]
- **Status**: ✅ RESOLVED
- **Target Version**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`)
- **Target Component(s)**: `ItemEntityRendererMixin.java`, `VisualScalingHelper.java`, `ItemEntityRenderStateMixin.java`, `ItemClumpsFabric.java`, `ItemClumpsConfig.java`
- **Date Added**: 2026-09-13
- **Date Resolved**: 2026-10-14 (v1.1.0)

#### ❓ Problem / Context
All item clumps render at vanilla scale regardless of whether they hold 1 item or 10,000 items. There is no visual sense of volume for giant drops.

#### 💡 Proposed Solution & Technical Specifications
- Inject into client item entity rendering to dynamically apply matrix scale.
- Configurable toggle (`item_clumps:enable_visual_scaling`, default: `true`) and client YACL config.
- Smooth logarithmic formula:
  $$\text{scale} = \text{clamp}(\text{minScale} + k \cdot \log_{10}(\text{count}), \text{minScale}, \text{maxScale})$$
  Default $\text{minScale} = 1.0F, \text{maxScale} = 2.5F, k = 0.3F$.
- Renderpearl Duck interface on modern anchors (`26.1.2`, `26.2`, `26.3`, `1.21.11`), direct injection after `pushPose()` on older anchors (`1.20.1`, `1.21.1`).

#### 🧪 Verification & Acceptance Criteria
- [x] Visual scale scales smoothly without sudden jump frames.
- [x] Config toggle disables scaling back to vanilla 1.0x.
- [x] Boundary inputs (0.1x to large scales) do not clip through ground hitboxes or crash OpenGL matrices.
- [x] Headless unit tests (`VisualScalingHelperTest.java`) pass across all 6 anchors.
- [x] Released in version `1.1.0` across all 6 version anchors.
