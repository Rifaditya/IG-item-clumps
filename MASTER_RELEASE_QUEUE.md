# 🎛️ Master Release Queue: Instant Gratification — Item Clumps

> **Mod Project Master Ground-Truth Document**  
> **Modrinth ID**: `lJiPHgum` | **CurseForge ID**: `1548645` | **Lead SemVer**: `1.3.0`

---

## 📊 Multi-Version Release Matrix & Queue Status

| Target MC | Generational Era | Live on Platforms | Next Queued Version | Status & Cadence Action | Feature Highlights / Notes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **MC 26.3** | Modern Lead | `1.0.29+26.3` | `1.0.30+26.3` ➔ `1.1.0+26.3` ➔ `1.2.0+26.3` ➔ `1.3.0+26.3` | 🟢 **Ready to Publish** | Knot safety (`1.0.30`), dynamic scaling (`1.1.0`), explosion pre-clump (`1.2.0`), label wall culling (`1.3.0`). |
| **MC 26.2** | Modern Predecessor | `1.0.26+26.2` | `1.0.27+26.2` ➔ `1.0.28+26.2` ➔ `1.0.29+26.2` ➔ `1.1.0+26.2` ➔ `1.2.0+26.2` ➔ `1.3.0+26.2` | ⏸️ **On Parity Hold** | Side safety (`1.0.27`), DasikLib 1.8.39 (`1.0.28`), Knot safety (`1.0.29`), visual scaling (`1.1.0`), explosion pre-clump (`1.2.0`), label wall culling (`1.3.0`). |
| **MC 26.1.2** | Modern Sovereign | `1.0.7+26.1.2` | `1.0.28+26.1.2` ➔ `1.0.29+26.1.2` ➔ `1.1.0+26.1.2` ➔ `1.2.0+26.1.2` ➔ `1.3.0+26.1.2` | ⏸️ **On Parity Hold** | Sovereign parity (`1.0.28`), Knot safety (`1.0.29`), visual scaling (`1.1.0`), explosion pre-clump (`1.2.0`), label wall culling (`1.3.0`). |
| **MC 1.21.11** | Older Anchor | `1.0.1+1.21.11` | `1.1.0+1.21.11` ➔ `1.2.0+1.21.11` ➔ `1.3.0+1.21.11` | 🟢 **Ready to Publish** | Dynamic visual scaling (`1.1.0`), explosion drop pre-clumping (`1.2.0`), label wall culling (`1.3.0`). |
| **MC 1.21.1** | Older Anchor | `1.0.1+1.21.1` | `1.1.0+1.21.1` ➔ `1.2.0+1.21.1` ➔ `1.3.0+1.21.1` | 🟢 **Ready to Publish** | Dynamic visual scaling (`1.1.0`), explosion drop pre-clumping (`1.2.0`), label wall culling (`1.3.0`). |
| **MC 1.20.1** | Legacy Anchor | `1.0.1+1.20.1` | `1.1.0+1.20.1` ➔ `1.2.0+1.20.1` ➔ `1.3.0+1.20.1` | 🟢 **Ready to Publish** | Dynamic visual scaling (`1.1.0`), explosion drop pre-clumping (`1.2.0`), label wall culling (`1.3.0`). |


---

## 🏛️ Project Operating Rules & Architectural Invariants

1. **📜 Subproject Changelog & Queue Segregation Law**:
   - Each version anchor subproject directory maintains its own dedicated `CHANGELOG.md` and `RELEASE_QUEUE.md` tracking solely that Minecraft version anchor with Strict Version Anchor Exclusivity.
