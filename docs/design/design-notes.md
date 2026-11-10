Draft prepared with AI assistance (Claude Code). Owner to review: Kyash Sidhu.

# t003 – Design notes for the condition report

Companion to `condition-report-hifi.html`. All values are taken from the `<style>` section of `index.html`; nothing in the prototype was restyled.

## Colour tokens

| Token (suggested name) | Value | Used for |
|---|---|---|
| `brand` | `#12324f` | Header background, price, back link, buttons |
| `text` | `#1d2733` | Body text |
| `page-bg` | `#f3f5f8` | Page background, table header, spec tiles |
| `surface` | `#ffffff` | Cards (details card, form) |
| `muted` | `#555555` | Notes, meta text, phone row labels |
| `muted-2` | `#666666` | Spec tile labels |
| `border` | `#e3e6ea` | Table row lines |
| `input-border` | `#8a94a0` | Form input borders |
| `good-fg` / `good-bg` | `#1b6b34` / `#e3f5e8` | Good status, "Inspected" badge, success message |
| `fair-fg` / `fair-bg` | `#8a5a00` / `#fff4d6` | Fair status |
| `needs-fg` / `needs-bg` | `#a12020` / `#fde4e4` | Needs work status; `#a12020` is also the error text and invalid input border |
| `focus` | `#f5a623` | 3 px keyboard focus outline |

## Type

Font stack: `Segoe UI, Arial, sans-serif` (falls back to Arial on Mac/Linux).

| Element | Size | Weight |
|---|---|---|
| Logo | 22 px | 700 |
| Page heading (h1) | 24 px | bold |
| Section heading "Mechanical Condition" (h2) | 18 px | bold |
| Price | 20 px | 700 |
| Table text, specs | 14 px | 400 (status label 600) |
| Notes, meta, error text | 13 px | 400 |
| Spec labels, form hints, badges | 12 px | 400 |

## Spacing

- Content max width 1100 px, page padding 20 px (14 px under 640 px).
- Card padding 20 px, corner radius 8 px, shadow `0 1px 3px rgba(0,0,0,.08)`.
- Table cells 9 px × 8 px padding, 1 px row line.
- Status label: 2 px × 8 px padding, radius 10 px (pill).
- Under 640 px every table row becomes a stacked card; column names appear as 108 px labels.

## Contrast (WCAG 2.1, computed with the relative luminance formula)

AA needs 4.5:1 for normal text and 3:1 for large text and for non-text items such as focus outlines.

| Pair | Foreground / background | Ratio | AA |
|---|---|---|---|
| **Good** status | `#1b6b34` on `#e3f5e8` | 5.78:1 | Pass |
| **Fair** status | `#8a5a00` on `#fff4d6` | 5.41:1 | Pass |
| **Needs work** status | `#a12020` on `#fde4e4` | 6.34:1 | Pass |
| Body text on page | `#1d2733` on `#f3f5f8` | 13.83:1 | Pass |
| Body text on card | `#1d2733` on `#ffffff` | 15.11:1 | Pass |
| Header text | `#ffffff` on `#12324f` | 13.17:1 | Pass |
| Header tagline (white at 80% opacity ≈ `#d0d6dc`) | on `#12324f` | 8.99:1 | Pass |
| Price / back link | `#12324f` on `#ffffff` | 13.17:1 | Pass |
| Notes / meta | `#555555` on `#ffffff` | 7.46:1 | Pass |
| Notes on page background | `#555555` on `#f3f5f8` | 6.83:1 | Pass |
| Spec labels | `#666666` on `#f3f5f8` | 5.26:1 | Pass |
| Error text | `#a12020` on `#ffffff` | 7.66:1 | Pass |
| Error text by invalid input | `#a12020` on `#fff8f8` | 7.30:1 | Pass |
| Input border (non-text) | `#8a94a0` on `#ffffff` | 3.08:1 | Pass |
| Focus outline (non-text) | `#f5a623` on `#ffffff` | 2.03:1 | **Fail** |
| Focus outline (non-text) | `#f5a623` on `#f3f5f8` | 1.86:1 | **Fail** |
| Focus outline (non-text) | `#f5a623` on `#12324f` (header links) | 6.50:1 | Pass |

All three status colour pairs pass AA. The status is also written as a word, so colour is never the only signal.

## Findings

1. **Focus outline contrast (not changed).** The orange focus outline is below 3:1 against white cards and the light page background, so keyboard users may struggle to see where focus is on the form and listing cards. A darker outline (for example the brand blue `#12324f`, 13.17:1 on white) or a two-colour outline would fix it. This is outside the status colours, so it is left for the team to decide.
2. **Garage and date above the table.** The hi-fi design keeps the prototype layout (garage note under the table) but adds the inspection date to that note. Moving both above the table is an open question in `docs/inspection-report-content.md`.
