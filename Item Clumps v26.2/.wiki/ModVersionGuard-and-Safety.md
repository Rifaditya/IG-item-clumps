# ModVersionGuard & Runtime Safety (MC 26.2)

This document details the zero-dependency runtime classloader checks, version guard architecture, and bytecode protection mechanisms in **Item Clumps** for **Minecraft 26.2**.

---

## 📊 Feature Infobox

| Property | Value |
| :--- | :--- |
| **System Name** | Knot ClassLoader Version Guard |
| **Target Class** | `net.instantgratification.item_clumps.util.ModVersionGuard` |
| **Verified Class** | `net.minecraft.world.entity.item.ItemEntity` |
| **Library Verification** | `net.dasik.social.api.config.ConfigHelper` (Requires DasikLibrary $\ge 1.7.4$) |
| **Safety Objective** | Prevent world corruption if loaded on an incompatible Minecraft API |

---

## 🛡️ The Knot ClassLoader Architecture

In Fabric Loader, mod classes are loaded across distinct classloaders (Knot ClassLoader vs. system application classloader). A standard `Class.forName(name)` call can falsely fail or resolve outdated classes if it does not query the active thread context loader.

Item Clumps implements a dual-stage classloader resolution:

```
                     ┌───────────────────────────────────┐
                     │ ModVersionGuard.checkClass Invoked│
                     └─────────────────┬─────────────────┘
                                       │
                                       ▼
                     ┌───────────────────────────────────┐
                     │ Query Knot Thread Context Loader  │
                     │ Thread.currentThread()            │
                     │   .getContextClassLoader()        │
                     └─────────────────┬─────────────────┘
                                       │
                      ┌────────────────┴────────────────┐
                      │ (Success)                       │ (ClassNotFoundException)
                      ▼                                 ▼
        ┌───────────────────────────┐     ┌───────────────────────────┐
        │ Game Continues Execution  │     │ Fallback to Current Loader│
        │ Safe Runtime Verified     │     │ ModVersionGuard.class     │
        └───────────────────────────┘     │   .getClassLoader()       │
                                          └─────────────┬─────────────┘
                                                        │
                                       ┌────────────────┴────────────────┐
                                       │ (Success)                       │ (ClassNotFoundException)
                                       ▼                                 ▼
                         ┌───────────────────────────┐     ┌───────────────────────────┐
                         │ Game Continues Execution  │     │ Throw Descriptive Warning │
                         │ Safe Runtime Verified     │     │ Halt Corrupted World Load │
                         └───────────────────────────┘     └───────────────────────────┘
```

---

## 💻 Ground-Truth Java Source Implementation

From `ModVersionGuard.java` in Minecraft 26.2:

```java
// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.util;

public final class ModVersionGuard {
    private ModVersionGuard() {}

    public static void checkClass(String modName, String requiredClassName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader currentLoader = ModVersionGuard.class.getClassLoader();
        try {
            if (contextLoader != null) {
                Class.forName(requiredClassName, false, contextLoader);
            } else {
                Class.forName(requiredClassName, false, currentLoader);
            }
        } catch (ClassNotFoundException e) {
            try {
                Class.forName(requiredClassName, false, currentLoader);
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("\n" +
                    "=====================================================================\n" +
                    " [PRE-RELEASE / VERSION GUARD WARNING] " + modName + "\n" +
                    "---------------------------------------------------------------------\n" +
                    " CRITICAL: Incompatible Minecraft Game Runtime or Missing Class!\n" +
                    " Required Class : " + requiredClassName + "\n" +
                    " Status         : UNRESOLVED AT RUNTIME\n\n" +
                    " Safety Protection:\n" +
                    " Execution halted to prevent unreleased/incompatible build deployment\n" +
                    " or broken world state save corruption.\n\n" +
                    " Troubleshooting Steps:\n" +
                    " 1. Verify target Minecraft version (26.2+ release drop).\n" +
                    " 2. Ensure all required dependencies (Fabric API, DasikLibrary) are loaded.\n" +
                    " 3. Build/Download a verified matching release JAR from Modrinth/CurseForge.\n" +
                    "=====================================================================");
            }
        }
    }
}
```

---

## 🔗 Related Documentation
* [[Developer Setup|Developer-Setup-and-Building]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
* [[FAQ & Diagnostics|Troubleshooting-and-FAQ]]
