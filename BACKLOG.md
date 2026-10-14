# 📌 Item Clumps Backlog

This file tracks planned features, technical refinements, performance optimizations, and deferred improvements for **Item Clumps**.

---

## 📊 Backlog Summary

| ID | Category | Title | Priority | Target Version | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `[BL-CLUMP-018]` | `[FEATURE]` | Multi-Era Anchor Porting (Scaffold Subprojects: 26.1, 1.21.11, 1.21.1, 1.20.1) | `[HIGH]` | `Multi-Era` | `✅ RESOLVED` |
| [BL-CLUMP-001] | [FEATURE] | Smooth Dynamic Visual Scaling (0.5x to custom cap, configurable) | [MEDIUM] | All Anchors | ✅ RESOLVED |
| [BL-CLUMP-002] | [REFINEMENT] | Clump Despawn Warning Effect (Final 30-60s visual indicator) | [MEDIUM] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-003] | [REFINEMENT] | Customizable Hex Color for Holographic Labels | [LOW] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-004] | [FEATURE] | Count-Scaled Pickup Audio Feedback (with In-Game Audio Settings toggle) | [MEDIUM] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-005] | [FEATURE] | Optional Batch Hopper Siphoning (1 to 64 items, default vanilla) | [HIGH] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-006] | [FEATURE] | Third-Party Modded Item Entity Auto-Detection & Merging | [MEDIUM] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-007] | [FEATURE] | Server Admin Command Suite (/clumps stats, /clumps purge, /clumps reload) | [HIGH] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-008] | [REFINEMENT] | Debug Clump Label Mode (Target Max Display e.g. 1,500/9,999) | [LOW] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-009] | [REFINEMENT] | Merge Particle Burst on Large Fusions (64+ items) | [LOW] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-010] | [PERF] | Explosion Drop Pre-Clumping on High Chunk Entity Density | [HIGH] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-011] | [FEATURE] | Configurable Blacklist & Datapack Tag (#c:unclumpable) | [HIGH] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-012] | [REFINEMENT] | Client-Side Glowing Outline Accessibility Toggle (YACL, default off) | [LOW] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-013] | [PERF] | Chunk Item Entity Density Console Warning Alert | [MEDIUM] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-014] | [REFINEMENT] | Crosshair / F3 Targeted Clump Stack Size Display | [LOW] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-015] | [PERF] | Holographic Label Wall-Occlusion Culling Toggle | [HIGH] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-016] | [REFINEMENT] | Spectator Mode Clump Hover HUD Readout | [LOW] | All Anchors | 📌 DEFERRED |
| [BL-CLUMP-017] | [FEATURE] | Jade / WTHIT / The One Probe Clump Provider Integration | [MEDIUM] | All Anchors | 📌 DEFERRED |
| **[BL-CLUMP-019]** | `[TECH_DEBT]` | Downstream Ecosystem & Toolchain Alignment | `[HIGH]` | All Anchors | `✅ RESOLVED` |
| `[BL-CLUMP-020]` | `[TECH_DEBT]` | BACKLOG.md Control-Character Corruption Repair & Formula Rendering | `[MEDIUM]` | Docs (No Anchor) | `✅ RESOLVED` |
| `[BL-CLUMP-021]` | `[TECH_DEBT]` | Adopt 5-Pillar Testing & Sidedness Verification Harness | `[MEDIUM]` | All Anchors | 📌 DEFERRED |
| `[BL-CLUMP-022]` | `[TECH_DEBT]` | Purge ModVersionGuard Reflection & String Reflection Runtime Hazards Across All Anchors | `[HIGH]` | `All Anchors` | `✅ RESOLVED` |

---

## 🏷 Legend & Status Tags
- **Categories**: [FEATURE], [REFINEMENT], [BUGFIX], [PERF], [TECH_DEBT]
- **Priorities**: [HIGH] (Significant impact/core mechanic), [MEDIUM] (Quality of life / immersion), [LOW] (Minor polish / debug)
- **Statuses**: 📌 DEFERRED (Queued for selection), 🚧 IN_PROGRESS (Active development), ✅ RESOLVED (Implemented and verified)

---

## 📝 Detailed Backlog Entries

### [BL-CLUMP-001] Smooth Dynamic Visual Scaling
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

---

### [BL-CLUMP-002] Clump Despawn Warning Effect
- **Category**: [REFINEMENT]
- **Priority**: [MEDIUM]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: ItemEntityRendererMixin.java, ItemClumpsConfig.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
When clumps sit idle without new items merging, players have no warning when the 5-minute (6000 tick) vanilla despawn timer is about to wipe thousands of gathered items.

#### 💡 Proposed Solution & Technical Specifications
- In the final 30–60 seconds (ticks 5400–6000), render visual indicators:
  - Holographic text color flashes or pulses red/yellow based on tick sine wave.
  - Subtle warning icon or flashing tag.
- Configurable toggle in YACL client settings.

#### 🧪 Verification & Acceptance Criteria
- [ ] Warning triggers only within configured threshold before despawn.
- [ ] Resets immediately if new items merge and reset entity age.

---

### [BL-CLUMP-003] Customizable Hex Color for Holographic Labels
- **Category**: [REFINEMENT]
- **Priority**: [LOW]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: ItemEntityMixin.java, ItemClumpsConfig.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Label colors are currently fixed to standard formatting codes with no user personalization.

#### 💡 Proposed Solution & Technical Specifications
- Keep default clean single color.
- Add hex color string config option in YACL (label_hex_color, default e.g. #FFFFFF or #FFD700).
- Parse and apply RGB formatting to the translatable/custom name component.

#### 🧪 Verification & Acceptance Criteria
- [ ] Hex string correctly parses into RGB text color.
- [ ] Invalid hex strings fallback gracefully to default color without crashing.

---

### [BL-CLUMP-004] Count-Scaled Pickup Audio Feedback
- **Category**: [FEATURE]
- **Priority**: [MEDIUM]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: ItemEntityMixin.java, ItemClumpsClient.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Picking up 1 item sounds identical to vacuuming 5,000 items in a single step.

#### 💡 Proposed Solution & Technical Specifications
- Play subtle pop/ding sound on player pickup with pitch slightly scaled based on the count absorbed.
- Controlled via client audio / game options toggle (accessible via Audio/Sound Settings, not locked to server GameRules).

#### 🧪 Verification & Acceptance Criteria
- [ ] Sound volume respects master/players audio sliders.
- [ ] Pitch clamp prevents extreme, ear-piercing audio ranges (0.5 <= pitch <= 2.0).

---

### [BL-CLUMP-005] Optional Batch Hopper Siphoning
- **Category**: [FEATURE]
- **Priority**: [HIGH]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: HopperBlockEntityMixin.java, ItemClumpsConfig.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Hoppers pull 1 item per 8-tick cycle. Moving a 10,000-item clump through hoppers takes thousands of seconds, creating massive sorting bottlenecks in automated mob farms.

#### 💡 Proposed Solution & Technical Specifications
- Default to vanilla behavior (1 item per pull).
- Provide a configurable setting / GameRule (hopper_pull_batch_size, range 1 to 64).
- When a hopper pulls from an ItemEntity with high count, extract up to hopper_pull_batch_size items into the hopper inventory in one transfer cycle.

#### 🧪 Verification & Acceptance Criteria
- [ ] Default 1 preserves vanilla redstone behavior and timings.
- [ ] Higher batch sizes fill available hopper slots without dropping items or deleting mega-stacks.

---

### [BL-CLUMP-006] Third-Party Modded Item Entity Compatibility
- **Category**: [FEATURE]
- **Priority**: [MEDIUM]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: ItemEntityMixin.java, ModCompatHelper.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Some mods extend ItemEntity or spawn custom ground items (e.g. Create conveyor belt packages, Applied Energistics crystal seeds) that might fail standard clumping checks or desync.

#### 💡 Proposed Solution & Technical Specifications
- Add dynamic reflection / interface checks to detect modded ItemEntity subclasses.
- Safely verify whether custom items support merging, preserving custom internal data/states.

#### 🧪 Verification & Acceptance Criteria
- [ ] Standard Create and AE2 items either merge safely or pass through untouched.
- [ ] Zero crash when foreign entity classes spawn.

---

### [BL-CLUMP-007] Server Admin Command Suite (/clumps)
- **Category**: [FEATURE]
- **Priority**: [HIGH]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: ItemClumpsCommands.java, ItemClumpsFabric.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Server administrators currently have no diagnostics to inspect how many clumps exist in a world or purge excessive drops during lag spikes.

#### 💡 Proposed Solution & Technical Specifications
- Register Brigadier command suite /clumps (aliases: /itemclumps):
  - /clumps stats: Lists total ground clumps, total condensed items, and highest-density chunks.
  - /clumps purge [radius|chunk|all]: Safely clears ground items or clamps oversized drops.
  - /clumps reload: Reloads config files from disk.

#### 🧪 Verification & Acceptance Criteria
- [ ] Permission levels enforced (level 2+ for ops).
- [ ] Tab-completion works seamlessly on dedicated servers.

---

### [BL-CLUMP-008] Debug Clump Label Mode (Target Max Display)
- **Category**: [REFINEMENT]
- **Priority**: [LOW]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: ItemEntityMixin.java, ItemClumpsConfig.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Power users and pack makers want to see how close a clump is to reaching max_clump_size.

#### 💡 Proposed Solution & Technical Specifications
- Default to simple count (Cobblestone x1,500).
- Add a debug label option to display target max format (Cobblestone 1,500/9,999).

#### 🧪 Verification & Acceptance Criteria
- [ ] When enabled, formats strings with current and max values.
- [ ] Reverts to clean simple count when disabled.

---

### [BL-CLUMP-009] Merge Particle Burst on Large Fusions
- **Category**: [REFINEMENT]
- **Priority**: [LOW]
- **Status**: 📌 DEFERRED
- **Target Component(s)**: ItemEntityMixin.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Merging items is completely silent and visually invisible.

#### 💡 Proposed Solution & Technical Specifications
- Only trigger when 64+ items merge at once to prevent particle spam.
- Spawn small vanilla puff/poof particles (ParticleTypes.POOF) at the clump centroid.

#### 🧪 Verification & Acceptance Criteria
- [ ] Single item drops do not spawn particles.
- [ ] Merges of >= 64 items emit a modest single-burst particle effect.

---

### [BL-CLUMP-010] Explosion Drop Pre-Clumping
- **Category**: [PERF]
- **Priority**: [HIGH]
- **Status**: 📌 DEFERRED
- **Target Component(s): ExplosionMixin.java, BlockMixin.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Massive TNT or Wither explosions break hundreds of blocks simultaneously, spawning hundreds of individual ItemEntity instances in the same tick before `tryToMerge` can consolidate them.

#### 💡 Proposed Solution & Technical Specifications
- Check entity count in the target chunk.
- If chunk entity count exceeds threshold, intercept drop loot tables or explosion block drops to spawn already consolidated clumps directly.

#### 🧪 Verification & Acceptance Criteria
- [ ] Detonating 100+ TNT blocks does not freeze the tick loop with item entity initialization.
- [ ] Drop totals match vanilla loot counts 100%.

---

### [BL-CLUMP-011] Configurable Blacklist & Datapack Tag (#c:unclumpable)
- **Category**: [FEATURE]
- **Priority**: [HIGH]
- **Status**: 📌 DEFERRED
- **Target Component(s): ItemEntityMixin.java, `tags/item/unclumpable.json`
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Certain puzzle items, modded machine inputs, or throwable items require separate individual entity tracking.

#### 💡 Proposed Solution & Technical Specifications
- Add common convention datapack tag: #c:unclumpable (and #item_clumps:unclumpable).
- Add config-based string identifier list in item-clumps.json.
- In `tryToMerge`, immediately bypass merging if either item matches the tag or config list.

#### 🧪 Verification & Acceptance Criteria
- [ ] Items in the tag never merge and behave as vanilla single entities.
- [ ] Tag can be modified dynamically via datapacks.

---

### [BL-CLUMP-012] Client Glowing Outline Accessibility Toggle
- **Category**: [REFINEMENT]
- **Priority**: [LOW]
- **Status**: 📌 DEFERRED
- **Target Component(s): ItemEntityRendererMixin.java, YaclScreenHelper.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Locating dark dropped items in deep caves or mob farms can be difficult for accessibility.

#### 💡 Proposed Solution & Technical Specifications
- Add client-side accessibility toggle in YACL config (default: 
alse).
- When enabled, applies glowing outline shader to clumps above a configurable threshold.

#### 🧪 Verification & Acceptance Criteria
- [ ] Strictly client-side; does not alter server entity data or broadcast packets.
- [ ] Defaults to off.

---

### [BL-CLUMP-013] Chunk Item Entity Density Console Warning Alert
- **Category**: [PERF]
- **Priority**: [MEDIUM]
- **Status**: 📌 DEFERRED
- **Target Component(s): ServerWorldMixin.java, ItemClumpsFabric.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Runaway mob farms or broken redstone droppers can flood a single chunk with items, lagging the server before admins notice.

#### 💡 Proposed Solution & Technical Specifications
- Default to none (vanilla behavior).
- Add configurable threshold (chunk_density_alert_threshold, default: disabled).
- When a chunk exceeds the threshold, throttle-log an alert to the server console citing chunk coordinates.

#### 🧪 Verification & Acceptance Criteria
- [ ] Logs at most once every 60 seconds per chunk to prevent console spam.
- [ ] Disabled by default.

---

### [BL-CLUMP-014] Crosshair / F3 Targeted Clump Stack Size Display
- **Category**: [REFINEMENT]
- **Priority**: [LOW]
- **Status**: 📌 DEFERRED
- **Target Component(s): DebugHudMixin.java / ItemClumpsClient.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
When looking directly at an entity with F3 debug screen active, standard vanilla debug info only shows base entity type without clump metadata.

#### 💡 Proposed Solution & Technical Specifications
- In F3 targeted entity screen or HUD tooltip, display exact clump count, despawn age, and merge state.

#### 🧪 Verification & Acceptance Criteria
- [ ] F3 right-hand side displays clump_count: <num> when raycast targets an ItemEntity.

---

### [BL-CLUMP-015] Holographic Label Wall-Occlusion Culling
- **Category**: [PERF]
- **Priority**: [HIGH]
- **Status**: 📌 DEFERRED
- **Target Component(s): ItemEntityRendererMixin.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Vanilla name tags and labels render through walls. Players near large automated farms see dozens of floating text labels through solid stone/concrete walls, causing visual clutter and render overhead.

#### 💡 Proposed Solution & Technical Specifications
- Add config toggle (cull_occluded_labels, default: `true`).
- Perform lightweight raycast or camera line-of-sight check before rendering the text label matrix.
- Hide label if solid opaque blocks obstruct the line of sight between the camera and the item.

#### 🧪 Verification & Acceptance Criteria
- [ ] Labels behind walls disappear completely.
- [ ] Reappears instantly as soon as line of sight is restored.

---

### [BL-CLUMP-016] Spectator Mode Clump Hover HUD Readout
- **Category**: [REFINEMENT]
- **Priority**: [LOW]
- **Status**: 📌 DEFERRED
- **Target Component(s): ItemClumpsClient.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Spectators inspecting farms or servers want instant telemetry without digging into NBT or opening consoles.

#### 💡 Proposed Solution & Technical Specifications
- When in Spectator mode and crosshair is hovering over a clump, display a clean actionbar or HUD overlay with item name, count, and despawn countdown.

#### 🧪 Verification & Acceptance Criteria
- [ ] Only active for Spectators (player.isSpectator()).
- [ ] Disappears when looking away.

---

### [BL-CLUMP-017] Jade / WTHIT / The One Probe Clump Provider
- **Category**: [FEATURE]
- **Priority**: [MEDIUM]
- **Status**: 📌 DEFERRED
- **Target Component(s): compat/JadePlugin.java, compat/WthitPlugin.java
- **Date Added**: 2026-09-13

#### ❓ Problem / Context
Players using standard info-overlay mods (Jade, WTHIT, TOP) see generic vanilla ItemEntity tooltips rather than native clump counts and despawn timers.

#### 💡 Proposed Solution & Technical Specifications
- Dynamically register custom entity component providers for Jade and WTHIT if present.
- Display formatted count and despawn progress bar inside the tooltip box.

#### 🧪 Verification & Acceptance Criteria
- [ ] Loads cleanly via optional entrypoint without hard crash if Jade/WTHIT are not installed.
- [ ] Formats stack count cleanly inside the overlay.

---

### [BL-CLUMP-018] Multi-Era Anchor Porting: Item Clumps
- **Category**: `[FEATURE]`
- **Priority**: `[HIGH]`
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: Multi-subproject directories, `ItemEntityMixin.java`, `ClumpMergeHandler.java`, `ClumpRenderer.java`, `ClumpConfig.java`, `build.gradle`, `fabric.mod.json`, `RELEASE_QUEUE.md`
- **Date Added**: 2026-09-25
- **Roadmap Tracker**: [`item_clumps_multi_era_porting_roadmap.md`](file:///C:/Users/fmrif/.gemini/antigravity/brain/cdc9efa4-763b-4a89-9682-450c0e8ea2ac/item_clumps_multi_era_porting_roadmap.md) (6 Atomic Steps)

#### ❓ Problem / Context
Existing versions: `26.2` (established baseline at root). Missing anchors: Modern `26.1 / 26.1.2`, `26.3`; Older `1.21.11`, `1.21.1`, `1.20.1`.
Per Multi-Era Version Matrix and 1 Jar 1 Version Policy, port mod across all missing anchors (Modern first, Older second).

#### 💡 Architectural Specifications & Toolchain Anchors
- **Phase 1: Modern Sovereign Anchors (Java 25+, Loom 1.15+, No Mappings Block)**:
  - `MC 26.1 / 26.1.2`: Java 25, Fabric Loom 1.15+, `Identifier.fromNamespaceAndPath`, `EntityTypes`, `dasik-library` 26.1.
  - `MC 26.2`: Java 25, Fabric Loom 1.15+, `Identifier.fromNamespaceAndPath`, `DynamicGameRuleManager` (already active baseline).
  - `MC 26.3`: Java 25, Fabric Loom 1.15+, `minecraft_version=26.3-snapshot-6`, `fabric_version=0.156.1+26.3`.
- **Phase 2: Older Anchors (Mojang Mappings, Java 21 / 17)**:
  - `MC 1.21.11`: Java 21, Loom 1.15-SNAPSHOT (`fabric-loom-remap`), Mojang mappings, `Identifier.of`, `Optional<T>` CompoundTag, relocated entity packages.
  - `MC 1.21.1`: Java 21, Loom 1.10+, Mojang mappings, `Identifier.of`, `DataComponents`, native `Attributes.SCALE`.
  - `MC 1.20.1`: Java 17, Loom 1.4–1.10, Mojang mappings, `new Identifier`, primitive NBT CompoundTag, `FabricItemSettings`, `GameRules.Category` enum.

#### 🧪 Verification & Acceptance Criteria

##### Phase 1: Modern Sovereign Anchors (Priority 1)
- [x] **Anchor: MC 26.1 / 26.1.2**
  - [x] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [x] Source adaptation, API/mixin relocation, and dasik-library wiring for target version
  - [x] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [x] Clean binary compilation (`./gradlew build --no-daemon`)
  - [x] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [x] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry
- [x] **Anchor: MC 26.2 - Baseline Subproject Isolated** (`Item Clumps v26.2/`)
  - [x] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [x] Source adaptation, API/mixin relocation, and dasik-library wiring for target version (`1.0.28+26.2`)
  - [x] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [x] Clean binary compilation (`./gradlew build --no-daemon`)
  - [x] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [x] Release queue registration in `RELEASE_QUEUE.md`
- [x] **Anchor: MC 26.3 - Subproject Isolated** (`Item Clumps v26.3/`)
  - [x] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [x] Source adaptation, API/mixin relocation, and dasik-library wiring for target version (`1.0.29+26.3`)
  - [x] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [x] Clean binary compilation (`./gradlew build --no-daemon`)
  - [x] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [x] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry

##### Phase 2: Older Anchors (Priority 2)
- [x] **Anchor: MC 1.21.11 (Java 21, Loom 1.15-SNAPSHOT `fabric-loom-remap`, Mojang mappings, `Identifier.of`, `Optional<T>` CompoundTag, relocated entity packages)**
  - [x] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [x] Source adaptation, API/mixin relocation, and dasik-library wiring for target version
  - [x] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [x] Clean binary compilation (`./gradlew build --no-daemon`)
  - [x] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [x] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry
- [x] **Anchor: MC 1.21.1 (Java 21, Loom 1.10+, Mojang mappings, `Identifier.of`, `DataComponents`, native `Attributes.SCALE`)**
  - [x] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [x] Source adaptation, API/mixin relocation, and dasik-library wiring for target version
  - [x] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [x] Clean binary compilation (`./gradlew build --no-daemon`)
  - [x] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [x] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry
- [x] **Anchor: MC 1.20.1 (Java 17, Loom 1.4-1.10, Mojang mappings, `new Identifier`, primitive NBT CompoundTag, `FabricItemSettings`, `GameRules.Category` enum)**
  - [x] Subproject directory & build script scaffolding (`build.gradle`, `gradle.properties`, `settings.gradle`)
  - [x] Source adaptation, API/mixin relocation, and dasik-library wiring for target version
  - [x] Headless unit & integration test suite pass (`./gradlew test --no-daemon`)
  - [x] Clean binary compilation (`./gradlew build --no-daemon`)
  - [x] Mandatory Universal 4-Point Distribution (Local Archive, Hub Archive, External Vault `D:\`, Launcher Test Profile)
  - [x] Release queue registration in `RELEASE_QUEUE.md` (`- [ ]`) and `CHANGELOG.md` entry

### [BL-CLUMP-019] Downstream Ecosystem & Toolchain Alignment: watched_projects, fabric.mod.json, Queue Segregation & Archive Hierarchy
- **Category**: `[TECH_DEBT]`
- **Priority**: `[HIGH]`
- **Target Version**: All Anchors
- **Status**: `✅ RESOLVED`
- **Date Added**: 2026-10-01
- **Problem / Context**:
  Across the studio release pipeline and downstream automation tools, systemic inconsistencies exist:
  1. `dasik-mod-sync/watched_projects.json`: Many mods contain broken `icon_path` mappings (non-existent nested folder references) or stale `versions` arrays that omit active anchors.
  2. `fabric.mod.json`: Platform metadata (`custom.modrinth.projectId`, `slug`, repository URLs) occasionally drifts from authoritative entries in `minecraft-mod-release-hub/config/platform_projects.json`.
  3. `RELEASE_QUEUE.md`: Multi-version subproject directories often retain predecessor anchor changelog headers, violating the Subproject Queue Segregation Law.
  4. Archive Structure: Collection roots lack standardized `Archive Jar of all versions/` hierarchies (`MC <Version>/`), preventing `sync_archives.py` from auto-discovering compiled builds for release hub and external vault distribution.
- **Proposed Solution & Technical Specifications**:
  1. Audit and correct `icon_path` and `versions` array in `dasik-mod-sync/watched_projects.json` for item-clumps.
  2. Align `fabric.mod.json` across all active subprojects with official `platform_projects.json` metadata.
  3. Purge predecessor version headers from subproject `RELEASE_QUEUE.md` and `CHANGELOG.md` files (strict Version Anchor Exclusivity).
  4. Scaffold/verify collection root `Archive Jar of all versions/` containing dedicated `MC <Version>/` folders with existing release JARs mirrored.
  5. Scaffold root `MASTER_RELEASE_QUEUE.md` multi-anchor dashboard where absent.
- **Verification & Acceptance Criteria**:
  - [x] `watched_projects.json` icon path physically exists on disk and `versions` matches active anchors.
  - [x] `fabric.mod.json` metadata strictly aligns with `platform_projects.json`.
  - [x] Subproject `RELEASE_QUEUE.md` files contain strictly target-anchor release entries.
  - [x] Root `Archive Jar of all versions/` exists and contains release JARs discoverable by `sync_archives.py`.

### [BL-CLUMP-020] BACKLOG.md Control-Character Corruption Repair & Formula Rendering
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Target Version**: Docs (No Anchor — no JAR, no SemVer bump)
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: `item-clumps/BACKLOG.md`, `MASTER_BACKLOG_BOARD.md`
- **Date Added**: 2026-10-03

#### ❓ Problem / Context
A prior write pipeline interpreted backslash sequences as escape codes: backslash-t became a literal TAB (`0x09`) and backslash-0 became a NUL byte (`0x00`). This silently corrupted ticket specifications (e.g. `true` renders as `<TAB>rue`, `0.1x` loses its leading digit). NUL bytes can also break downstream parsers (`sync_backlog_board.py`, regex scrapers, Git diffs treating the file as binary).

Additionally, the `[BL-CLUMP-001]` scaling formula is raw LaTeX without `$$` delimiters, so it does not render.

#### 💡 Proposed Solution & Technical Specifications
Replace corrupted characters with backtick-wrapped literals (spec meaning unchanged):

| Ticket | Corrupted Text | Restored Text |
| :--- | :--- | :--- |
| `[BL-CLUMP-001]` | `default: <TAB>rue` | `default: true` |
| `[BL-CLUMP-001]` | `from <NUL>.1x … from <NUL>.5x` | `from 0.1x … from 0.5x` |
| `[BL-CLUMP-010]` | `before <TAB>ryToMerge` | `before tryToMerge` |
| `[BL-CLUMP-011]` | `<TAB>ags/item/unclumpable.json` | `tags/item/unclumpable.json` |
| `[BL-CLUMP-011]` | `In <TAB>ryToMerge` | `In tryToMerge` |
| `[BL-CLUMP-015]` | `default: <TAB>rue` | `default: true` |

- Wrap the `[BL-CLUMP-001]` scaling formula in `$$ … $$` so it renders as display math.
- Preserve CRLF line endings and UTF-8 encoding (no BOM change).
- Re-run `python .agents/skills/backlog-manager/scripts/sync_backlog_board.py` so `MASTER_BACKLOG_BOARD.md` reflects clean ticket titles.
- **Root-cause guard**: When authoring backlog text, never emit bare backslash escapes (backslash-t, backslash-0, backslash-n) outside of code spans or math delimiters.

#### 🧪 Verification & Acceptance Criteria
- [x] Control-character scan returns zero hits (any char `< 0x20` other than CR/LF) across `item-clumps/BACKLOG.md`.
- [x] All 6 corrupted locations read correctly; ticket scope/specs unchanged.
- [x] `[BL-CLUMP-001]` formula renders as math in Markdown preview.
- [x] `MASTER_BACKLOG_BOARD.md` re-synced.
- [x] Workspace-wide sweep: other `BACKLOG.md` files scanned for the same corruption; any hits spawn companion tickets in their own project backlogs. *(117 `.md` files scanned 2026-10-03 — zero other hits, no companion tickets needed.)*

---

### [BL-CLUMP-021] Adopt 5-Pillar Testing & Sidedness Verification Harness
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Status**: `✅ RESOLVED`
- **Target Version**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`)
- **Date Added**: 2026-10-14
- **Date Resolved**: 2026-10-14

#### ❓ Problem / Context
Citing `Testing & Runtime Verification Policy.md` and `gradle-tester/SKILL.md`, existing tests do not fully guard against the 5 Core Failure Modes (Headless Sidedness, Dynamic Mixin Transformation Drift, Emergent State, Concurrency, and Test Oracle limitations).

#### 💡 Proposed Solution
1. **Pillar 1**: Implement `ItemClumpsStressFuzzTest.java` (chaos property fuzzing with 20,000 iterations, NaN/Infinity bounds, negative counts, 64-bit overflow saturation).
2. **Pillar 2**: Implement `ItemClumpsConcurrencyLoadTest.java` (50 concurrent worker threads, atomic quotas).
3. **Pillar 5**: Implement `ItemClumpsSidednessContractTest.java` (bytecode constant-pool scan verifying zero `net.minecraft.client.*` or `com.mojang.blaze3d.*` imports in common packages).

#### 🧪 Verification & Acceptance Criteria
- [x] 5-Pillar test suite passes on `./gradlew test --no-daemon` (48 / 48 tests across 6 anchors).
- [x] Zero client class references in common code.
- [x] Clean build and deploy across all targeted anchors.


---

### [BL-CLUMP-022] Purge ModVersionGuard Reflection & String Reflection Runtime Hazards Across All Anchors
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
