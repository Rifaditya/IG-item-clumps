# Commands & Administration (MC 26.1.2)

This document details server administration, permission nodes, command syntax, and Absence Policy verification for **Item Clumps** on **Minecraft 26.1.2**.

---

## 📊 Administration Infobox

| Property | Value |
| :--- | :--- |
| **Command Engine** | Native Minecraft Vanilla GameRules (`/gamerule`) |
| **Permission Level Required** | Level 2 (OP / Server Administrator / Singleplayer Cheats Enabled) |
| **Custom Brigadier Subtrees** | **None** (Absence Policy verified: relies on vanilla `/gamerule`) |
| **Execution Sidedness** | Server-Side / Integrated Server Console & Chat |

---

## 📜 Absence Policy & Architecture Notice

> 📌 **Absence Policy Verification**: Item Clumps intentionally omits custom Brigadier command subtrees (e.g. no custom `/itemclumps` root command). All administrative features are 100% integrated into Minecraft's native `GameRules` engine.

---

## 🛠️ Command Syntax & Querying

### 1. Querying Active Values
```text
/gamerule item_clumps:enable_clumping
/gamerule item_clumps:max_clump_size
/gamerule item_clumps:merge_radius
/gamerule item_clumps:render_labels
```

### 2. Modifying Values
```text
/gamerule item_clumps:enable_clumping <true|false>
/gamerule item_clumps:max_clump_size <integer>
/gamerule item_clumps:merge_radius <integer (1-10)>
/gamerule item_clumps:render_labels <true|false>
```

---

## 🔗 Related Documentation
* [[GameRules Table|GameRules]]
* [[Configuration Suite|Configuration]]
* [[FAQ & Diagnostics|Troubleshooting-and-FAQ]]
