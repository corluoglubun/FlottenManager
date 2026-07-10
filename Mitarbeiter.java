public class Mitarbeiter {

    private int id;
    private String name;
    private String abteilung;
    private int aktiveBuchungen; // Wie viele Buchungen hat er gerade?

    public Mitarbeiter(int id, String name, String abteilung) {
        this.id = id;
        this.name = name;
        this.abteilung = abteilung;
        this.aktiveBuchungen = 0; // Neu = noch keine Buchungen
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAbteilung() { return abteilung; }
    public int getAktiveBuchungen() { return aktiveBuchungen; }

    public void buchungHinzufuegen() {
        this.aktiveBuchungen++;
    }

    public void buchungEntfernen() {
        if (aktiveBuchungen > 0) {
            this.aktiveBuchungen--;
        }
    }

    public boolean darfBuchen() {
        return aktiveBuchungen < 2; // Regel: max. 2 Buchungen gleichzeitig
    }

    @Override
    public String toString() {
        return "Mitarbeiter [" + id + "] " + name + 
               " (" + abteilung + ")" +
               " - Aktive Buchungen: " + aktiveBuchungen;
    }
}