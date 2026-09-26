# Compatibility & Cross-Mod Integrations (MC 26.2)

This document details third-party mod integrations, dynamic reflection safeguards, Stack Size Adjuster alignment, and farm compatibility in **Item Clumps** for **Minecraft 26.2**.

---

## 📊 Feature Infobox

| Integration | Type | Compatibility Status | Handling Mechanism |
| :--- | :---: | :---: | :--- |
| **Magnet ("Let me get that!")** | Optional | 🟢 100% Seamless | Dynamic reflection check (`ig_magnet$isMagnetized` / `ig$isMagnetized`) |
| **Stack Size Adjuster** | Optional | 🟢 Dynamic Limit Sync | Yields `max_clump_size` registration to `getMaxStackSize()` |
| **YetAnotherConfigLib (YACL v3)** | Optional GUI | 🟢 Supported | Deferred classloading via `GuiHelper.getOptionalYaclFactory` |
| **ModMenu** | Optional GUI | 🟢 Supported | ModMenu entrypoint integration |
| **Vanilla Automation / Redstone** | Core | 🟢 100% Parity | 1-item hopper drip extraction & youngest age inheritance |

---

## 🧲 1. Magnet Mod In-Flight Protection

When using item vacuum or magnet mods (such as **Magnet: Let me get that!**), items on the ground are lifted into 3D flight trajectories heading toward the player.

```
       ┌───────────────────────────────────────────────────────────┐
       │             Item In-Flight Magnet Trajectory              │
       │                                                           │
       │       [Item A (In-Flight)] --------> [Player]             │
       │               │                                           │
       │       (Passes close to Item B on ground)                  │
       │               │                                           │
       │   ❌ WITHOUT MAGNET CHECK:                                │
       │      Item A merges with B; flight velocity resets;        │
       │      Item snaps to ground.                                │
       │                                                           │
       │   ✅ WITH ITEM CLUMPS MAGNET CHECK:                       │
       │      Merge paused while in-flight;                         │
       │      Smooth curve preserved straight to player!           │
       └───────────────────────────────────────────────────────────┘
```

Item Clumps queries the magnet state via reflection without hardcoded compile-time dependencies:

```java
if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("magnet")) {
    try {
        java.lang.reflect.Method isMagnetizedMethod;
        try {
            isMagnetizedMethod = this.getClass().getMethod("ig_magnet$isMagnetized");
        } catch (NoSuchMethodException e) {
            isMagnetizedMethod = this.getClass().getMethod("ig$isMagnetized");
        }
        if ((boolean) isMagnetizedMethod.invoke(this) || (boolean) isMagnetizedMethod.invoke(other)) {
            ci.cancel();
            return;
        }
    } catch (Throwable ignored) {}
}
```

---

## 📦 2. Stack Size Adjuster Dynamic Alignment

When **Stack Size Adjuster** is installed alongside Item Clumps:
1. **GameRule De-duplication**: Item Clumps automatically suppresses the registration of `item_clumps:max_clump_size` and its YACL config widget.
2. **Unified Limit Sync**: Ground merging limits automatically query `itemStack.getMaxStackSize()`, allowing Stack Size Adjuster to dictate ground stack caps globally or per-item.

```java
int maxClump;
if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("stack-size-adjuster") || 
    ItemClumpsFabric.MAX_CLUMP_SIZE == null) {
    maxClump = thisStack.getMaxStackSize();
} else {
    maxClump = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);
}
```

---

## 🔗 Related Documentation
* [[Mega-Stack Clumping|Clumping-Mechanics-and-Mega-Stacks]]
* [[Configuration Suite|Configuration]]
* [[Architecture & Mixins|Architecture-and-Mixins]]
