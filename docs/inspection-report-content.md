# t001 – Inspection report content (US 001)

> "As a customer I want to view the mechanical condition of a vehicle so that I have an idea of what I'm purchasing."

This describes what the Mechanical Condition report in `index.html` shows today (SECTION 1 data, SECTION 4 `detailPage()`). Anything the prototype does not do is listed under open questions, not described as a feature.

## Report header details

| Detail | Where it comes from | Where it is shown now |
|---|---|---|
| Inspection date | `inspected` on each car, e.g. `3 Nov 2026` | "Inspected" tile in the specs grid on the details page, and the "Inspected …" badge on the listing card |
| Partner garage | `garage` on each car, e.g. `Bury Park Motors (partner garage)` | Note under the table: "Inspected by … ." |

## Inspected areas

Every car has the same six areas, in this order. The "what the mechanic checks" column is our description of the checks implied by the sample notes; the prototype stores only the area name.

| Area | What the mechanic checks |
|---|---|
| Engine | Starting, warning lights, running, leaks, timing chain/belt noise, service history (for hybrids, battery health) |
| Brakes | Pad thickness, disc condition (wear, lip), general braking |
| Tyres | Tread depth on each tyre (mm), matching tyres |
| Suspension | Knocks or noises on the road test, bushes, drop links |
| Bodywork | Dents, scuffs, stone chips, paint, alloys, signs of repairs |
| Electrics | Lights, windows, infotainment, cameras, stored fault codes |

## Status scale

The status is shown as a coloured label (`.Good`, `.Fair`, `.Needs` classes).

| Status | Rule (draft) |
|---|---|
| Good | No fault found, or only cosmetic wear that needs no work. |
| Fair | Wear or a minor fault; safe to drive but should be fixed or watched in the coming months. |
| Needs work | A fault that should be repaired before or soon after buying. |

## Mechanic's notes

- One or two short sentences, plain English, written for a buyer, not a mechanic.
- Say what was found and, if anything is needed, what and when (e.g. "Rear tyres at 3 mm; replace within 6 months.").
- Use measurements where possible (mm, %), and mention evidence seen (e.g. "receipts seen").
- End with a full stop. No prices in the notes; the cost has its own column.

## Estimated repair cost and total

- Each row has a cost in whole pounds (GBP), stored as a number. `0` means no repair needed.
- A cost above 0 is shown as `£` with thousand separators (e.g. `£950`); a cost of 0 is shown as `–`.
- **Total estimated repair cost** = the sum of the six row costs. It is shown in the bottom row; when the sum is 0 it shows `£0`.

## Open questions for the team

1. **Fair with no cost.** BMW 320d Bodywork is "Fair" (kerbed alloys, faded paint) with a cost of 0. Should every Fair/Needs work row carry a cost, or is "Fair, £0" allowed for cosmetic items? (Car data is not changed by this task.)
2. **No real report header.** The inspection date sits in the specs grid and the garage in a note under the table. Should both appear directly above the Mechanical Condition table?
3. **"–" vs "£0".** Rows show `–` for no cost but the total shows `£0`. Pick one convention.
4. **Who wrote the notes.** The report shows the garage but not the mechanic. Is a mechanic name needed?
5. **Checks list.** The prototype only stores area names. Should the report list what was checked in each area (as above), and who owns that list?
6. **Status rules.** The rules above are a draft inferred from the sample data; the team should confirm them with the product owner.
