# 🎫 [BL-CLUMP-011] Configurable Blacklist & Datapack Tag (#c:unclumpable)

- **Ticket ID**: `[BL-CLUMP-011]`
- **Category**: `[FEATURE]`
- **Priority**: `[HIGH]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Certain items (e.g. Nether Stars, quest books, custom puzzle keys, throwable weapons) must remain distinct individual entities and should never merge.

Provides complete control to pack developers and server owners to blacklist specific item IDs or tag items via `#c:unclumpable` to bypass clumping.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.filter`
- Classes to Modify/Create:
  - `ItemClumpFilter.java`: Checks both datapack tag `#c:unclumpable` (and `#itemclumps:unclumpable`) and configuration blacklist string array.
  - `ItemEntityMixin.java`: Early return from `tryToMerge` if either entity contains a blacklisted item.
  - Datapack Tag: `data/c/tags/item/unclumpable.json`.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Blacklisted items behave 100% identical to vanilla separate entities with individual physics and separate despawn timers.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `ItemClumpFilter.java` with tag and config checks.
2. Inject into `ItemEntityMixin.java`.
3. Scaffold default `unclumpable.json` tag file.
4. Unit test filter matching.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
