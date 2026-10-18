# 🎫 [BL-CLUMP-020] BACKLOG.md Control-Character Corruption Repair & Formula Rendering

- **Category**: `[TECH_DEBT]`
- **Priority**: `[MEDIUM]`
- **Target Version**: Docs (No Anchor — no JAR, no SemVer bump)
- **Status**: `✅ RESOLVED`
- **Target Component(s)**: `item-clumps/BACKLOG.md`, `MASTER_BACKLOG_BOARD.md`
- **Date Added**: 2026-10-03

#### ❓ Problem / Context
A prior write pipeline interpreted backslash sequences as escape codes: backslash-t became a literal TAB (`0x09`) and backslash-0 became a NUL byte (`0x00`). This silently corrupted ticket specifications (e.g. `true` renders as `<TAB>rue`, `0.1x` loses its leading digit). NUL bytes can also break downstream parsers (`sync_backlog_board.py`, regex scrapers, Git diffs treating the file as binary).

Additionally, the `[BL-CLUMP-001]` scaling formula is raw LaTeX without `$$` delimiters, so it does not render.

#### 💡 Proposed Solution & Technical Specifications
Replace corrupted characters with backtick-wrapped literals (spec meaning unchanged):

| Ticket | Corrupted Text | Restored Text |
| :--- | :--- | :--- |
| `[BL-CLUMP-001]` | `default: <TAB>rue` | `default: true` |
| `[BL-CLUMP-001]` | `from <NUL>.1x … from <NUL>.5x` | `from 0.1x … from 0.5x` |
| `[BL-CLUMP-010]` | `before <TAB>ryToMerge` | `before tryToMerge` |
| `[BL-CLUMP-011]` | `<TAB>ags/item/unclumpable.json` | `tags/item/unclumpable.json` |
| `[BL-CLUMP-011]` | `In <TAB>ryToMerge` | `In tryToMerge` |
| `[BL-CLUMP-015]` | `default: <TAB>rue` | `default: true` |

- Wrap the `[BL-CLUMP-001]` scaling formula in `$$ … $$` so it renders as display math.
- Preserve CRLF line endings and UTF-8 encoding (no BOM change).
- Re-run `python .agents/skills/backlog-manager/scripts/sync_backlog_board.py` so `MASTER_BACKLOG_BOARD.md` reflects clean ticket titles.
- **Root-cause guard**: When authoring backlog text, never emit bare backslash escapes (backslash-t, backslash-0, backslash-n) outside of code spans or math delimiters.

#### 🧪 Verification & Acceptance Criteria
- [x] Control-character scan returns zero hits (any char `< 0x20` other than CR/LF) across `item-clumps/BACKLOG.md`.
- [x] All 6 corrupted locations read correctly; ticket scope/specs unchanged.
- [x] `[BL-CLUMP-001]` formula renders as math in Markdown preview.
- [x] `MASTER_BACKLOG_BOARD.md` re-synced.
- [x] Workspace-wide sweep: other `BACKLOG.md` files scanned for the same corruption; any hits spawn companion tickets in their own project backlogs. *(117 `.md` files scanned 2026-10-03 — zero other hits, no companion tickets needed.)*
