import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DatenbankManager.getVerbindung();

        // Flottenverwaltung erstellen
        Flottenverwaltung verwaltung = new Flottenverwaltung();

// Testdaten nur einfügen wenn Datenbank leer
if (verwaltung.istDatenbankLeer()) {
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(1, "HH-AB 123", "VW Golf", false));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(2, "HH-CD 456", "BMW i3", true));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(3, "HH-EF 789", "Tesla Model 3", true));

    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(1, "Bünyamin Corluoglu", "IT"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(2, "Anna Schmidt", "Vertrieb"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(3, "Max Müller", "Logistik"));
    System.out.println("Testdaten eingefügt.");
} else {
    System.out.println("Daten bereits vorhanden.");
}

verwaltung.datenAusDatenbankLaden();

        // Menü
        Scanner scanner = new Scanner(System.in);
        boolean laufen = true;

        while (laufen) {
            System.out.println("\n=============================");
            System.out.println("   FLOTTEN MANAGER");
            System.out.println("=============================");
            System.out.println("1 - Alle Fahrzeuge anzeigen");
            System.out.println("2 - Verfügbare Fahrzeuge anzeigen");
            System.out.println("3 - Alle Mitarbeiter anzeigen");
            System.out.println("4 - Fahrzeug buchen");
            System.out.println("5 - Buchung stornieren");
            System.out.println("6 - Alle Buchungen anzeigen");
            System.out.println("0 - Beenden");
            System.out.println("=============================");
            System.out.print("Ihre Wahl: ");

            int wahl = scanner.nextInt();

            switch (wahl) {
                case 1:
                    verwaltung.alleFahrzeugeAnzeigen();
                    break;
                case 2:
                    verwaltung.verfuegbareFahrzeugeAnzeigen();
                    break;
                case 3:
                    verwaltung.alleMitarbeiterAnzeigen();
                    break;
                case 4:
                    System.out.print("Mitarbeiter-ID: ");
                    int mId = scanner.nextInt();
                    System.out.print("Fahrzeug-ID: ");
                    int fId = scanner.nextInt();
                    verwaltung.buchen(mId, fId, 
                        LocalDate.now(), 
                        LocalDate.now().plusDays(2));
                    break;
                case 5:
                    System.out.print("Buchungs-ID: ");
                    int bId = scanner.nextInt();
                    verwaltung.buchungStornieren(bId);
                    break;
                case 6:
                    verwaltung.alleBuchungenAnzeigen();
                    break;
                case 0:
                    System.out.println("Auf Wiedersehen!");
                    laufen = false;
                    break;
                default:
                    System.out.println("Ungültige Eingabe.");
            }
        }
        DatenbankManager.verbindungSchliessen();
        scanner.close();
    }
}