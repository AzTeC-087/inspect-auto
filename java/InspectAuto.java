/*
 * Inspect Auto \u2013 clickable prototype for Sprint 1 (User 001: view a car's mechanical condition).
 * JAVA VERSION: the same website as index.html, but the pages are built by Java.
 *
 * HOW TO RUN (needs Java 17 or newer, no libraries, no build tool):
 *   1. Open a terminal in this "java" folder.
 *   2. Run:  java InspectAuto.java
 *   3. Open http://localhost:8080 in your browser.  Stop the server with Ctrl+C.
 *
 * HOW TO EDIT:
 *   - Add / change cars ........... CARS list (SECTION 2)
 *   - Change the filter choices ... PRICE_CHOICES / MILEAGE_CHOICES (SECTION 2)
 *   - Change the listings page .... listPage() (SECTION 4)
 *   - Change the car details page . detailPage() (SECTION 5)
 *   - Change colours / layout ..... CSS (SECTION 6)
 *
 * All cars, garages and figures are invented sample data.
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class InspectAuto {

    // ===== SECTION 1: DATA TYPES =====

    /** One row of the Mechanical Condition report. status is "Good", "Fair" or "Needs work"; cost in \u00a3 (0 = none). */
    record Check(String area, String status, String notes, int cost) {}

    /** One car listing. colour = colour of the photo placeholder boxes; rating = inspection rating out of 5. */
    record Car(int id, String make, String model, int year, int price, int mileage, String location,
               String fuel, String gearbox, int owners, String colour, double rating,
               String inspected, String garage, List<Check> report) {}

    // ===== SECTION 2: CAR DATA (all invented) =====
    // To add a car: copy one new Car(...) block, give it the next id number, and change the values.

    static final List<Car> CARS = List.of(
        new Car(1, "Ford", "Fiesta 1.0 EcoBoost Zetec", 2017, 7495, 48200, "Luton", "Petrol", "Manual", 2, "#2b6cb0", 4.5,
            "3 Nov 2026", "Bury Park Motors (partner garage)", List.of(
                new Check("Engine", "Good", "Starts cleanly, no warning lights, oil changed at last service.", 0),
                new Check("Brakes", "Good", "Pads 70% remaining, discs within limits.", 0),
                new Check("Tyres", "Fair", "Rear tyres at 3 mm; replace within 6 months.", 120),
                new Check("Suspension", "Good", "No knocks on road test.", 0),
                new Check("Bodywork", "Good", "Minor stone chips on bonnet only.", 0),
                new Check("Electrics", "Good", "All lights, windows and infotainment working.", 0))),
        new Car(2, "Vauxhall", "Corsa 1.4 SE", 2015, 4250, 72900, "Watford", "Petrol", "Manual", 4, "#718096", 3.0,
            "28 Oct 2026", "Oxhey Auto Care (partner garage)", List.of(
                new Check("Engine", "Good", "Runs smoothly; timing chain quiet.", 0),
                new Check("Brakes", "Needs work", "Front pads worn to 2 mm and discs lipped; replace both.", 260),
                new Check("Tyres", "Good", "All four at 5 mm or more.", 0),
                new Check("Suspension", "Fair", "Front drop links knocking over bumps.", 180),
                new Check("Bodywork", "Fair", "Scuffed rear bumper and dent in driver's door.", 150),
                new Check("Electrics", "Good", "No faults found.", 0))),
        new Car(3, "Volkswagen", "Golf 1.6 TDI Match", 2019, 11995, 61400, "Uxbridge", "Diesel", "Manual", 1, "#c53030", 4.0,
            "5 Nov 2026", "Hillingdon Service Centre (partner garage)", List.of(
                new Check("Engine", "Fair", "Small oil seep from cam cover gasket; no leak onto ground.", 140),
                new Check("Brakes", "Good", "Pads and discs in good condition.", 0),
                new Check("Tyres", "Good", "Matching brand, 6 mm all round.", 0),
                new Check("Suspension", "Good", "No issues on road test.", 0),
                new Check("Bodywork", "Good", "Clean, no visible repairs.", 0),
                new Check("Electrics", "Good", "DPF regeneration completed during test drive; no warning lights.", 0))),
        new Car(4, "Toyota", "Yaris 1.5 Hybrid Icon", 2018, 10450, 39800, "Slough", "Hybrid", "Automatic", 1, "#38a169", 5.0,
            "1 Nov 2026", "Langley Hybrid Specialists (partner garage)", List.of(
                new Check("Engine", "Good", "Hybrid battery health check passed.", 0),
                new Check("Brakes", "Good", "Low wear thanks to regenerative braking.", 0),
                new Check("Tyres", "Good", "All four at 6 mm.", 0),
                new Check("Suspension", "Good", "No faults found.", 0),
                new Check("Bodywork", "Good", "Excellent condition.", 0),
                new Check("Electrics", "Good", "Reversing camera and all systems working.", 0))),
        new Car(5, "BMW", "320d SE", 2014, 6750, 98300, "Reading", "Diesel", "Automatic", 3, "#1a202c", 2.5,
            "30 Oct 2026", "Caversham Car Clinic (partner garage)", List.of(
                new Check("Engine", "Needs work", "Timing chain rattle on cold start; replacement recommended soon.", 950),
                new Check("Brakes", "Good", "Recently replaced, receipts seen.", 0),
                new Check("Tyres", "Fair", "Front tyres at 3 mm.", 180),
                new Check("Suspension", "Needs work", "Front lower arm bushes split.", 320),
                new Check("Bodywork", "Fair", "Kerbed alloys and faded paint on roof.", 0),
                new Check("Electrics", "Good", "No faults stored.", 0))),
        new Car(6, "Kia", "Picanto 1.0 2", 2020, 8295, 24600, "Hemel Hempstead", "Petrol", "Manual", 1, "#d69e2e", 4.5,
            "4 Nov 2026", "Apsley Motor Works (partner garage)", List.of(
                new Check("Engine", "Good", "Full manufacturer service history.", 0),
                new Check("Brakes", "Good", "Pads 80% remaining.", 0),
                new Check("Tyres", "Good", "All four at 7 mm.", 0),
                new Check("Suspension", "Good", "No issues.", 0),
                new Check("Bodywork", "Fair", "Stone chips on front bumper.", 90),
                new Check("Electrics", "Good", "All working; remaining manufacturer warranty until 2027.", 0)))
    );

    /** Choices shown in the "Max price" and "Max mileage" filters. */
    static final List<Integer> PRICE_CHOICES = List.of(5000, 7500, 10000, 12500);
    static final List<Integer> MILEAGE_CHOICES = List.of(30000, 50000, 75000, 100000);

    // ===== SECTION 3: SERVER =====
    // "/" shows the listings (filters arrive as ?make=...&maxPrice=...), "/car?id=3" shows car 3.

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", InspectAuto::handle);
        server.start();
        System.out.println("Inspect Auto running: open http://localhost:8080  (Ctrl+C to stop)");
    }

    static void handle(HttpExchange ex) throws IOException {
        Map<String, String> q = query(ex.getRequestURI().getRawQuery());
        String path = ex.getRequestURI().getPath();
        String body;
        if (path.equals("/car")) {
            Car car = CARS.stream().filter(c -> String.valueOf(c.id()).equals(q.get("id"))).findFirst().orElse(null);
            body = car != null ? detailPage(car) : listPage(q);
        } else {
            body = listPage(q);
        }
        byte[] bytes = page(body).getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        ex.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
    }

    /** Turns "make=BMW&maxPrice=7500" into a map. Missing filters count as "" (Any). */
    static Map<String, String> query(String raw) {
        Map<String, String> m = new HashMap<>();
        if (raw == null) return m;
        for (String pair : raw.split("&")) {
            String[] kv = pair.split("=", 2);
            m.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                  kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "");
        }
        return m;
    }

    // ===== SECTION 4: LISTINGS PAGE =====
    // Keeps only the cars that match every chosen filter, then draws the filter bar and one card per car.

    static String listPage(Map<String, String> q) {
        String make = q.getOrDefault("make", ""), loc = q.getOrDefault("location", "");
        String maxPrice = q.getOrDefault("maxPrice", ""), maxMiles = q.getOrDefault("maxMiles", "");

        List<Car> shown = CARS.stream()
            .filter(c -> make.isEmpty() || c.make().equals(make))
            .filter(c -> maxPrice.isEmpty() || c.price() <= Integer.parseInt(maxPrice))
            .filter(c -> maxMiles.isEmpty() || c.mileage() <= Integer.parseInt(maxMiles))
            .filter(c -> loc.isEmpty() || c.location().equals(loc))
            .toList();

        List<String> makes = CARS.stream().map(Car::make).distinct().sorted().toList();
        List<String> places = CARS.stream().map(Car::location).distinct().sorted().toList();

        // The filter bar is a normal form: changing a dropdown re-submits it.
        StringBuilder sb = new StringBuilder("<form class=\"filters\" method=\"get\" action=\"/\">");
        sb.append(select("Make", "make", makes, make, s -> s));
        sb.append(select("Max price", "maxPrice", PRICE_CHOICES.stream().map(String::valueOf).toList(), maxPrice, s -> gbp(Integer.parseInt(s))));
        sb.append(select("Max mileage", "maxMiles", MILEAGE_CHOICES.stream().map(String::valueOf).toList(), maxMiles, s -> miles(Integer.parseInt(s))));
        sb.append(select("Location", "location", places, loc, s -> s));
        sb.append("<a class=\"clear\" href=\"/\">Clear filters</a></form>");

        sb.append("<div class=\"count\">").append(shown.size()).append(" inspected car").append(shown.size() == 1 ? "" : "s").append(" found</div>");
        if (shown.isEmpty()) return sb.append("<div class=\"empty\">No cars match these filters.</div>").toString();

        sb.append("<div class=\"grid\">");
        for (Car c : shown) {
            sb.append("<a class=\"card\" href=\"/car?id=").append(c.id()).append("\">")
              .append(photo(c, c.make() + " " + c.model()))
              .append("<div class=\"body\"><h3>").append(c.year()).append(' ').append(c.make()).append(' ').append(c.model()).append("</h3>")
              .append("<div class=\"price\">").append(gbp(c.price())).append("</div>")
              .append("<div class=\"meta\">").append(miles(c.mileage())).append(" \u00b7 ").append(c.fuel()).append(" \u00b7 ")
              .append(c.gearbox()).append(" \u00b7 ").append(c.location()).append("</div>")
              .append("<span class=\"badge rating\">Inspection rating ").append(String.format("%.1f", c.rating())).append(" / 5</span>")
              .append("<span class=\"badge ok\">HPI clear</span><span class=\"badge ok\">Seller ID verified</span></div></a>");
        }
        return sb.append("</div>").toString();
    }

    /** One dropdown in the filter bar. "" = Any; the chosen value stays selected after the page reloads. */
    static String select(String label, String name, List<String> values, String chosen, java.util.function.Function<String, String> fmt) {
        String opts = values.stream()
            .map(v -> "<option value=\"" + v + "\"" + (v.equals(chosen) ? " selected" : "") + ">" + fmt.apply(v) + "</option>")
            .collect(Collectors.joining());
        return "<label>" + label + "<select name=\"" + name + "\" onchange=\"this.form.submit()\"><option value=\"\">Any</option>" + opts + "</select></label>";
    }

    // ===== SECTION 5: CAR DETAILS PAGE =====
    // Photos (placeholders), specs, badges and the Mechanical Condition table with a total repair cost.

    static String detailPage(Car c) {
        int total = c.report().stream().mapToInt(Check::cost).sum();
        StringBuilder sb = new StringBuilder("<a class=\"back\" href=\"/\">\u2190 Back to listings</a><div class=\"detail\">");
        sb.append("<div class=\"gallery\">");
        for (String p : List.of("Front", "Interior", "Rear", "Engine bay", "Boot")) sb.append(photo(c, p));
        sb.append("</div><h2 style=\"margin:0\">").append(c.year()).append(' ').append(c.make()).append(' ').append(c.model()).append("</h2>")
          .append("<div class=\"price\">").append(gbp(c.price())).append("</div>")
          .append("<span class=\"badge rating\">Inspection rating ").append(String.format("%.1f", c.rating())).append(" / 5</span>")
          .append("<span class=\"badge ok\">HPI clear: no finance, not written off, not stolen</span><span class=\"badge ok\">Seller ID verified</span>")
          .append("<div class=\"specs\">")
          .append(spec("Mileage", miles(c.mileage()))).append(spec("Fuel", c.fuel())).append(spec("Gearbox", c.gearbox()))
          .append(spec("Previous owners", String.valueOf(c.owners()))).append(spec("Location", c.location())).append(spec("Inspected", c.inspected()))
          .append("</div><h3>Mechanical Condition</h3><table>")
          .append("<tr><th>Area</th><th>Status</th><th>Mechanic's notes</th><th>Est. repair cost</th></tr>");
        for (Check r : c.report()) {
            sb.append("<tr><td>").append(r.area()).append("</td><td><span class=\"st ").append(r.status().split(" ")[0]).append("\">")
              .append(r.status()).append("</span></td><td>").append(r.notes()).append("</td><td>")
              .append(r.cost() > 0 ? gbp(r.cost()) : "\u2013").append("</td></tr>");
        }
        sb.append("<tr class=\"total\"><td colspan=\"3\">Total estimated repair cost</td><td>").append(gbp(total)).append("</td></tr></table>")
          .append("<p class=\"note\">Inspected by ").append(c.garage()).append(". Prototype: all cars, garages and figures are invented sample data.</p></div>");
        return sb.toString();
    }

    // ===== HELPERS =====

    static String gbp(int n) { return "\u00a3" + NumberFormat.getIntegerInstance(Locale.UK).format(n); }
    static String miles(int n) { return NumberFormat.getIntegerInstance(Locale.UK).format(n) + " miles"; }
    static String photo(Car c, String label) { return "<div class=\"photo\" style=\"background:" + c.colour() + "\">" + label + "</div>"; }
    static String spec(String name, String value) { return "<div><b>" + name + "</b>" + value + "</div>"; }

    /** Wraps a page body in the shared header and styling. */
    static String page(String body) {
        return "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
            + "<title>Inspect Auto \u2013 Prototype (Java)</title><style>" + CSS + "</style></head><body>"
            + "<header><a href=\"/\">Inspect Auto</a><span>Every car inspected by a partner mechanic before it is listed</span></header>"
            + "<main>" + body + "</main></body></html>";
    }

    // ===== SECTION 6: STYLING (same look as index.html; #12324f is the main brand blue) =====

    static final String CSS = """
        * { box-sizing: border-box; }
        body { margin: 0; font-family: Segoe UI, Arial, sans-serif; background: #f3f5f8; color: #1d2733; }
        header { background: #12324f; color: #fff; padding: 14px 24px; display: flex; align-items: center; gap: 16px; }
        header a { color: #fff; text-decoration: none; font-size: 22px; font-weight: 700; }
        header span { opacity: .8; font-size: 14px; }
        main { max-width: 1100px; margin: 0 auto; padding: 20px; }
        .filters { background: #fff; border-radius: 8px; padding: 14px; display: flex; flex-wrap: wrap; gap: 14px; align-items: end; box-shadow: 0 1px 3px rgba(0,0,0,.08); }
        .filters label { display: flex; flex-direction: column; font-size: 13px; gap: 4px; }
        .filters select { padding: 6px 8px; min-width: 150px; }
        .clear { padding: 7px 12px; color: #12324f; }
        .count { margin: 14px 0 8px; font-size: 14px; color: #555; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 16px; }
        .card { background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 1px 3px rgba(0,0,0,.08); text-decoration: none; color: inherit; display: block; }
        .card:hover { box-shadow: 0 4px 12px rgba(0,0,0,.15); }
        .photo { height: 150px; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 18px; font-weight: 600; }
        .card .body { padding: 12px 14px; }
        .card h3 { margin: 0 0 4px; font-size: 17px; }
        .price { font-size: 20px; font-weight: 700; color: #12324f; }
        .meta { font-size: 13px; color: #555; margin-top: 4px; }
        .badge { display: inline-block; font-size: 12px; padding: 2px 8px; border-radius: 10px; margin: 6px 6px 0 0; }
        .rating { background: #12324f; color: #fff; }
        .ok { background: #e3f5e8; color: #1b6b34; }
        .empty { padding: 30px; text-align: center; background: #fff; border-radius: 8px; }
        .back { display: inline-block; margin-bottom: 12px; color: #12324f; }
        .detail { background: #fff; border-radius: 8px; padding: 20px; box-shadow: 0 1px 3px rgba(0,0,0,.08); }
        .gallery { display: grid; grid-template-columns: 2fr 1fr 1fr; gap: 8px; margin-bottom: 16px; }
        .gallery .photo { height: 120px; border-radius: 6px; font-size: 14px; }
        .gallery .photo:first-child { grid-row: span 2; height: 248px; font-size: 18px; }
        .specs { display: grid; grid-template-columns: repeat(auto-fill, minmax(170px, 1fr)); gap: 8px; margin: 12px 0 18px; }
        .specs div { background: #f3f5f8; padding: 8px 10px; border-radius: 6px; font-size: 14px; }
        .specs b { display: block; font-size: 12px; color: #666; font-weight: 400; }
        table { width: 100%; border-collapse: collapse; font-size: 14px; }
        th, td { text-align: left; padding: 9px 8px; border-bottom: 1px solid #e3e6ea; vertical-align: top; }
        th { background: #f3f5f8; }
        .st { font-weight: 600; padding: 2px 8px; border-radius: 10px; white-space: nowrap; }
        .Good { background: #e3f5e8; color: #1b6b34; }
        .Fair { background: #fff4d6; color: #8a5a00; }
        .Needs { background: #fde4e4; color: #a12020; }
        .total td { font-weight: 700; }
        .note { font-size: 13px; color: #555; margin-top: 10px; }
        """;
}
