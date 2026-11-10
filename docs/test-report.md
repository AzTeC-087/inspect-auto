Draft prepared with AI assistance (Claude Code). Owner to review: Yaasir Hassan (US 001), Jalil Rashid (U002-1).

# t006 / t011 – Sprint 1 test report

Tests follow each story's "how to demo". They were run on 10 Nov 2026 with Playwright (Chromium) against `index.html` opened from `file://` on branch `sprint1-completion`, at **1280 px** (desktop) and **360 px** (phone) wide. Each width used a fresh browser profile, so the garage list started from the six sample garages. The test script was kept outside the repo. Screenshots are in `docs/test-evidence/` (full page; PNG is the only non-text file type in the repo, agreed for evidence).

Results recorded below are the ones observed in that run. **Summary: desktop 15/15 passed, phone 15/15 passed, no failures, no changes to `index.html` were needed.**

## US 001 – view the mechanical condition of a vehicle (t006)

### Desktop (1280 px)

| # | Step | Expected | Actual | Result | Screenshot |
|---|---|---|---|---|---|
| 1 | Open the listings page | Listings show the inspected cars | 6 car cards shown, heading "Inspected cars" | Pass | [us001-desktop-01-listings.png](test-evidence/us001-desktop-01-listings.png) |
| 2 | Select a car (first card) and scroll to Mechanical Condition | Section shown; every row has a status, mechanic's notes and an estimated repair cost | /car/1: heading found=true; 6 rows, all with status/notes/cost=true (statuses: Good, Good, Fair, Good, Good, Good) | Pass | [us001-desktop-02-car1-condition.png](test-evidence/us001-desktop-02-car1-condition.png) |
| 3 | Check the total row | Total estimated repair cost equals the sum of the row costs | car 1: rows sum £120, total shows £120; car 2: rows sum £590, total shows £590; car 3: rows sum £140, total shows £140; car 4: rows sum £0, total shows £0; car 5: rows sum £1450, total shows £1,450; car 6: rows sum £90, total shows £90 | Pass | – |
| 4 | Go back to listings and open a second car (Vauxhall Corsa) | Back link returns to listings; second report shows different results | back to listings=true; /car/2 statuses: Good, Needs work, Good, Fair, Fair, Good; total £590; differs from car 1=true | Pass | [us001-desktop-03-car2-condition.png](test-evidence/us001-desktop-03-car2-condition.png) |
| 5 | No horizontal scrolling at 1280 px | Page is never wider than the window | scrollWidth <= 1280 on listings and all 6 car pages | Pass | – |
| 6 | No console errors | Console has no errors | none | Pass | – |

### Phone (360 px)

| # | Step | Expected | Actual | Result | Screenshot |
|---|---|---|---|---|---|
| 1 | Open the listings page | Listings show the inspected cars | 6 car cards shown, heading "Inspected cars" | Pass | [us001-phone-01-listings.png](test-evidence/us001-phone-01-listings.png) |
| 2 | Select a car (first card) and scroll to Mechanical Condition | Section shown; every row has a status, mechanic's notes and an estimated repair cost | /car/1: heading found=true; 6 rows, all with status/notes/cost=true (statuses: Good, Good, Fair, Good, Good, Good) | Pass | [us001-phone-02-car1-condition.png](test-evidence/us001-phone-02-car1-condition.png) |
| 3 | Check the total row | Total estimated repair cost equals the sum of the row costs | car 1: rows sum £120, total shows £120; car 2: rows sum £590, total shows £590; car 3: rows sum £140, total shows £140; car 4: rows sum £0, total shows £0; car 5: rows sum £1450, total shows £1,450; car 6: rows sum £90, total shows £90 | Pass | – |
| 4 | Go back to listings and open a second car (Vauxhall Corsa) | Back link returns to listings; second report shows different results | back to listings=true; /car/2 statuses: Good, Needs work, Good, Fair, Fair, Good; total £590; differs from car 1=true | Pass | [us001-phone-03-car2-condition.png](test-evidence/us001-phone-03-car2-condition.png) |
| 5 | No horizontal scrolling at 360 px | Page is never wider than the window | scrollWidth <= 360 on listings and all 6 car pages | Pass | – |
| 6 | No console errors | Console has no errors | none | Pass | – |

## U002-1 – register approved garages (t011)

### Desktop (1280 px)

| # | Step | Expected | Actual | Result | Screenshot |
|---|---|---|---|---|---|
| 1 | Click "Admin: garages" | Registration form and registered garages list shown | heading "Register an approved garage", 7 inputs, 6 garages listed | Pass | [u002-1-desktop-01-empty-form.png](test-evidence/u002-1-desktop-01-empty-form.png) |
| 2 | Submit the empty form | Every field shows an error; focus moves to the first invalid field (Garage name) | 7 errors shown; focused element: #g-name | Pass | [u002-1-desktop-02-empty-submit-errors.png](test-evidence/u002-1-desktop-02-empty-submit-errors.png) |
| 3 | Enter postcode "12345", email "garage@", phone "12345", reference "AB" (other fields valid) and submit | Postcode, email, phone and reference each show their error message; nothing else is flagged | postcode: "Enter a valid UK postcode, for example LU1 3AA."; email: "Enter a valid email address, for example info@garage.co.uk."; phone: "Enter a UK phone number, for example 01582 123456."; ref: "Enter the approval reference (at least 4 characters)."; focus on #g-postcode | Pass | [u002-1-desktop-03-invalid-values.png](test-evidence/u002-1-desktop-03-invalid-values.png) |
| 4 | Fill in a valid garage and submit | Confirmation shown and the garage is added to the list | message "Hatters Lane Garage has been registered as an approved garage."; list has 7 garages, new row found=true | Pass | [u002-1-desktop-04-valid-garage-added.png](test-evidence/u002-1-desktop-04-valid-garage-added.png) |
| 5 | Register the same garage again as "hatters lane garage", postcode "lu13aa" | Rejected as a duplicate (same name and postcode); list unchanged | name error "A garage with this name and postcode is already registered."; list still has 7 garages | Pass | [u002-1-desktop-05-duplicate-rejected.png](test-evidence/u002-1-desktop-05-duplicate-rejected.png) |
| 6 | Reload the page | The registered garage is still listed | after reload: 7 garages, Hatters Lane Garage found=true | Pass | [u002-1-desktop-06-after-reload.png](test-evidence/u002-1-desktop-06-after-reload.png) |
| 7 | Register a garage named `<img src=x onerror="window.__xss=1"><b>Bold Garage</b>` | The name is shown as plain text; no image, bold or script runs | script ran=false; img elements=0; bold elements=0; dialogs=0; list shows "`<img src=x onerror="window.__xss=1"><b>Bold Garage</b>`" | Pass | [u002-1-desktop-07-html-shown-as-text.png](test-evidence/u002-1-desktop-07-html-shown-as-text.png) |
| 8 | No horizontal scrolling at 1280 px (garage page with 8 garages) | Page is never wider than the window | scrollWidth 1280, window 1280 | Pass | – |
| 9 | No console errors | Console has no errors | none | Pass | – |

### Phone (360 px)

| # | Step | Expected | Actual | Result | Screenshot |
|---|---|---|---|---|---|
| 1 | Click "Admin: garages" | Registration form and registered garages list shown | heading "Register an approved garage", 7 inputs, 6 garages listed | Pass | [u002-1-phone-01-empty-form.png](test-evidence/u002-1-phone-01-empty-form.png) |
| 2 | Submit the empty form | Every field shows an error; focus moves to the first invalid field (Garage name) | 7 errors shown; focused element: #g-name | Pass | [u002-1-phone-02-empty-submit-errors.png](test-evidence/u002-1-phone-02-empty-submit-errors.png) |
| 3 | Enter postcode "12345", email "garage@", phone "12345", reference "AB" (other fields valid) and submit | Postcode, email, phone and reference each show their error message; nothing else is flagged | postcode: "Enter a valid UK postcode, for example LU1 3AA."; email: "Enter a valid email address, for example info@garage.co.uk."; phone: "Enter a UK phone number, for example 01582 123456."; ref: "Enter the approval reference (at least 4 characters)."; focus on #g-postcode | Pass | [u002-1-phone-03-invalid-values.png](test-evidence/u002-1-phone-03-invalid-values.png) |
| 4 | Fill in a valid garage and submit | Confirmation shown and the garage is added to the list | message "Hatters Lane Garage has been registered as an approved garage."; list has 7 garages, new row found=true | Pass | [u002-1-phone-04-valid-garage-added.png](test-evidence/u002-1-phone-04-valid-garage-added.png) |
| 5 | Register the same garage again as "hatters lane garage", postcode "lu13aa" | Rejected as a duplicate (same name and postcode); list unchanged | name error "A garage with this name and postcode is already registered."; list still has 7 garages | Pass | [u002-1-phone-05-duplicate-rejected.png](test-evidence/u002-1-phone-05-duplicate-rejected.png) |
| 6 | Reload the page | The registered garage is still listed | after reload: 7 garages, Hatters Lane Garage found=true | Pass | [u002-1-phone-06-after-reload.png](test-evidence/u002-1-phone-06-after-reload.png) |
| 7 | Register a garage named `<img src=x onerror="window.__xss=1"><b>Bold Garage</b>` | The name is shown as plain text; no image, bold or script runs | script ran=false; img elements=0; bold elements=0; dialogs=0; list shows "`<img src=x onerror="window.__xss=1"><b>Bold Garage</b>`" | Pass | [u002-1-phone-07-html-shown-as-text.png](test-evidence/u002-1-phone-07-html-shown-as-text.png) |
| 8 | No horizontal scrolling at 360 px (garage page with 8 garages) | Page is never wider than the window | scrollWidth 360, window 360 | Pass | – |
| 9 | No console errors | Console has no errors | none | Pass | – |

## Notes

- "Total" was checked on all six cars, not only the two in the demo.
- The no-scroll check compares the page's `scrollWidth` with the window width on the listings page, all six car pages and the garage page.
- The tests check behaviour, not looks. The owners should still look through the screenshots.
- Not tested here: other browsers (only Chromium), screen readers, and the live GitHub Pages site.

## Sign-off

US 001 (t006)
Tester: ____ Date: ____

U002-1 (t011)
Tester: ____ Date: ____
