# Concept: Item Clumps

> **Philosophy**: Instant Gratification (IG) - "Performance First. Friction Zero."
> **Category**: Optimization / Quality of Life

## Core Identity
**Item Clumps** is an aggressive performance optimization mod designed to solve entity lag caused by massive item drops (mob farms, large explosions, automated quarries). By subverting the vanilla 64-item stack limit for ground entities, it condenses thousands of identical items into a single, highly-optimized `ItemEntity`.

## The Problem
Vanilla Minecraft merges items, but strictly caps them at maximum stack size (usually 64). If farm produces 6,400 cobblestone, game ticks and renders 100 separate `ItemEntity` objects. This causes exponential TPS decay and client-side FPS drops due to rendering and collision calculations.

## The Solution (Mechanics)
1. **Aggressive Clumping (Mega-Stack)**: Hardcoded 64-item merge limit removed for ground entities. `ItemEntity` objects merge into "Clumps" holding thousands of items.
2. **Strict Component Verification**: Items ONLY merge if Data Components (durability, enchantments, custom data) are exactly identical.
3. **Strict Vanilla Inventory Limits (Smart Pick-up)**: The player's inventory stack limits (e.g., 64 for stone) are **NEVER** modified. When a player collides with a Mega-Stack, they only extract items up to their normal vanilla inventory capacity. The remaining amount stays on the ground in the Mega-Stack entity. Entity destroyed only when count reaches 0.
4. **Holographic Count Display**: Dynamic, floating text label renders above clump indicating true count (e.g., "Cobblestone x1500"). 
5. **Radius Expansion**: Search radius for identical items to merge slightly increased to encourage clumping in water streams or hoppers.

## Technical Architecture (Minecraft 26.x)
- **Entity Data (MegaCount Strategy)**: Modifying `ItemStack` count > 99 will crash vanilla's `ItemStack.CODEC` strict validation during chunk saves. 
  - **Solution**: Keep internal `ItemStack` count clamped to 1. Store the true count in a custom `TrackedData<Integer>` and save it as a custom `mega_count` NBT tag directly on the `ItemEntity`.
- **Mixin Targets**:
  - `ItemEntity.tick()` and `ItemEntity.tryMerge()`: Override stack size limits, expand bounding box search, and sum the custom `mega_count`.
  - `ItemEntity.playerTouch()`: Handle partial pickups. Feed items into player inventory in safe, vanilla-sized chunks (e.g., 64) via loop to prevent `Inventory.add()` crashes.
  - `ItemEntity.readAdditionalSaveData` / `addAdditionalSaveData`: Serialize `mega_count`.
  - `EntityRenderer<ItemEntity>`: Inject text rendering logic.
- **Networking**: True count synced to client via `TrackedData` for text renderer.

## Configuration & Integration (DasikLibrary)
Mod uses `DasikLibrary`'s `DynamicGameRuleManager` under custom `GameRuleCategory` (`item_clumps`).
- `item_clumps:enable_clumping` (Boolean, Default: `true`): Master toggle.
- `item_clumps:max_clump_size` (Integer, Default: `9999`): Hard cap on items per clump. Prevents integer overflow.
- `item_clumps:render_labels` (Boolean, Default: `true`): Toggles floating text for performance purists.
- `item_clumps:merge_radius` (Integer, Default: `1`): Block radius items search to merge.

## Edge Cases & Safety
- **Hopper/Dropper Interactions**: Hoppers must extract 1 (or 8) items at a time from Mega-Stack without deleting whole entity. Mixins into `HopperBlockEntity` collision logic required.
- **Despawn Timers**: When a new item merges into an existing Mega-Stack, the despawn timer (`age`) resets to the minimum age of the merging entities (which is typically `0` for newly dropped items). This prevents the sudden deletion of valuable items as long as new items are constantly merging. However, if the stack sits idle without new items merging, the entire clump will despawn at once when the surviving entity reaches 5 minutes (6000 ticks) of age.
- **Physics**: Larger clumps receive slight visual scale bump. Collision boxes remain vanilla. Prevents getting stuck in blocks.
