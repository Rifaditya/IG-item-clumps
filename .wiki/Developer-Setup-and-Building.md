# Developer Setup & Building (MC 26.2)

This document details the JDK 25 toolchain setup, Loom Gradle configuration, build commands, and development environment workflows for **Item Clumps** on **Minecraft 26.2**.

---

## 📊 Toolchain & Requirements Infobox

| Tool | Required Version | Configuration Notes |
| :--- | :--- | :--- |
| **Java Development Kit (JDK)** | Java 25+ | Configured via `org.gradle.java.home=E:/JDK25` |
| **Fabric Loom** | `1.15.2` | Native non-obfuscated runtime (no `mappings` block) |
| **Gradle Wrapper** | `8.12` / `9.x` | Always build with `--no-daemon` on Windows |
| **Fabric Loader** | `>=0.19.1` | Modern Sovereign Era loader |
| **Fabric API** | `0.149.2+local` | Universal API dependency |
| **DasikLibrary** | `1.8.3` | Required core library |

---

## ⚙️ Gradle Project Configuration (`build.gradle`)

```groovy
plugins {
    id 'net.fabricmc.fabric-loom' version '1.15.2'
    id 'maven-publish'
}

version = project.mod_version
group = project.maven_group
base.archivesName = project.archives_base_name

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    minecraft "com.mojang:minecraft:${project.minecraft_version}"
    modImplementation "net.fabricmc:fabric-loader:${project.fabric_loader_version}"
    modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
    modImplementation "net.dasik:dasik-library:${project.dasik_library_version}"
}

tasks.withType(JavaCompile).configureEach {
    options.release = 25
    options.encoding = "UTF-8"
}
```

---

## 🔨 Building & Compiling from Source

### 1. Cloning the Repository
```bash
git clone https://github.com/Rifaditya/IG-item-clumps.git
cd "IG-item-clumps/Item Clumps v26.2/Item Clumps 26.2"
```

### 2. Building the Release JAR
```powershell
./gradlew build --no-daemon
```
The compiled artifact will be generated at:
`build/libs/item-clumps-1.0.23+26.2.jar`

### 3. Publishing to Maven Local
```powershell
./gradlew publishToMavenLocal --no-daemon
```

---

## 🔗 Related Documentation
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[ModVersionGuard|ModVersionGuard-and-Safety]]
* [[API & Addon Integration|API-and-Addon-Integration]]
