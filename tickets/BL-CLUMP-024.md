# 🎫 [BL-CLUMP-024] Scaffold Anchor 7: Item Clumps v26.x (rolling 26.3+)

- **Ticket ID**: `[BL-CLUMP-024]`
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Scope**: `Anchor 7 (MC 26.x Sovereign Rolling Lead)`
- **Status**: `📌 DEFERRED`
- **Target Repository / Root**: `Instant Gratification Collection/item-clumps`
- **Governance Mandate**: Studio Constitution §1.4 (*1 Jar 1 Version Policy*), §1.5 (*Multi-Era Anchor Parity Law*), and `Minecraft Version Matrix.md`
- **Prerequisite**: [BL-WS-035](file:///e:/Minecraft%20Project/tickets/BL-WS-035.md) Phase 1 governance (pinned/rolling rule) + Keep Gear pilot green

---

## 🎯 1. Goal & Definition of Done (The 4-Pillar Objective Architecture)

1. **North Star Objective**: Establish Anchor 7 by scaffolding `Item Clumps v26.x/` subproject cloned from the modern 26.3 codebase, configured with Java 25, Gradle 9.3+, Loom 1.15+, and open-ended `"minecraft": ">=26.3-"` dependency bounds, accompanied by dedicated `Archive Jar of all versions/MC 26.x/` distribution.
2. **In-Game Experience (Before vs. After)**:
   - *Before*: Mod targets only fixed sovereign anchor releases (up to 26.3); players running newer modern snapshot or rolling versions (26.4+) face incompatible version rejections or require individual point-release ports.
   - *After*: Mod provides a dedicated forward-compatible rolling build (`...+26.x.jar`) that loads seamlessly across all stable Minecraft 26.3+ sovereign runtimes.
3. **🚫 Anti-Goals (Off-Limits / Do Not Touch)**:
   - ❌ Do not execute this scaffolding in a monolithic fleet batch; execute opportunistically per mod under individual release cadences.
   - ❌ Do not alter or break existing pinned version anchors (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`).
   - ❌ Do not publish untagged or multi-version composite JARs violating the 1 Jar 1 Version policy.
   - ❌ Do not bypass the Studio 5-Link Release Chain or platform tag collision rules.
4. **✅ Definition of Done (Binary Verification Checklist)**:
   - [ ] Subproject directory `Item Clumps v26.x/` fully scaffolded with `build.gradle`, `gradle.properties`, and `settings.gradle` inclusion.
   - [ ] `fabric.mod.json` declares open-ended dependency `"minecraft": ">=26.3-"`.
   - [ ] Clean compilation and headless verification pass: `./gradlew test build --no-daemon`.
   - [ ] Pillar 5 bytecode sidedness audit confirms 0 illegal client imports in common code and valid GPLv3 headers.
   - [ ] Output artifact stored in `Archive Jar of all versions/MC 26.x/`.
   - [ ] Mod release tracked in `RELEASE_QUEUE.md` and `MASTER_ANCHOR_BOARD.md` registers `26.x` as green (`✅`).

---

## ❓ 2. Problem Statement & Player Impact

Minecraft modern sovereign releases are expanding beyond 26.3 into snapshot 26.4 and future rolling iterations. Under Studio Constitution §1.5 (*Multi-Era Anchor Parity Law*) and `BL-WS-035`, the studio has upgraded from a 6-anchor surface to the **7 Master Version Anchors** (`1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3`, `26.x`).

For **Item Clumps**, establishing Anchor 7 (`26.x`) provides forward compatibility for players on the rolling modern channel while maintaining strict separation from pinned sovereign anchors.

This mod possesses an active v26.3 codebase. Scaffolding v26.x clones the verified 26.3 baseline, adjusts version metadata, and establishes the rolling build pipeline.

---

## 🔬 3. Architectural Blueprint & Target Directories

- **Target Subproject Directory**: `Item Clumps v26.x/`
- **Archive Directory**: `Archive Jar of all versions/MC 26.x/`
- **Output Artifact**: `item-clumps-1.3.1+26.x.jar`
- **Baseline Clone Source**: `Item Clumps v26.3/`
- **Configuration Targets**:
  - `gradle.properties`: `minecraft_version = 26.3`, `mod_version = <calibrated>+26.x`
  - `fabric.mod.json`:
    ```json
    "depends": {
      "fabricloader": ">=0.19.5",
      "minecraft": ">=26.3-",
      "java": ">=25"
    }
    ```
  - `settings.gradle`: Include subproject in root orchestration.
- **Pinned Companion Rule**:
  - Existing `v26.3` anchor pinned to `minecraft: ">=26.3- <26.4-"` with its own SemVer bump to prevent platform tag collision.

---

## ⚓ 4. Multi-Era Anchor Parity Specifications

This ticket establishes Anchor 7 in the studio's 7 Master Version Anchors:
1. **Anchor 1 (`1.20.1`)**: Legacy Long-Term Support (Java 17, primitive NBT, legacy Fabric Loader).
2. **Anchor 2 (`1.21.1`)**: Transitional Early (Java 21, DataComponents).
3. **Anchor 3 (`1.21.11`)**: Transitional Late / Winter Drop (Java 21, `fabric-loom-remap`).
4. **Anchor 4 (`26.1.2`)**: Modern Initial Sovereign (Java 25, DataComponents, dynamic GameRules).
5. **Anchor 5 (`26.2`)**: Modern Intermediate Sovereign (Java 25, Loom 1.15+).
6. **Anchor 6 (`26.3`)**: Modern Standard Sovereign (Java 25, pinned `>=26.3- <26.4-`).
7. **Anchor 7 (`26.x`)**: **Modern Sovereign Rolling Anchor** (Java 25, rolling `>=26.3-`).

---

## 🛡️ 5. Edge Cases, Collisions & Failure Modes

- **Platform Tag Collision**: Publishing both a `+26.3` JAR and a `+26.x` JAR claiming the same Minecraft version creates release collisions. Resolved via BL-WS-035 Phase 1 governance (pinned vs. rolling split).
- **Double Maintenance Invariant**: Under the Multi-Era Anchor Parity Law, any feature addition, bug fix, or GameRule adjustment applied to v26.3 must be ported in lockstep to v26.x.
- **Knot ClassLoader Safety**: Ensure headless test suite and runtime do not invoke unremapped or client-only classes in server environments.
- **Arithmetic Saturation & Bounds**: Saturated arithmetic and uncapped player config boundaries must remain fully intact in rolling builds per Freedom Over Anti-Crash policy.

---

## 🚀 6. Implementation Steps (Code-Level Plan)

1. **Verify Prerequisites**: Confirm BL-WS-035 Phase 1 governance is active and Keep Gear pilot is verified.
2. **Directory Scaffolding**: Clone `Item Clumps v26.3/` to `Item Clumps v26.x/`.
3. **Build Script & Metadata Calibration**:
   - Update `gradle.properties` (`mod_version` calibrated with SemVer Provenance table, `+26.x` suffix).
   - Update `fabric.mod.json` with `"minecraft": ">=26.3-"`.
   - Update root `settings.gradle` to include `Item Clumps v26.x`.
4. **Test & Verification**:
   - Execute `./gradlew test --no-daemon` to ensure test suite passes.
   - Execute `./gradlew build --no-daemon` to produce binary JAR.
   - Run Pillar 5 Sidedness Contract scan to confirm zero client leakage.
5. **Distribution & Release Registration**:
   - Place output JAR in `Archive Jar of all versions/MC 26.x/`.
   - Register release entry in `RELEASE_QUEUE.md`.
   - Document changes in `CHANGELOG.md` and `History.md`.

---

## 🏁 7. 5-Pillar Verification Checklist

- [ ] **Pillar 1 (Chaos Fuzzing)**: Chaos property fuzzing passes 10k-50k iterations for configuration bounds.
- [ ] **Pillar 2 (Concurrency Load)**: Concurrency stress tests pass 50 virtual worker threads with zero race conditions.
- [ ] **Pillar 3 (Brigadier AST / GUI AST)**: Brigadier command tree AST or client GUI/translation tokens validate headlessly.
- [ ] **Pillar 4 (GameTest / State Simulation)**: Headless in-engine simulation verifies logic without graphical display.
- [ ] **Pillar 5 (Sidedness & License Hygiene)**: Sidedness contract asserts 0 client imports in common code; single-line GPLv3 headers verified.
- [ ] **Clean Build**: `./gradlew test build --no-daemon` succeeds with exit code 0.
