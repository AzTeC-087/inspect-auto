# Inspect Auto – Sprint 1 prototype (CS3617, Group A11)

A clickable, front-end only prototype of a used-car marketplace where every car is inspected by a partner mechanic before it is listed. Plain HTML, CSS and JavaScript in one file, no backend.

**Live site:** https://aztec-087.github.io/inspect-auto/

All cars, garages and figures are **invented sample data**.

## How to open it

Double-click `index.html` (no install or internet needed), or use the live link.

## Demo: US 001 – view the mechanical condition of a vehicle

1. Open the listings page and click a car.
2. Scroll to **Mechanical Condition**: each area shows a status, the mechanic's notes and an estimated repair cost, with a total at the bottom.
3. Click **Back to listings**, open a second car and check its report shows different results (e.g. Vauxhall Corsa vs Toyota Yaris).

## Demo: U002-1 – register approved garages

1. Click **Admin: garages** in the header (or open `index.html#/admin/garages`).
2. Click **Register garage** with the form empty: each field shows an error and the first one gets focus.
3. Fill in every field (e.g. postcode `LU1 3AA`, phone `01582 123456`) and submit: a confirmation appears and the garage is added to **Registered garages**.
4. Refresh the page: the garage is still listed. **Reset sample garages** restores the starting list.

## What is in Sprint 1

Only US 001 and U002-1. Editing or removing garages is U002-2 (not in Sprint 1). Three extras from other backlog stories are switched off by `FEATURES` in `index.html` and stay off: the filter bar (User 003), the inspection rating (User 005) and the "HPI clear" / "Seller ID verified" badges (User 006).

## Docs

| File | What it is |
|---|---|
| `docs/inspection-report-content.md` | t001 – what the inspection report contains |
| `docs/garage-registration-fields.md` | t007 – garage registration fields and rules |
| `docs/wireframes/vehicle-details.svg` | t002 – vehicle details wireframe |
| `docs/wireframes/garage-registration.svg` | t008 – garage registration wireframe |
| `docs/design/condition-report-hifi.html`, `docs/design/design-notes.md` | t003 – high-fidelity condition report and design notes |
| `docs/test-report.md`, `docs/test-evidence/` | t006 / t011 – test report and screenshots |
| `docs/genai-log.md` | Record of generative AI use |

AI (Claude) was used for design and documentation of this prototype; see docs/genai-log.md.
