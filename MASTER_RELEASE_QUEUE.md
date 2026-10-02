# 🎛️ Master Release Queue: Instant Gratification — Item Clumps

> **Mod Project Master Ground-Truth Document**  
> **Modrinth ID**: `lJiPHgum` | **CurseForge ID**: `1548645` | **Lead SemVer**: `1.0.29`

---

## 📊 Multi-Version Release Matrix & Queue Status

| Target MC | Generational Era | Live on Platforms | Next Queued Version | Status & Cadence Action | Feature Highlights / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | Modern Lead | *(Unreleased)* | `1.0.29+26.3` | 🟢 **Ready to Publish** | Official MC 26.3 stable port with Fabric Loader 0.19.5 and DasikLibrary 1.9.2. |
| **MC 26.2** | Modern Predecessor | `1.0.26+26.2` | `1.0.27+26.2` | 🟢 **Ready to Publish** | Client side-safety annotations (`1.0.28+26.2` queued next). |
| **MC 26.1.2** | Modern Sovereign | `1.0.7+26.1.2` | `1.0.28+26.1.2` | 🟢 **Ready to Publish** | Modern sovereign anchor parity port with DasikLibrary 1.8.39 alignment. |
| **MC 1.21.11** | Older Anchor | *(Unreleased)* | `1.0.0+1.21.11` | 🟢 **Ready to Publish** | Older anchor port for Minecraft 1.21.11 with DasikLibrary 1.1.0 alignment. |
| **MC 1.21.1** | Older Anchor | *(Unreleased)* | `1.0.0+1.21.1` | 🟢 **Ready to Publish** | Older anchor port for Minecraft 1.21.1 with DasikLibrary 1.1.0 alignment. |

---

## 🏛️ Project Operating Rules & Architectural Invariants

1. **📜 Subproject Changelog & Queue Segregation Law**:
   - Each version anchor subproject directory maintains its own dedicated `CHANGELOG.md` and `RELEASE_QUEUE.md` tracking solely that Minecraft version anchor with Strict Version Anchor Exclusivity.
