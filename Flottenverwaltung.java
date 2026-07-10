import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Flottenverwaltung {

    private ArrayList<Fahrzeug> fahrzeuge;
    private ArrayList<Mitarbeiter> mitarbeiter;
    private ArrayList<Buchung> buchungen;
    private int naechsteBuchungsId;

    // Konstruktor — erstellt leere Listen
    public Flottenverwaltung() {
        this.fahrzeuge = new ArrayList<>();
        this.mitarbeiter = new ArrayList<>();
        this.buchungen = new ArrayList<>();
        this.naechsteBuchungsId = 1;
    }

    // ===== FAHRZEUG METHODEN =====

    public void fahrzeugHinzufuegen(Fahrzeug f) {
    // 1. In die Liste hinzufügen (wie vorher)
    fahrzeuge.add(f);

    // 2. In die Datenbank speichern (neu!)
    String sql = "INSERT INTO fahrzeuge (id, kennzeichen, modell, ist_elektro, ist_verfuegbar) VALUES (?, ?, ?, ?, ?)";
    
    try {
        Connection con = DatenbankManager.getVerbindung();
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, f.getId());
        stmt.setString(2, f.getKennzeichen());
        stmt.setString(3, f.getModell());
        stmt.setBoolean(4, f.isIstElektro());
        stmt.setBoolean(5, f.isIstVerfuegbar());
        
        stmt.executeUpdate();
        System.out.println("Fahrzeug in Datenbank gespeichert: " + f.getKennzeichen());
        
    } catch (SQLException e) {
        System.out.println("Fehler beim Speichern: " + e.getMessage());
    }
}

    public void alleFahrzeugeAnzeigen() {
        System.out.println("\n=== ALLE FAHRZEUGE ===");
        if (fahrzeuge.isEmpty()) {
            System.out.println("Keine Fahrzeuge vorhanden.");
            return;
        }
        for (Fahrzeug f : fahrzeuge) {
            System.out.println(f);
        }
    }

    public void verfuegbareFahrzeugeAnzeigen() {
        System.out.println("\n=== VERFÜGBARE FAHRZEUGE ===");
        boolean gefunden = false;
        for (Fahrzeug f : fahrzeuge) {
            if (f.isIstVerfuegbar()) {
                System.out.println(f);
                gefunden = true;
            }
        }
        if (!gefunden) {
            System.out.println("Keine Fahrzeuge verfügbar.");
        }
    }

    // ===== MITARBEITER METHODEN =====

 public void mitarbeiterHinzufuegen(Mitarbeiter m) {
    // 1. In die Liste hinzufügen
    mitarbeiter.add(m);

    // 2. In die Datenbank speichern
    String sql = "INSERT INTO mitarbeiter (id, name, abteilung, aktive_buchungen) VALUES (?, ?, ?, ?)";
    
    try {
        Connection con = DatenbankManager.getVerbindung();
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, m.getId());
        stmt.setString(2, m.getName());
        stmt.setString(3, m.getAbteilung());
        stmt.setInt(4, m.getAktiveBuchungen());
        
        stmt.executeUpdate();
        System.out.println("Mitarbeiter in Datenbank gespeichert: " + m.getName());
        
    } catch (SQLException e) {
        System.out.println("Fehler beim Speichern: " + e.getMessage());
    }
}
    public void alleMitarbeiterAnzeigen() {
        System.out.println("\n=== ALLE MITARBEITER ===");
        if (mitarbeiter.isEmpty()) {
            System.out.println("Keine Mitarbeiter vorhanden.");
            return;
        }
        for (Mitarbeiter m : mitarbeiter) {
            System.out.println(m);
        }
    }

    // ===== BUCHUNG METHODEN =====

    public void buchen(int mitarbeiterId, int fahrzeugId, 
                       LocalDate start, LocalDate ende) {

        // Mitarbeiter suchen
        Mitarbeiter m = mitarbeiterSuchen(mitarbeiterId);
        if (m == null) {
            System.out.println("✗ Mitarbeiter nicht gefunden.");
            return;
        }

        // Fahrzeug suchen
        Fahrzeug f = fahrzeugSuchen(fahrzeugId);
        if (f == null) {
            System.out.println("✗ Fahrzeug nicht gefunden.");
            return;
        }

        // Business Rules prüfen
        if (!m.darfBuchen()) {
            System.out.println("✗ " + m.getName() + 
                " hat bereits 2 aktive Buchungen.");
            return;
        }

        if (!f.isIstVerfuegbar()) {
            System.out.println("✗ Fahrzeug " + 
                f.getKennzeichen() + " ist nicht verfügbar.");
            return;
        }

        // Buchung erstellen
        Buchung b = new Buchung(naechsteBuchungsId++, m, f, start, ende);
        buchungen.add(b);
        f.setVerfuegbar(false);
        // Fahrzeugstatus in DB aktualisieren
try {
    Connection con = DatenbankManager.getVerbindung();
    PreparedStatement stmt = con.prepareStatement(
"UPDATE fahrzeuge SET ist_verfuegbar = 0 WHERE id = ?");
    stmt.setInt(1, f.getId());
    stmt.executeUpdate();
} catch (SQLException e) {
    System.out.println("Fehler beim Aktualisieren: " + e.getMessage());
}
        m.buchungHinzufuegen();

        System.out.println("✓ Buchung erfolgreich: " + b);
        // Buchung in Datenbank speichern
String sql = "INSERT INTO buchungen (mitarbeiter_id, fahrzeug_id, start_datum, end_datum, aktiv) VALUES (?, ?, ?, ?, ?)";
try {
    Connection con = DatenbankManager.getVerbindung();
    PreparedStatement stmt = con.prepareStatement(sql);
    
    stmt.setInt(1, m.getId());
    stmt.setInt(2, f.getId());
    stmt.setDate(3, java.sql.Date.valueOf(start));
    stmt.setDate(4, java.sql.Date.valueOf(ende));
    stmt.setBoolean(5, true);
    
    stmt.executeUpdate();
    System.out.println("Buchung in Datenbank gespeichert.");
    
} catch (SQLException e) {
    System.out.println("Fehler beim Speichern der Buchung: " + e.getMessage());
}
    }

    public void buchungStornieren(int buchungsId) {
        for (Buchung b : buchungen) {
            if (b.getId() == buchungsId && b.isAktiv()) {
                b.stornieren();
                System.out.println("✓ Buchung " + buchungsId + " storniert.");
                return;
            }
        }
        System.out.println("✗ Buchung nicht gefunden oder bereits storniert.");
    }

    public void alleBuchungenAnzeigen() {
        System.out.println("\n=== ALLE BUCHUNGEN ===");
        if (buchungen.isEmpty()) {
            System.out.println("Keine Buchungen vorhanden.");
            return;
        }
        for (Buchung b : buchungen) {
            System.out.println(b);
        }
    }

    // ===== HILFSMETHODEN =====

    private Mitarbeiter mitarbeiterSuchen(int id) {
        for (Mitarbeiter m : mitarbeiter) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    private Fahrzeug fahrzeugSuchen(int id) {
        for (Fahrzeug f : fahrzeuge) {
            if (f.getId() == id) return f;
        }
        return null;
    }
    public boolean istDatenbankLeer() {
    try {
        Connection con = DatenbankManager.getVerbindung();
        PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM fahrzeuge");
        var result = stmt.executeQuery();
        result.next();
        return result.getInt(1) == 0;
    } catch (SQLException e) {
        return true;
    }
}
public void datenAusDatenbankLaden() {
    try {
        Connection con = DatenbankManager.getVerbindung();
        
        // Fahrzeuge laden
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM fahrzeuge");
        var result = stmt.executeQuery();
        while (result.next()) {
            Fahrzeug f = new Fahrzeug(
                result.getInt("id"),
                result.getString("kennzeichen"),
                result.getString("modell"),
                result.getBoolean("ist_elektro")
            );
            f.setVerfuegbar(result.getBoolean("ist_verfuegbar"));
            fahrzeuge.add(f);
        }
        
        // Mitarbeiter laden
        stmt = con.prepareStatement("SELECT * FROM mitarbeiter");
        result = stmt.executeQuery();
        while (result.next()) {
            Mitarbeiter m = new Mitarbeiter(
                result.getInt("id"),
                result.getString("name"),
                result.getString("abteilung")
            );
            mitarbeiter.add(m);
        }
        
        System.out.println("Daten aus Datenbank geladen: " + 
            fahrzeuge.size() + " Fahrzeuge, " + 
            mitarbeiter.size() + " Mitarbeiter.");
            
    } catch (SQLException e) {
        System.out.println("Fehler beim Laden: " + e.getMessage());
    }
}
}