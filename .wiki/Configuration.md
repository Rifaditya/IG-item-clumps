# Configuration & GUI Integration (MC 26.1.2)

This document details the configuration file schema, Cloth Config v2 GUI integration, ModMenu entrypoints, and world precedence rules for **Item Clumps** on **Minecraft 26.1.2**.

---

## 📊 Configuration Infobox

| Property | Value |
| :--- | :--- |
| **Config File Path** | `config/item-clumps.json` |
| **File Format** | JSON (Gson serialization with atomic swap safety) |
| **Current Schema Version** | `configVersion: 1` |
| **Graphical Interface Provider** | Cloth Config v2 via ModMenu |
| **Precedence Mandate** | Config sets **new world defaults**; active worlds use **GameRules** |

---

## 📄 JSON File Schema (`config/item-clumps.json`)

```json
{
  "configVersion": 1,
  "enableClumping": true,
  "maxClumpSize": 9999,
  "renderLabels": true,
  "mergeRadius": 1
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

---

## 🖥️ Graphical Interface (Cloth Config & ModMenu)

Item Clumps integrates optional client-side configuration screens via **Cloth Config v2** and **ModMenu**:

```java
// ModMenuIntegration.java (MC 26.1.2)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return ClothConfigScreenHelper.createFactory();
        }
        return parent -> null;
    }
}
```

---

## 🔗 Related Documentation
* [[GameRules Reference|GameRules]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
