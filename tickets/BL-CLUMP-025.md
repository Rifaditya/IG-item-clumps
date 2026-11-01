# 🎫 [BL-CLUMP-025] Wiki Audit & Update to Latest Version: item-clumps

- **Ticket ID**: `[BL-CLUMP-025]`
- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Scope**: `GitHub Wiki (.wiki/ & Wiki/)`
- **Status**: `📌 DEFERRED`
- **Target Version**: `All Anchors`
- **Target Repository / Root**: `item-clumps`
- **Date Added**: 2026-10-31

---

## 🎯 1. Goal & Definition of Done (The 4-Pillar Objective Architecture)

1. **North Star Objective**: Audit the existing GitHub Wiki of **item-clumps** against the latest source code and version anchors with `wiki-auditor`, fix every discrepancy, and publish it to the live `.wiki.git`.
2. **In-Game Experience (Before vs. After)**:
   - *Before*: Existing wiki content: `.wiki/` (19 pages). It may lag behind the latest `mod_version`, GameRules, commands, Mixins and anchors.
   - *After*: Players and developers get an accurate, code-backed wiki matching the current source for every supported anchor, with working `[[wikilinks]]` and the repository-source disclaimer.
3. **🚫 Anti-Goals (Off-Limits / Do Not Touch)**:
   - ❌ Do not modify Java source or bump `mod_version` in this ticket.
   - ❌ Do not document features, configs, or commands absent from `src/main/java/` or `src/main/resources/` (Zero-Phantom Law).
   - ❌ Do not use corporate "we" voice; use the Solo Indie ("I") voice.
4. **✅ Definition of Done (Binary Verification Checklist)**:
   - [ ] 10-phase `wiki-auditor` report shows 0 discrepancies.
   - [ ] 100% of `[[wikilinks]]` resolve; no YAML frontmatter in `.wiki/`.
   - [ ] `.wiki/` mirrored to `Wiki/` and pushed to the `.wiki.git` remote (transient `.git` purged).
   - [ ] Changes committed to the main repository.

---

## ❓ 2. Problem Statement & Player Impact

Outdated or missing wiki pages leave players and modpack authors guessing GameRule bounds, command syntax, version support, and uninstall behavior. Storefront Factual Accuracy requires documentation to match the Java source.

---

## 🔬 3. Architectural Blueprint & Target Pages

- `Home.md` (version selector portal or overview), `_Sidebar.md`, `_Footer.md`, `Version-Compatibility.md`.
- Subsystem pages discovered from the source packages (infobox, formulas, diagrams, reference tables).
- `GameRules.md` / `Configuration.md` with the **Uninstallation & World Save Lifecycle** section if the mod uses GameRules.
- Language suite (11 languages) kept in parity with the English pages if localized pages already exist.
- Skills: [wiki-maker](file:///e:/Minecraft%20Project/.agents/skills/wiki-maker/SKILL.md), [wiki-auditor](file:///e:/Minecraft%20Project/.agents/skills/wiki-auditor/SKILL.md).

---

## ⚓ 4. Multi-Era Anchor Parity Specifications

- Cover every anchor actually compiled for this mod (cross-check `Archive Jar of all versions/` and subproject folders): `1.20.1`, `1.21.1`, `1.21.11`, `26.1.2`, `26.2`, `26.3+`.
- Version pages stay isolated per anchor (Pure Isolated Version Tree Law); no phantom entries for uncompiled versions.

---

## 🛡️ 5. Edge Cases, Collisions & Failure Modes

- Client-only mods: omit GameRules/Commands/Advancements pages and state the vanilla reliance on Home.
- Stale or renamed pages must be deleted from `.wiki/` and `Wiki/` before publishing.
- Table-cell wikilinks must escape the pipe as `&#124;`; external links use plain Markdown.

---

## 🚀 6. Implementation Steps (Execution Plan)

1. Phase 1 discovery and Phase 2 symbol manifest (in the session scratch area, not on Drive E:).
2. Update, add, and purge pages per the blueprint.
3. Run the `wiki-auditor` 10-phase audit; fix until 0 discrepancies.
4. Mirror to `Wiki/`, push to `.wiki.git` master, purge transient `.git`, commit main repo.

---

## 🏁 7. 5-Pillar Verification Checklist

- [ ] **Pillar 1 (Schema & Snippets)**: All JSON/SNBT/Java snippets are copied from real source.
- [ ] **Pillar 2 (Multi-Era Parity)**: Each anchor's page tree matches that anchor's code.
- [ ] **Pillar 3 (Commands & GameRules)**: Keys, bounds, defaults, and syntax match source.
- [ ] **Pillar 4 (Code-to-Wiki Match)**: Mixin targets, `@At` points, records, and formulas match.
- [ ] **Pillar 5 (Links & Voice)**: `[[wikilinks]]` 100% resolved; Solo Indie voice; author `Dasik (Rifaditya)`.
