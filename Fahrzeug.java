public class Fahrzeug {
    
    private int id;
    private String kennzeichen;
    private String modell;
    private boolean istElektro;
    private boolean istVerfuegbar;

    public Fahrzeug(int id, String kennzeichen, String modell, boolean istElektro) {
        this.id = id;
        this.kennzeichen = kennzeichen;
        this.modell = modell;
        this.istElektro = istElektro;
        this.istVerfuegbar = true;
    }

    public int getId() { return id; }
    public String getKennzeichen() { return kennzeichen; }
    public String getModell() { return modell; }
    public boolean isIstElektro() { return istElektro; }
    public boolean isIstVerfuegbar() { return istVerfuegbar; }

    public void setVerfuegbar(boolean verfuegbar) {
        this.istVerfuegbar = verfuegbar;
    }

    @Override
    public String toString() {
        return "Fahrzeug [" + id + "] " + kennzeichen + 
               " - " + modell + 
               (istElektro ? " (E-Auto)" : "") + 
               (istVerfuegbar ? " verfuegbar" : " gebucht");
    }
}