Draft prepared with AI assistance (Claude Code). Owner to review: Oguz Cayir.

# t007 – Garage registration fields (U002-1)

> "As a vehicle marketplace administrator, I want to register approved garages on the platform, so that trusted garages can provide vehicle inspection services to buyers."

This matches `GARAGE_FIELDS` and `wireAdmin()` in `index.html` (SECTION 6) exactly. Values are trimmed of leading/trailing spaces before they are checked. Only registering is in scope; editing or removing garages is U002-2.

## Fields

| # | Field (id) | Label | Required | Validation rule (as coded) | Example | Error message |
|---|---|---|---|---|---|---|
| 1 | `name` | Garage name | Required | Not empty | Bury Park Motors | Enter the garage name. |
| 2 | `address` | Street address | Required | Not empty | 12 Dunstable Road | Enter the street address. |
| 3 | `town` | Town or city | Required | Not empty | Luton | Enter the town or city. |
| 4 | `postcode` | Postcode | Required | `/^[A-Z]{1,2}\d[A-Z\d]? ?\d[A-Z]{2}$/i` (UK format, optional single space, any case). Hint: "For example LU1 3AA" | LU1 3AA | Enter a valid UK postcode, for example LU1 3AA. |
| 5 | `email` | Contact email | Required | `/^[^@\s]+@[^@\s]+\.[^@\s]+$/` (text@text.text, no spaces). Input type `email`, browser validation off (`novalidate`) | info@garage.co.uk | Enter a valid email address, for example info@garage.co.uk. |
| 6 | `phone` | Contact phone | Required | Spaces, brackets and dashes removed, a leading `+44` becomes `0`, then `/^0\d{9,10}$/` (0 then 9–10 digits). Input type `tel`. Hint: "For example 01582 123456" | 01582 123456 | Enter a UK phone number, for example 01582 123456. |
| 7 | `ref` | Approval reference | Required | At least 4 characters. Hint: "For example a trade body membership number" | TB-48213 | Enter the approval reference (at least 4 characters). |

All seven fields are required; there are no optional fields.

## Behaviour on submit

- All fields are checked at once. Each failing field gets its message under it, a red border (`aria-invalid="true"`) and keyboard focus moves to the first invalid field.
- On success the garage is added to **Registered garages** with the postcode in capitals and "Registered" set to today's date, the form is cleared and the message "<name> has been registered as an approved garage." gets focus.
- The list is kept in this browser's `localStorage` (key `inspectAutoGarages`). **Reset sample garages** restores the starting list (the six partner garages named on the cars, with only name and town filled and "Registered" = Sample).
- Typed text is escaped before it is shown, so HTML is displayed as text.

## Duplicate rule

A garage is rejected if one already in the list has the **same name** (ignoring upper/lower case) **and the same postcode** (ignoring spaces and case). The check only runs when name and postcode are both valid. The error is shown on the name field: "A garage with this name and postcode is already registered."

## Open questions for the team

1. **Approval reference.** What does "approved" mean in a real product: a trade body membership number, our own approval ID after a site visit, or something else? Who issues it, and should it have a fixed format?
2. **Duplicate rule.** Is name + postcode the right key? Two branches of a chain may share a name; one garage may be entered with a slightly different name. Should the approval reference be unique instead?
3. **Sample garages** have no address, postcode, email, phone or reference. Should the starting data be completed?
4. **Postcode storage.** It is stored in capitals but with whatever spacing was typed (`LU13AA` vs `LU1 3AA`). Should it be normalised to the standard format?
5. **Administrator access.** There is no login; the page says so. When is that needed (not Sprint 1)?

## Suggestions (not applied to the code)

- Store the postcode in standard format (outward code, space, inward code).
- Give the approval reference a defined format once question 1 is answered.
- Consider a maximum length for name and address.
- Consider making email or phone optional if one contact method is enough.
