# Configuration & GUI Integration (MC 26.2)

This document details the configuration file schema, YACL v3 GUI integration, ModMenu entrypoints, and world precedence rules for **Item Clumps** on **Minecraft 26.2**.

---

## 📊 Configuration Infobox

| Property | Value |
| :--- | :--- |
| **Config File Path** | `config/item-clumps.json` |
| **File Format** | JSON (Managed via DasikLibrary `ConfigHelper`) |
| **Current Schema Version** | `configVersion: 1` |
| **Graphical Interface Provider** | YetAnotherConfigLib v3 (YACL) via ModMenu |
| **Classloading Isolation** | Deferred reflection (`GuiHelper.getOptionalYaclFactory`) |
| **Precedence Mandate** | Config sets **new world defaults**; active worlds use **GameRules** |

---

## ⚠️ The Global Config vs. Active World Precedence Rule

> ⚠️ **IMPORTANT NOTICE**: Modifying `config/item-clumps.json` or changing settings in the ModMenu GUI defines the baseline default values for **newly created worlds**. To configure settings for an active or existing world, use the in-game `/gamerule` command or the native Game Rules edit screen.

---

## 📄 JSON File Schema (`config/item-clumps.json`)

```json
{
  "configVersion": 1,
  "enableClumping": true,
  "maxClumpSize": 9999,
  "renderLabels": true,
  "mergeRadius": 1,
  "labelMinCount": -1
}
```

### Field Definitions

| JSON Key | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `configVersion` | Integer | `1` | Internal schema migration tracker. |
| `enableClumping` | Boolean | `true` | Enables or disables mega-stack item merging. |
| `maxClumpSize` | Integer | `9999` | Hard cap on items per clump entity ($64\text{ to }2,147,483,647$). |
| `renderLabels` | Boolean | `true` | Renders floating custom name tag counts above clumps. |
| `mergeRadius` | Integer | `1` | Horizontal block search radius ($1\text{ to }10\text{ blocks}$). |
| `labelMinCount` | Integer | `-1` | Threshold before count label displays (`-1` = vanilla max stack). |

---

## 🖥️ Graphical Interface (YACL v3 & ModMenu)

Item Clumps integrates optional client-side configuration screens via **YetAnotherConfigLib (YACL v3)** and **ModMenu**:

```
+-------------------------------------------------------------------------+
|                       ITEM CLUMPS CONFIGURATION                         |
|                                                                         |
|  [ General Settings ]                                                   |
|                                                                         |
|  Enable Clumping ............................................ [ ON ]    |
|  When true, ground items aggressively merge into mega-stacks.          |
|                                                                         |
|  Max Clump Size ............................................. [ 9999 ]  |
|  The hard cap on items per entity. (Hidden if Stack Size Adjuster on)   |
|                                                                         |
|  Render Labels .............................................. [ ON ]    |
|  Renders holographic count above item clumps.                           |
|                                                                         |
|  Merge Radius ................................... [───●──────────] (1)  |
|  Horizontal block search radius (1-10 blocks).                          |
|                                                                         |
|  Label Min Count ............................................ [ -1 ]    |
|  Minimum item count before label displays (-1 for vanilla max stack).   |
|                                                                         |
|  [ Cancel ]                                            [ Save & Quit ]  |
+-------------------------------------------------------------------------+
```

### Safe Deferred Classloading
To guarantee that the mod never crashes dedicated servers or clients that do not have YACL or ModMenu installed, screen instantiation is isolated behind `GuiHelper.getOptionalYaclFactory`:

```java
// ModMenuIntegration.java (MC 26.2)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalYaclFactory(
            "item_clumps",
            "net.instantgratification.item_clumps.config.YaclScreenHelper",
            "createScreen"
        );
    }
}
```

---

## 🔗 Related Documentation
* [[GameRules Reference|GameRules]]
* [[Mod Compatibility|Compatibility-and-Integrations]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
