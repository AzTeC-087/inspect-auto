# Inspect Auto – Sprint 1 prototype (Group A11)

A clickable prototype for **User 001**: *"As a customer I want to view the mechanical condition of a vehicle so that I have an idea of what I'm purchasing."*

**Live site:** https://aztec-087.github.io/inspect-auto/

All cars, garages and figures are **invented sample data**.
Built with AI assistance (Claude).

## Two copies of the same website

| Copy | File | How to open |
|---|---|---|
| HTML | `index.html` | Double-click it, or use the live link above. No internet or install needed. |
| Java | `java/InspectAuto.java` | Needs Java 17+. In the `java` folder run `java InspectAuto.java`, then open http://localhost:8080 |

Only the HTML copy is on the live link (GitHub Pages can't run Java).
If you change cars in one copy, change them in the other too so they match.

## How to demo (from our formative, User 001)

1. Open the listings page and click a car.
2. Scroll to **Mechanical Condition**: each area (engine, brakes, tyres, etc.) shows a status, the mechanic's notes and an estimated repair cost.
3. Click **Back to listings**, open a second car, and check its report shows different results (e.g. Vauxhall Corsa vs Toyota Yaris).

## How to edit (no install needed)

1. **Say in the group chat that you're editing**, so two people don't change the same file at once.
2. On this repo page, click the file (e.g. `index.html`), then the **pencil icon** (Edit this file).
3. Make your change. The comments in each file say where things are:
   - cars: **SECTION 1** in `index.html`, **SECTION 2** in `InspectAuto.java`
   - filters, listings page, car details page and styling each have their own marked section
4. Click **Commit changes**, write what you changed (e.g. "Added a 2016 Honda Jazz"), and commit to `main`.
5. The live site updates within about 1–2 minutes. Refresh to check it.

Every commit is saved with your name and the date, so the history shows who did what in Sprint 1.
