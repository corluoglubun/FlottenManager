import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatenbankManager {

    // Lokale Standardwerte können über Umgebungsvariablen überschrieben werden.
    private static final String URL = System.getenv().getOrDefault(
            "FLOTTEN_DB_URL",
            "jdbc:mariadb://localhost:3307/flottenmanager"
    );

    private static final String USER = System.getenv().getOrDefault(
            "FLOTTEN_DB_USER",
            "root"
    );

    private static final String PASSWORD = System.getenv().getOrDefault(
            "FLOTTEN_DB_PASSWORD",
            ""
    );

    private static Connection verbindung = null;

    // Verbindung herstellen
    public static Connection getVerbindung() {
        if (verbindung == null) {
            try {
                verbindung = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Datenbankverbindung erfolgreich!");
            } catch (SQLException e) {
                System.out.println(
                        "Fehler bei der Datenbankverbindung: " + e.getMessage()
                );
            }
        }

        return verbindung;
    }

    // Verbindung schließen
    public static void verbindungSchliessen() {
        if (verbindung != null) {
            try {
                verbindung.close();
                verbindung = null;
                System.out.println("Datenbankverbindung geschlossen.");
            } catch (SQLException e) {
                System.out.println(
                        "Fehler beim Schließen der Datenbankverbindung: "
                                + e.getMessage()
                );
                e.printStackTrace();
            }
        }
    }
}