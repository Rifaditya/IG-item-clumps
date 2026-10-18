# 🎫 [BL-CLUMP-018] Multi-Era Anchor Porting: Item Clumps

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
