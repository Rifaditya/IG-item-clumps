# Troubleshooting & FAQ (MC 26.1.2)

This document addresses frequently asked questions, common troubleshooting scenarios, and performance diagnostics for **Item Clumps** on **Minecraft 26.1.2**.

---

## ❓ Frequently Asked Questions

### Q1: Do players connecting to my server need to install this mod?
**No.** Item Clumps is **100% server-side only**. Un-modded vanilla Minecraft clients can connect without any extra mods or resource packs. The floating item count labels render natively using vanilla entity custom name tags.

---

### Q2: Why don't my items stack to 9,999 inside my chest or inventory?
**This is intentional.** Item Clumps is engineered strictly as a ground entity performance optimization mod. It intentionally avoids touching player inventory or container stack limits to ensure zero inventory pollution, zero crafting glitches, and 100% compatibility with vanilla mechanics.

---

### Q3: Will high-yield mob farms cause items to despawn?
**No.** Item Clumps implements **Youngest Age Inheritance** ($\min(\text{age}_A, \text{age}_B)$). Every time a newly dropped item merges into an existing mega-clump, the despawn timer of the entire clump resets to `0`. As long as your farm is actively dropping items, your items will never despawn.

---

### Q4: When do idle clumps despawn?
Once a farm stops producing and a clump sits idle with no new items merging into it, the standard vanilla 5-minute despawn timer ($6,000\text{ ticks} = 300\text{s}$) counts down normally and deletes the clump to prevent long-term world save bloat.

---

### Q5: How do I hide floating count labels?
Run the following in-game command:
```text
/gamerule item_clumps:render_labels false
```

---

## 🛠️ Diagnostic Matrix

| Issue | Root Cause | Solution |
| :--- | :--- | :--- |
| **Items not clumping** | `item_clumps:enable_clumping` is set to `false` | Run `/gamerule item_clumps:enable_clumping true` |
| **Hopper not draining** | Hopper container is full | Clear space inside the hopper inventory |
| **Labels not showing** | Clump count is below vanilla max stack size | Increase item count above stack size ($> 64$) |

---

## 🔗 Related Documentation
* [[GameRules Reference|GameRules]]
* [[Despawn Age Rules|Despawn-Timer-and-Age-Inheritance]]
* [[Configuration Suite|Configuration]]
