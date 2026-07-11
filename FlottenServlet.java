import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

public class FlottenServlet extends HttpServlet {

    private static final String URL = "jdbc:mariadb://localhost:3307/flottenmanager";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String aktion = request.getParameter("aktion");

        out.println("<!DOCTYPE html>");
        out.println("<html lang='de'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>FlottenManager</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: -apple-system, BlinkMacSystemFont, 'SF Pro Display', sans-serif; min-height: 100vh; background: linear-gradient(135deg, #0a0a2e 0%, #0d1b4b 30%, #0a3d3d 60%, #0d2b1a 100%); color: white; }");
        out.println(".bg-blur { position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: 0; overflow: hidden; pointer-events: none; }");
        out.println(".bg-circle { position: absolute; border-radius: 50%; filter: blur(80px); opacity: 0.25; }");
        out.println(".bg-circle-1 { width: 600px; height: 600px; background: radial-gradient(circle, #00c7be, transparent); top: -100px; left: -100px; }");
        out.println(".bg-circle-2 { width: 500px; height: 500px; background: radial-gradient(circle, #007aff, transparent); top: 50%; right: -100px; }");
        out.println(".bg-circle-3 { width: 400px; height: 400px; background: radial-gradient(circle, #34c759, transparent); bottom: -100px; left: 30%; }");
        out.println(".header { background: rgba(255,255,255,0.06); backdrop-filter: blur(30px); border-bottom: 1px solid rgba(255,255,255,0.1); padding: 18px 40px; position: sticky; top: 0; z-index: 100; display: flex; align-items: center; justify-content: space-between; }");
        out.println(".header h1 { font-size: 20px; font-weight: 700; letter-spacing: -0.5px; color: white; }");
        out.println(".nav { display: flex; gap: 8px; }");
        out.println(".nav a { padding: 8px 18px; border-radius: 20px; font-size: 14px; font-weight: 500; text-decoration: none; transition: all 0.2s; }");
        out.println(".nav a.primary { background: rgba(255,255,255,0.9); color: #0a0a2e; }");
        out.println(".nav a.primary:hover { background: white; }");
        out.println(".nav a.secondary { background: rgba(255,255,255,0.1); color: white; border: 1px solid rgba(255,255,255,0.15); }");
        out.println(".nav a.secondary:hover { background: rgba(255,255,255,0.18); }");
        out.println(".container { max-width: 1200px; margin: 48px auto; padding: 0 24px; position: relative; z-index: 1; }");
        out.println(".section-title { font-size: 26px; font-weight: 700; letter-spacing: -0.5px; margin-bottom: 20px; color: white; }");
        out.println(".cards { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; margin-bottom: 56px; grid-auto-rows: 1fr; }");
        out.println(".card { background: rgba(255,255,255,0.07); backdrop-filter: blur(20px); border: 1px solid rgba(255,255,255,0.12); border-radius: 24px; padding: 26px; transition: transform 0.2s, box-shadow 0.2s; display: flex; flex-direction: column; }");
        out.println(".card:hover { transform: translateY(-4px); box-shadow: 0 20px 60px rgba(0,0,0,0.3); background: rgba(255,255,255,0.11); }");
        out.println(".card-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 14px; }");
        out.println(".card-title { font-size: 18px; font-weight: 700; color: white; }");
        out.println(".card-body { flex: 1; }");
        out.println(".badge { padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 600; }");
        out.println(".badge-verfuegbar { background: rgba(52,199,89,0.2); color: #34c759; border: 1px solid rgba(52,199,89,0.3); }");
        out.println(".badge-gebucht { background: rgba(255,59,48,0.15); color: #ff6b6b; border: 1px solid rgba(255,59,48,0.25); }");
        out.println(".card-info { font-size: 14px; color: rgba(255,255,255,0.55); margin-bottom: 6px; }");
        out.println(".card-info.elektro { color: #00c7be; }");
        out.println(".spacer { flex: 1; }");
        out.println(".btn-buchen { display: block; width: 100%; padding: 13px; background: rgba(255,255,255,0.12); color: white; border: 1px solid rgba(255,255,255,0.2); border-radius: 14px; font-size: 15px; font-weight: 600; cursor: pointer; text-align: center; text-decoration: none; margin-top: 18px; transition: all 0.2s; }");
        out.println(".btn-buchen:hover { background: rgba(255,255,255,0.22); }");
        out.println(".btn-storno { display: block; width: 100%; padding: 13px; background: rgba(255,59,48,0.1); color: #ff6b6b; border: 1px solid rgba(255,59,48,0.2); border-radius: 14px; font-size: 15px; font-weight: 600; cursor: pointer; margin-top: 18px; transition: all 0.2s; }");
        out.println(".btn-storno:hover { background: rgba(255,59,48,0.2); }");
        out.println(".circle-wrap { display: flex; flex-direction: column; align-items: center; margin: 20px 0 8px; position: relative; }");
        out.println(".circle-time { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center; width: 110px; }");
        out.println(".circle-time .hours { font-size: 18px; font-weight: 700; color: white; letter-spacing: -0.5px; }");
        out.println(".circle-time .label { font-size: 10px; color: rgba(255,255,255,0.4); margin-top: 2px; }");
        out.println(".circle-label { font-size: 11px; color: rgba(255,255,255,0.35); margin-top: 8px; }");
        out.println(".form-card { background: rgba(255,255,255,0.07); backdrop-filter: blur(20px); border: 1px solid rgba(255,255,255,0.12); border-radius: 24px; padding: 32px; max-width: 480px; }");
        out.println(".form-group { margin-bottom: 20px; }");
        out.println(".form-group label { display: block; font-size: 11px; font-weight: 600; color: rgba(255,255,255,0.4); margin-bottom: 8px; text-transform: uppercase; letter-spacing: 1px; }");
        out.println(".form-group select, .form-group input { width: 100%; padding: 13px 16px; border: 1px solid rgba(255,255,255,0.15); border-radius: 12px; font-size: 15px; outline: none; background: rgba(255,255,255,0.08); color: white; transition: all 0.2s; }");
        out.println(".form-group select:focus, .form-group input:focus { border-color: rgba(255,255,255,0.35); background: rgba(255,255,255,0.12); }");
        out.println(".form-group select option { background: #0d1b4b; }");
        out.println(".form-group input[type='date']::-webkit-calendar-picker-indicator { filter: invert(1); }");
        out.println(".checkbox-row { display: flex; align-items: center; gap: 10px; padding: 13px 16px; border: 1px solid rgba(255,255,255,0.15); border-radius: 12px; background: rgba(255,255,255,0.08); color: rgba(255,255,255,0.8); cursor: pointer; }");
        out.println(".checkbox-row input { width: 18px; height: 18px; accent-color: #00c7be; }");
        out.println(".btn-submit { width: 100%; padding: 15px; background: rgba(255,255,255,0.9); color: #0a0a2e; border: none; border-radius: 14px; font-size: 16px; font-weight: 700; cursor: pointer; margin-top: 8px; transition: all 0.2s; }");
        out.println(".btn-submit:hover { background: white; }");
        out.println(".empty { background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.08); border-radius: 24px; padding: 48px; text-align: center; color: rgba(255,255,255,0.35); }");
        out.println(".hint { font-size: 12px; color: rgba(255,255,255,0.35); margin-top: 6px; }");
        out.println("</style>");

        // JavaScript Timer — läuft clientseitig ohne Sprünge
        out.println("<script>");
        out.println("document.addEventListener('DOMContentLoaded', function(){");
        out.println("  setInterval(function(){");
        out.println("    document.querySelectorAll('.hours').forEach(function(el){");
        out.println("      var h=parseInt(el.dataset.h||0);");
        out.println("      var m=parseInt(el.dataset.m||0);");
        out.println("      var s=parseInt(el.dataset.s||0);");
        out.println("      s--;");
        out.println("      if(s<0){s=59;m--;}");
        out.println("      if(m<0){m=59;h--;}");
        out.println("      if(h<=0&&m<=0){location.reload();return;}");
        out.println("      el.dataset.h=h;el.dataset.m=m;el.dataset.s=s;");
        out.println("      el.textContent=h+'h '+m+'m';");
        out.println("    });");
        out.println("  }, 1000);");
        out.println("  setTimeout(function(){ location.reload(); }, 300000);");
        out.println("});");
        out.println("</script>");

        out.println("</head><body>");
        out.println("<div class='bg-blur'><div class='bg-circle bg-circle-1'></div><div class='bg-circle bg-circle-2'></div><div class='bg-circle bg-circle-3'></div></div>");

        out.println("<div class='header'>");
        out.println("<h1>FlottenManager</h1>");
        out.println("<nav class='nav'>");
        out.println("<a href='/flotten/fahrzeuge' class='secondary'>Übersicht</a>");
        out.println("<a href='/flotten/fahrzeuge?aktion=buchen' class='secondary'>Buchen</a>");
        out.println("<a href='/flotten/fahrzeuge?aktion=fahrzeug_hinzufuegen' class='secondary'>+ Fahrzeug</a>");
        out.println("<a href='/flotten/fahrzeuge?aktion=mitarbeiter_hinzufuegen' class='secondary'>+ Mitarbeiter</a>");
        out.println("</nav></div>");
        out.println("<div class='container'>");

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            con.prepareStatement("UPDATE buchungen SET aktiv = false WHERE end_datum < NOW() AND aktiv = true").executeUpdate();
            con.prepareStatement("UPDATE fahrzeuge SET ist_verfuegbar = 1 WHERE id NOT IN (SELECT fahrzeug_id FROM buchungen WHERE aktiv = true)").executeUpdate();
            con.prepareStatement("UPDATE fahrzeuge SET ist_verfuegbar = 0 WHERE id IN (SELECT fahrzeug_id FROM buchungen WHERE aktiv = true)").executeUpdate();

            if ("buchen".equals(aktion)) zeigeBuchenFormular(out, con);
            else if ("fahrzeug_hinzufuegen".equals(aktion)) zeigeFahrzeugFormular(out);
            else if ("mitarbeiter_hinzufuegen".equals(aktion)) zeigeMitarbeiterFormular(out);
            else zeigeUebersicht(out, con);

            con.close();
        } catch (SQLException e) {
            out.println("<p style='color:#ff6b6b'>Fehler: " + e.getMessage() + "</p>");
        }
        out.println("</div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String aktion = request.getParameter("aktion");
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            if ("buchen".equals(aktion)) {
                buchungErstellen(con,
                    Integer.parseInt(request.getParameter("mitarbeiter_id")),
                    Integer.parseInt(request.getParameter("fahrzeug_id")),
                    request.getParameter("end_datum"));
            } else if ("stornieren".equals(aktion)) {
                buchungStornieren(con, Integer.parseInt(request.getParameter("buchungs_id")));
            } else if ("fahrzeug_hinzufuegen".equals(aktion)) {
                fahrzeugHinzufuegen(con,
                    request.getParameter("kennzeichen"),
                    request.getParameter("modell"),
                    "on".equals(request.getParameter("ist_elektro")));
            } else if ("mitarbeiter_hinzufuegen".equals(aktion)) {
                mitarbeiterHinzufuegen(con,
                    request.getParameter("name"),
                    request.getParameter("abteilung"));
            }
            con.close();
        } catch (SQLException e) { e.printStackTrace(); }
        response.sendRedirect("/flotten/fahrzeuge");
    }

    private void zeigeUebersicht(PrintWriter out, Connection con) throws SQLException {

        out.println("<h2 class='section-title'>Verfügbare Fahrzeuge</h2>");
        out.println("<div class='cards'>");
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM fahrzeuge WHERE ist_verfuegbar = true ORDER BY id");
        ResultSet rs = stmt.executeQuery();
        boolean hat = false;
        while (rs.next()) {
            hat = true;
            out.println("<div class='card'>");
            out.println("<div class='card-header'><div class='card-title'>" + rs.getString("modell") + "</div><span class='badge badge-verfuegbar'>Verfügbar</span></div>");
            out.println("<div class='card-body'>");
            out.println("<p class='card-info'>" + rs.getString("kennzeichen") + "</p>");
            if (rs.getBoolean("ist_elektro")) out.println("<p class='card-info elektro'>Elektrofahrzeug</p>");
            out.println("</div><div class='spacer'></div>");
            out.println("<a href='/flotten/fahrzeuge?aktion=buchen&fahrzeug_id=" + rs.getInt("id") + "' class='btn-buchen'>Jetzt buchen</a>");
            out.println("</div>");
        }
        if (!hat) out.println("<div class='empty'>Alle Fahrzeuge sind momentan gebucht.</div>");
        out.println("</div>");

        out.println("<h2 class='section-title'>Gebuchte Fahrzeuge</h2>");
        out.println("<div class='cards'>");
        stmt = con.prepareStatement(
            "SELECT f.*, b.id as bid, b.start_datum, b.end_datum, m.name as mname " +
            "FROM fahrzeuge f JOIN buchungen b ON f.id = b.fahrzeug_id AND b.aktiv = true " +
            "JOIN mitarbeiter m ON b.mitarbeiter_id = m.id ORDER BY b.end_datum ASC");
        rs = stmt.executeQuery();
        hat = false;
        while (rs.next()) {
            hat = true;
            Timestamp endTs = rs.getTimestamp("end_datum");
            Timestamp startTs = rs.getTimestamp("start_datum");
            long jetzt = System.currentTimeMillis();
            long endMs = endTs != null ? endTs.getTime() : jetzt;
            long verbleibendMs = Math.max(0, endMs - jetzt);
            long verbleibendH = verbleibendMs / (1000 * 60 * 60);
            long verbleibendMin = (verbleibendMs / (1000 * 60)) % 60;
            long verbleibendSek = (verbleibendMs / 1000) % 60;

            // Immer 7 Tage als Referenz
            // prozent = wie viel von 7 Tagen bereits verbraucht wurde
            // Viel Zeit übrig = kleiner Balken, wenig Zeit übrig = großer Balken
            long siebenTageMs = 7L * 24 * 60 * 60 * 1000;
            double restAnteil = Math.min(1.0, (double) verbleibendMs / siebenTageMs);
            double prozent = 1.0 - restAnteil; // gefüllter Teil = bereits verbrauchte Zeit
            double restProzent = restAnteil;

            // Farbe basierend auf verbleibender Zeit
            String farbe1, farbe2;
            if (restProzent > 0.66) {
                // Noch viel Zeit — grün
                farbe1 = "rgba(52,199,89,0.95)";
                farbe2 = "rgba(0,210,180,0.85)";
            } else if (restProzent > 0.33) {
                // Mittlere Zeit — türkis
                farbe1 = "rgba(0,199,190,0.95)";
                farbe2 = "rgba(90,200,250,0.85)";
            } else {
                // Wenig Zeit — blau
                farbe1 = "rgba(90,200,250,0.95)";
                farbe2 = "rgba(0,122,255,0.85)";
            }

            double r = 56;
            double circ = 2 * Math.PI * r;
            double geladenLen = circ * prozent;
            double restLen = circ - geladenLen;

            out.println("<div class='card'>");
            out.println("<div class='card-header'><div class='card-title'>" + rs.getString("modell") + "</div><span class='badge badge-gebucht'>Gebucht</span></div>");
            out.println("<div class='card-body'>");
            out.println("<p class='card-info'>" + rs.getString("kennzeichen") + "</p>");
            out.println("<p class='card-info'>" + rs.getString("mname") + "</p>");
            out.println("</div>");

            // Glasiger Ladebalken — startet exakt bei 12 Uhr
            out.println("<div class='circle-wrap'>");
            out.println("<svg width='160' height='160' viewBox='0 0 160 160'>");
            out.println("<defs>");
            out.println("<linearGradient id='grad" + rs.getInt("bid") + "' x1='0%' y1='0%' x2='100%' y2='100%'>");
            out.println("<stop offset='0%' style='stop-color:" + farbe1 + "'/>");
            out.println("<stop offset='100%' style='stop-color:" + farbe2 + "'/>");
            out.println("</linearGradient>");
            out.println("<filter id='glow" + rs.getInt("bid") + "'>");
            out.println("<feGaussianBlur in='SourceGraphic' stdDeviation='4' result='blur'/>");
            out.println("<feComposite in='SourceGraphic' in2='blur' operator='over'/>");
            out.println("</filter>");
            out.println("<filter id='glass" + rs.getInt("bid") + "'>");
            out.println("<feGaussianBlur in='SourceGraphic' stdDeviation='1' result='blur'/>");
            out.println("<feComposite in='SourceGraphic' in2='blur' operator='over'/>");
            out.println("</filter>");
            out.println("</defs>");

            // Äußerer Glanzring
            out.println("<circle cx='80' cy='80' r='72' fill='none' stroke='rgba(255,255,255,0.03)' stroke-width='14'/>");

            // Hintergrundkreis gedämpft — zeigt den noch nicht geladenen Teil
            out.println("<circle cx='80' cy='80' r='" + r + "' fill='none' stroke='rgba(255,255,255,0.07)' stroke-width='11' filter='url(#glass" + rs.getInt("bid") + ")'/>");

            // Aktiver Ladebalken — startet exakt bei 12 Uhr (rotate -90 Grad)
          if (prozent > 0.005) {
    out.println("<circle cx='80' cy='80' r='" + r + "' fill='none' " +
        "stroke='url(#grad" + rs.getInt("bid") + ")' stroke-width='11' " +
        "stroke-dasharray='" + String.format("%.3f", geladenLen) + " " + String.format("%.3f", restLen) + "' " +
        "stroke-dashoffset='0' " +
        "stroke-linecap='butt' " +
        "transform='rotate(-90, 80, 80)' " +
        "filter='url(#glow" + rs.getInt("bid") + ")'/>");

    out.println("<circle cx='80' cy='80' r='" + r + "' fill='none' " +
        "stroke='url(#grad" + rs.getInt("bid") + ")' stroke-width='11' " +
        "stroke-dasharray='1 " + String.format("%.3f", circ - 1) + "' " +
        "stroke-dashoffset='" + String.format("%.3f", circ - geladenLen) + "' " +
        "stroke-linecap='round' " +
        "transform='rotate(-90, 80, 80)'/>");

    out.println("<circle cx='80' cy='80' r='" + r + "' fill='none' " +
        "stroke='rgba(255,255,255,0.8)' stroke-width='3' " +
        "stroke-dasharray='2 " + String.format("%.3f", circ - 2) + "' " +
        "stroke-dashoffset='" + String.format("%.3f", circ - geladenLen + 1) + "' " +
        "transform='rotate(-90, 80, 80)'/>");
}

            // Innerer Glanzring
            out.println("<circle cx='80' cy='80' r='" + (r - 7) + "' fill='none' stroke='rgba(255,255,255,0.02)' stroke-width='5'/>");
            out.println("</svg>");

            out.println("<div class='circle-time'>");
            out.println("<div class='hours' data-h='" + verbleibendH + "' data-m='" + verbleibendMin + "' data-s='" + verbleibendSek + "'>" + verbleibendH + "h " + verbleibendMin + "m</div>");
            out.println("<div class='label'>verbleibend</div>");
            out.println("</div></div>");
            out.println("<p class='circle-label' style='text-align:center'>Rückgabe: " + (endTs != null ? endTs.toString().substring(0, 10) : "-") + "</p>");

            out.println("<form method='post' action='/flotten/fahrzeuge'>");
            out.println("<input type='hidden' name='aktion' value='stornieren'>");
            out.println("<input type='hidden' name='buchungs_id' value='" + rs.getInt("bid") + "'>");
            out.println("<button class='btn-storno' type='submit'>Stornieren</button>");
            out.println("</form></div>");
        }
        if (!hat) out.println("<div class='empty'>Keine aktiven Buchungen vorhanden.</div>");
        out.println("</div>");
    }

    private void zeigeBuchenFormular(PrintWriter out, Connection con) throws SQLException {
        LocalDate heute = LocalDate.now();
        LocalDate maxDatum = heute.plusDays(7);
        out.println("<h2 class='section-title'>Fahrzeug buchen</h2>");
        out.println("<div class='form-card'>");
        out.println("<form method='post' action='/flotten/fahrzeuge'>");
        out.println("<input type='hidden' name='aktion' value='buchen'>");
        out.println("<div class='form-group'><label>Mitarbeiter</label><select name='mitarbeiter_id'>");
        ResultSet rs = con.prepareStatement("SELECT * FROM mitarbeiter ORDER BY name").executeQuery();
        while (rs.next()) out.println("<option value='" + rs.getInt("id") + "'>" + rs.getString("name") + " — " + rs.getString("abteilung") + "</option>");
        out.println("</select></div>");
        out.println("<div class='form-group'><label>Fahrzeug</label><select name='fahrzeug_id'>");
        rs = con.prepareStatement("SELECT * FROM fahrzeuge WHERE ist_verfuegbar = true ORDER BY modell").executeQuery();
        while (rs.next()) out.println("<option value='" + rs.getInt("id") + "'>" + rs.getString("kennzeichen") + " — " + rs.getString("modell") + "</option>");
        out.println("</select></div>");
        out.println("<div class='form-group'><label>Rückgabedatum (max. 7 Tage)</label>");
        out.println("<input type='date' name='end_datum' min='" + heute.plusDays(1) + "' max='" + maxDatum + "' required>");
        out.println("<p class='hint'>Maximale Buchungsdauer: 7 Tage</p></div>");
        out.println("<button class='btn-submit' type='submit'>Jetzt buchen</button>");
        out.println("</form></div>");
    }

    private void zeigeFahrzeugFormular(PrintWriter out) {
        out.println("<h2 class='section-title'>Neues Fahrzeug</h2>");
        out.println("<div class='form-card'>");
        out.println("<form method='post' action='/flotten/fahrzeuge'>");
        out.println("<input type='hidden' name='aktion' value='fahrzeug_hinzufuegen'>");
        out.println("<div class='form-group'><label>Kennzeichen</label><input type='text' name='kennzeichen' placeholder='HH-AB 123' required></div>");
        out.println("<div class='form-group'><label>Modell</label><input type='text' name='modell' placeholder='z.B. VW Passat' required></div>");
        out.println("<div class='form-group'><label>Fahrzeugtyp</label><label class='checkbox-row'><input type='checkbox' name='ist_elektro'> Elektrofahrzeug</label></div>");
        out.println("<button class='btn-submit' type='submit'>Fahrzeug hinzufügen</button>");
        out.println("</form></div>");
    }

    private void zeigeMitarbeiterFormular(PrintWriter out) {
        out.println("<h2 class='section-title'>Neuer Mitarbeiter</h2>");
        out.println("<div class='form-card'>");
        out.println("<form method='post' action='/flotten/fahrzeuge'>");
        out.println("<input type='hidden' name='aktion' value='mitarbeiter_hinzufuegen'>");
        out.println("<div class='form-group'><label>Name</label><input type='text' name='name' placeholder='Vor- und Nachname' required></div>");
        out.println("<div class='form-group'><label>Abteilung</label><input type='text' name='abteilung' placeholder='z.B. IT, Vertrieb' required></div>");
        out.println("<button class='btn-submit' type='submit'>Mitarbeiter hinzufügen</button>");
        out.println("</form></div>");
    }

    private void buchungErstellen(Connection con, int mId, int fId, String endDatum) throws SQLException {
        // Prüfen ob Mitarbeiter bereits eine aktive Buchung hat
        PreparedStatement check = con.prepareStatement(
            "SELECT COUNT(*) FROM buchungen WHERE mitarbeiter_id = ? AND aktiv = true");
        check.setInt(1, mId);
        ResultSet rs = check.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) return;

        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO buchungen (mitarbeiter_id, fahrzeug_id, start_datum, end_datum, aktiv) VALUES (?, ?, NOW(), ?, true)");
        stmt.setInt(1, mId);
        stmt.setInt(2, fId);
        stmt.setDate(3, Date.valueOf(endDatum));
        stmt.executeUpdate();
        con.prepareStatement("UPDATE fahrzeuge SET ist_verfuegbar = 0 WHERE id = " + fId).executeUpdate();
    }

    private void buchungStornieren(Connection con, int bId) throws SQLException {
        ResultSet rs = con.prepareStatement("SELECT fahrzeug_id FROM buchungen WHERE id = " + bId).executeQuery();
        if (rs.next()) {
            int fId = rs.getInt("fahrzeug_id");
            con.prepareStatement("UPDATE buchungen SET aktiv = false WHERE id = " + bId).executeUpdate();
            con.prepareStatement("UPDATE fahrzeuge SET ist_verfuegbar = 1 WHERE id = " + fId).executeUpdate();
        }
    }

    private void fahrzeugHinzufuegen(Connection con, String k, String m, boolean e) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO fahrzeuge (kennzeichen, modell, ist_elektro, ist_verfuegbar) VALUES (?, ?, ?, true)");
        stmt.setString(1, k); stmt.setString(2, m); stmt.setBoolean(3, e);
        stmt.executeUpdate();
    }

    private void mitarbeiterHinzufuegen(Connection con, String name, String abt) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
            "INSERT INTO mitarbeiter (name, abteilung, aktive_buchungen) VALUES (?, ?, 0)");
        stmt.setString(1, name); stmt.setString(2, abt);
        stmt.executeUpdate();
    }
}