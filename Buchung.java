import java.time.LocalDate;

public class Buchung {

    private int id;
    private Mitarbeiter mitarbeiter;
    private Fahrzeug fahrzeug;
    private LocalDate startDatum;
    private LocalDate endDatum;
    private boolean aktiv;

    public Buchung(int id, Mitarbeiter mitarbeiter, Fahrzeug fahrzeug, 
                   LocalDate startDatum, LocalDate endDatum) {
        this.id = id;
        this.mitarbeiter = mitarbeiter;
        this.fahrzeug = fahrzeug;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
        this.aktiv = true;
    }

    public int getId() { return id; }
    public Mitarbeiter getMitarbeiter() { return mitarbeiter; }
    public Fahrzeug getFahrzeug() { return fahrzeug; }
    public LocalDate getStartDatum() { return startDatum; }
    public LocalDate getEndDatum() { return endDatum; }
    public boolean isAktiv() { return aktiv; }

    public void stornieren() {
        this.aktiv = false;
        fahrzeug.setVerfuegbar(true);
        mitarbeiter.buchungEntfernen();
    }

    @Override
    public String toString() {
        return "Buchung [" + id + "] " +
               mitarbeiter.getName() + " → " + 
               fahrzeug.getKennzeichen() +
               " | " + startDatum + " bis " + endDatum +
               (aktiv ? " (aktiv)" : " (storniert)");
    }
}