# 🎫 [BL-CLUMP-005] Optional Batch Hopper Siphoning

- **Ticket ID**: `[BL-CLUMP-005]`
- **Category**: `[REFINEMENT]`
- **Priority**: `[MEDIUM]`
- **Scope**: `All 7 Master Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`, `26.x`)
- **Status**: `📌 DEFERRED`
- **Grilling Status**: `✅ PASSED` (Verified via `/grill-me`: Redstone threshold guard >= 64, single-slot saturated fill)
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`
- **Governance Mandate**: Studio Constitution §1.5 (*Multi-Era Parity*), §1.10 (*Freedom Over Anti-Crash*), §1.9 (*Omni-Channel Config*)

---

## 🎯 1. Goal & Definition of Done (The 4-Pillar Objective Architecture)

1. **North Star Objective**: Provide an opt-in, redstone-safe batch siphoning mechanism for hoppers underneath mega-clumps ($\ge 64$ items), allowing configurable batch transfers (default: 16 items per 8 ticks) to eliminate massive item entity bottlenecks without breaking vanilla redstone mechanics.
2. **In-Game Experience (Before vs. After)**:
   - *Before*: A hopper underneath a mob farm drop chute with 2,000 clumped items pulls only 1 item every 8 ticks, taking >13 minutes to drain the clump and leaving large entity counts lingering in the world.
   - *After*: When enabled by a server admin, hoppers drain clumps in single-slot batches (16 items/cycle by default), clearing 2,000 items in ~50 seconds. Small item drops ($<64$ items) continue pulling strictly 1-by-1, leaving player shops, clocks, and redstone contraptions 100% intact.
3. **🚫 Anti-Goals (Off-Limits / Do Not Touch)**:
   - ❌ **Do NOT touch container-to-container hoppers**: Never hook container transfers (`Hopper -> Hopper`, `Chest -> Hopper`). Injections are strictly limited to `addItem(Container, ItemEntity)` on floating world entities.
   - ❌ **Do NOT enable by default**: Must default to `false` (`item_clumps:batch_hopper_siphoning = false`) to guarantee vanilla parity out of the box.
   - ❌ **Do NOT batch small redstone drops**: When an item entity has fewer items than `item_clumps:batch_siphon_min_threshold` (default: 64), batching MUST NOT engage.
   - ❌ **Do NOT overflow slots or bypass stack limits**: Respect `maxStackSize` (tools = 1, ender pearls = 16) and remaining target slot capacity. Saturated arithmetic prevents item duplication or voiding.
4. **✅ Definition of Done (Binary Verification Checklist)**:
   - [ ] Dynamic GameRules registered via `DynamicGameRuleManager`: `item_clumps:batch_hopper_siphoning` (default: `false`), `item_clumps:hopper_batch_size` (default: `16`), and `item_clumps:batch_siphon_min_threshold` (default: `64`).
   - [ ] Config defaults stored in `ItemClumpsConfig.java` (`item-clumps.json`) and exposed via YACL GUI screen.
   - [ ] Mixin in `HopperBlockEntityMixin.java` pulls single-slot saturated batches only when enabled and threshold is met.
   - [ ] Non-stackable (swords) and 16-stack (pearls) items verified to never exceed their slot limits.
   - [ ] Headless unit and GameTest verification passes across all 7 Master Anchors (`./gradlew test`).
   - [ ] 0 client imports in common code verified via Pillar 5 sidedness audit.

---

## ❓ 2. Problem Statement & Player Impact

In vanilla Minecraft, hoppers siphon exactly 1 item every 8 game ticks (2.5 items/second) from floating item entities. While balanced for individual item drops, this creates severe bottlenecks in high-output mob grinders and quarry farms where **Item Clumps** merges thousands of drops into single entities. Draining a 2,000-item clump takes over 13 minutes, causing items to accumulate faster than they can be collected.

Enabling batch siphoning resolves farm lag, but naive implementations break vanilla redstone builds (metered shops, hopper clocks, and item counters). This ticket establishes an opt-in, dual-guarded architecture: disabled by default, and protected by a minimum clump size threshold when enabled.

---

## 🔬 3. Architectural Blueprint & Target Classes

- **Target Packages**:
  - `net.instantgratification.item_clumps.mixin.HopperBlockEntityMixin`
  - `net.instantgratification.item_clumps.ItemClumpsFabric`
  - `net.instantgratification.item_clumps.config.ItemClumpsConfig`
  - `net.instantgratification.item_clumps.config.YaclScreenHelper`
- **Dynamic GameRules (Omni-Channel Config)**:
  - `item_clumps:batch_hopper_siphoning` (Boolean, default: `false`): Toggles batch siphoning feature.
  - `item_clumps:hopper_batch_size` (Integer, default: `16`, bounds: `[1, Integer.MAX_VALUE]`): Maximum items pulled per 8-tick cycle, capped by slot capacity.
  - `item_clumps:batch_siphon_min_threshold` (Integer, default: `64`, bounds: `[1, Integer.MAX_VALUE]`): Minimum entity stack count required to trigger batch siphoning.
- **Transfer Sizing & Saturated Arithmetic**:
  ```java
  int entityCount = entity.getItem().getCount();
  boolean batchActive = isBatchEnabled && entityCount >= minThreshold;
  
  int targetBatch = batchActive ? Math.min(entityCount, batchSize) : 1;
  // Calculate remaining capacity in first matching or empty slot
  int maxTransfer = Math.min(targetBatch, slotCapacity);
  ```

---

## ⚓ 4. Multi-Era Anchor Parity Specifications

This ticket applies across all **7 Master Anchors**:
1. **Anchor 1 (`1.20.1`)**: Legacy Long-Term Support (Java 17, `ItemStack.setCount()`, primitive NBT, Fabric Loader 0.15+).
2. **Anchor 2 (`1.21.1`)**: Transitional Early (Java 21, `ItemStack.copyWithCount()`, DataComponents).
3. **Anchor 3 (`1.21.11`)**: Transitional Late / Winter Drop (Java 21, DataComponents).
4. **Anchor 4 (`26.1.2`)**: Modern Initial Sovereign (Java 25, DataComponents, dynamic GameRules).
5. **Anchor 5 (`26.2`)**: Modern Intermediate Sovereign (Java 25, Loom 1.15+).
6. **Anchor 6 (`26.3`)**: Modern Standard Sovereign (Java 25, pinned).
7. **Anchor 7 (`26.x`)**: Modern Sovereign Rolling Lead (Java 25, rolling `>=26.3-`).

---

## 🛡️ 5. Edge Cases, Collisions & Failure Modes (Redstone Blast Radius)

### Redstone Impact Matrix (When Enabled)
| Contraption | Vanilla Behavior | Risk When Enabled | Mitigation in this Ticket |
|:---|:---|:---|:---|
| **Item-Metered Shops / Casinos** | Locked hopper pulsed for 4–8 ticks pulls exactly 1 payment coin | High: Siphons 16 coins per pulse if batched | **Protected**: Payment drops are $<64$ items, falling below `batch_siphon_min_threshold` |
| **Pulse Extenders & Item Clocks** | Timed based on $N \times 8$ ticks for dropped item count | High: Timing collapses to 8 ticks | **Protected**: Clock items are dropped in small counts ($<64$), falling back to vanilla 1-by-1 |
| **Parallel Water-Stream Siphons** | 8 hoppers in a row siphon 1 item each as items float past | Medium: Hopper #1 vacuums 16 items, starving downstream hoppers | Documented in GameRule description; only applies to farm clumps $\ge 64$ |
| **Standard Tileable Sorters (ImpulseSV)** | Filter slot holds 41 items + 4 dummy items ($45/320$ capacity = SS 2) | None: Siphoning up to 23 items ($68/320$ capacity) reaches SS 3, never bleeding SS 4 crosstalk | Single-slot saturated fill prevents multi-slot overflow |
| **Container-to-Container Hoppers** | Hopper pulling from chest or hopper pipeline | Zero: Untouched | Mixin hooks exclusively `ItemEntity` siphoning, zero container code modified |

### Technical Edge Cases
- **Non-Stackable Items (Swords, Armor)**: `maxTransfer` is saturated to `Math.min(targetBatch, itemStack.getMaxCount() - slotCount)`. A sword will never transfer $>1$.
- **16-Stack Items (Ender Pearls, Signs)**: Capped by `maxCount = 16`. Even if batch size is 64, maximum transfer into an empty slot is 16.
- **Corrupted Config Fallback**: Missing or malformed config entries safely fall back to defaults (`false`, `16`, `64`) per Freedom Over Anti-Crash policy.

---

## 🚀 6. Implementation Steps (Code-Level Plan)

1. **Config & GameRule Foundation**:
   - Add fields `batchHopperSiphoning`, `hopperBatchSize`, and `batchSiphonMinThreshold` to `ItemClumpsConfig.java`.
   - Register dynamic GameRules in `ItemClumpsFabric.java` using `DynamicGameRuleManager`.
   - Add toggle and slider controls in `YaclScreenHelper.java`.
2. **Batch Siphoning Mixin (`HopperBlockEntityMixin.java`)**:
   - Refactor `item_clumps$customHopperExtract` in `HopperBlockEntityMixin.java`.
   - Read GameRule values from world level.
   - If enabled and `entity.getCount() >= minThreshold`, compute single-slot saturated batch transfer size.
   - Shrink entity stack by successfully transferred count and update entity.
3. **Multi-Era Lockstep Porting**:
   - Verify `ItemStack.setCount()` on `1.20.1` vs `copyWithCount()` on `1.21.1+` and `26.x`.
4. **Verification & Testing**:
   - Unit test boundary conditions: count = 1, 15, 63, 64, 2000; tools (max stack 1); pearls (max stack 16).
   - Headless GameTest asserting ImpulseSV filter slice stability.

---

## 🏁 7. 5-Pillar Verification Checklist

- [ ] **Pillar 1 (Chaos Fuzzing)**: Saturated arithmetic fuzzing passes 50k random stack counts and slot capacities with 0 negative counts or arithmetic overflows.
- [ ] **Pillar 2 (Concurrency Load)**: Multi-threaded hopper siphoning harness passes 50 virtual threads with zero race conditions on entity shrinking.
- [ ] **Pillar 3 (Brigadier AST)**: Dynamic GameRule discovery and `/gamerule` tab-completion verified headlessly.
- [ ] **Pillar 4 (GameTest)**: Headless Minecraft GameTest asserts that an entity with count 63 siphons 1 item, and count 64 siphons 16 items.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified on all edited files.
- [ ] **Anchor Compilation**: Clean `./gradlew test` across all 7 Master Anchors (`1.20.1` through `26.x`).
