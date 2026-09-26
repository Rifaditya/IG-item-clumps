# Architecture & Symbol Index: Item Clumps

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `item_clumps`
- **Main Entrypoint**: `net.instantgratification.item_clumps.ItemClumpsFabric` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `None`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.instantgratification.item_clumps.mixin.HopperBlockEntityMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.item_clumps.mixin.ItemEntityMixin` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`item_clumps:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
