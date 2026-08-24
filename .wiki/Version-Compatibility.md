# Version Compatibility Matrix

This document provides a comprehensive overview of Minecraft version targets, runtime environments, toolchains, and compatibility bounds for **Item Clumps**.

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 🧭 Multi-Era Lifecycle Matrix

| Minecraft Anchor | Release Era | SemVer Tag | Java Version | Fabric Loader | Fabric API | DasikLibrary | Config GUI Provider |
| :--- | :--- | :---: | :---: | :---: | :---: | :---: | :--- |
| **Minecraft 26.2** | Modern Sovereign Era | `1.0.23+26.2` | Java 25+ | `>=0.19.1` | `*` (Universal) | `>=1.8.2` | YetAnotherConfigLib v3 (YACL) + ModMenu |
| **Minecraft 26.1.2** | Modern Sovereign Era | `1.0.4+26.1.2` | Java 25+ | `>=0.19.1` | `>=0.145.4+26.1.2` | `>=1.6.9+build.22` | Cloth Config v2 + ModMenu |

---

## ⚙️ Build Toolchain & Environment Specifications

### Minecraft 26.2 (`Item Clumps 26.2`)
* **Gradle Project Path**: `Item Clumps v26.2/Item Clumps 26.2`
* **Loom Version**: `1.15.2` (`id 'net.fabricmc.fabric-loom'`)
* **Gradle Wrapper**: `8.12` / `9.x`
* **Java Target Level**: Java 25 (`release = 25`, `org.gradle.java.home=E:/JDK25`)
* **Dependency Bounds in `fabric.mod.json`**:
  ```json
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": ">=26.2-",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": ">=1.8.2"
  },
  "suggests": {
    "yet-another-config-lib": "*",
    "modmenu": "*"
  }
  ```
* **Runtime Version Guard**: `ModVersionGuard.checkClass("Item Clumps", "net.minecraft.world.entity.item.ItemEntity")`

---

## 🔗 Related Documentation
* [[🏠 Return to Home Portal|Home]]
* [[GameRules Reference|GameRules]]
* [[Developer Setup & Building|Developer-Setup-and-Building]]
