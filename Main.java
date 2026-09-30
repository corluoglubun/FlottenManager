import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DatenbankManager.getVerbindung();

        // Flottenverwaltung erstellen
        Flottenverwaltung verwaltung = new Flottenverwaltung();

// Testdaten nur einfügen wenn Datenbank leer
if (verwaltung.istDatenbankLeer()) {
    // 10 Fahrzeuge
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(1, "HH-AB 123", "VW Golf", false));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(2, "HH-CD 456", "BMW i3", true));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(3, "HH-EF 789", "Tesla Model 3", true));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(4, "HH-GH 101", "Mercedes C-Klasse", false));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(5, "HH-IJ 202", "Audi A4", false));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(6, "HH-KL 303", "Volkswagen Passat", false));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(7, "HH-MN 404", "Toyota Prius", true));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(8, "HH-OP 505", "Porsche Taycan", true));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(9, "HH-QR 606", "Ford Focus", false));
    verwaltung.fahrzeugHinzufuegen(new Fahrzeug(10, "HH-ST 707", "Skoda Octavia", false));

    // 15 Mitarbeiter
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(1, "Bünyamin Corluoglu", "IT"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(2, "Anna Schmidt", "Vertrieb"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(3, "Max Müller", "Logistik"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(4, "Sarah Weber", "HR"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(5, "Thomas Becker", "Finanzen"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(6, "Julia Fischer", "Marketing"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(7, "Michael Wagner", "IT"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(8, "Laura Hoffmann", "Vertrieb"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(9, "Stefan Schulz", "Logistik"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(10, "Nina Braun", "Einkauf"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(11, "Klaus Zimmermann", "Produktion"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(12, "Petra Krause", "Qualität"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(13, "Andreas Hartmann", "IT"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(14, "Monika Lange", "Verwaltung"));
    verwaltung.mitarbeiterHinzufuegen(new Mitarbeiter(15, "Tobias Wolf", "Vertrieb"));
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