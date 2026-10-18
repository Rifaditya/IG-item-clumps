# 🎫 [BL-CLUMP-017] Jade / WTHIT / The One Probe Clump Provider

- **Ticket ID**: `[BL-CLUMP-017]`
- **Category**: `[FEATURE]`
- **Priority**: `[MEDIUM]`
- **Scope**: `All Anchors` (`1.20.1`, `1.21.1`, `1.21.11`, `26.1`, `26.2`, `26.3`)
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`

---

## 🎯 1. Problem Statement & Player Impact
Players using info-overlay mods (Jade, WTHIT) see generic single-item tooltips rather than native clump counts and despawn progress.

Provides seamless compatibility plugins for Jade and WTHIT, displaying item clump counts, despawn bars, and stack composition directly inside the overlay HUD.

## 🔬 2. Architectural Blueprint & Target Classes
- Package: `net.instantgratification.itemclumps.compat.jade` & `.wthit`
- Classes to Modify/Create:
  - `JadeClumpPlugin.java`: Implements `IWailaPlugin` and `IEntityComponentProvider` to surface clump count and despawn progress bar.
  - `WthitClumpPlugin.java`: Implements WTHIT entity provider.
  - `fabric.mod.json`: Declares optional entrypoints for `jade` and `wthit`.

## ⚓ 3. Multi-Era Anchor Parity Specifications
- **MC 1.20.1 (Java 17)**: Uses legacy Blaze3D matrix stack, primitive NBT item entity counts.
- **MC 1.21.1 / 1.21.11 (Java 21)**: DataComponents for item stack metadata, modern entity render layers.
- **MC 26.1 / 26.2 / 26.3+ Sovereign Lead (Java 25)**: Modern Renderpearl entity render state, saturation arithmetic bounds.

## 🛡️ 4. Edge Cases, Collisions & Failure Modes
- Optional runtime loading: uses reflection / deferred classloading to prevent crashes when Jade or WTHIT are not installed.
- Dedicated Server Safety: client rendering logic strictly isolated to client package; 0 client imports in common code.
- Freedom Over Anti-Crash: corrupted configs fall back safely to defaults without startup abort.

## 🚀 5. Implementation Steps (Code-Level Plan)
1. Author `JadeClumpPlugin.java` and `WthitClumpPlugin.java`.
2. Register entrypoints in `fabric.mod.json`.
3. Unit test classloading isolation without Jade/WTHIT on classpath.
5. Verify clean build and test execution across all active anchors.

## 🏁 6. 5-Pillar Verification Checklist
- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency harness passes 50 virtual threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST)**: Brigadier command tree AST and permissions validated headlessly.
- [ ] **Pillar 4 (GameTest)**: Native Mojang GameTests pass in headless server environment.
- [ ] **Pillar 5 (Sidedness & Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Anchor Compilation**: Clean `./gradlew test` and `./gradlew build` across all targeted anchors.
