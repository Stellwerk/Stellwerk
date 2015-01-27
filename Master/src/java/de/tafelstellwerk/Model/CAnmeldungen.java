package de.tafelstellwerk.Model;

/**
 * Erstellt ein Objekt zur Überwachung der Anmeldungen
 *
 * @author Simon Lau
 */
public class CAnmeldungen {

    private String eMail;
    private long timestamp;
    private int fehlgeschlageneAnmeldungsVersuche;
    private boolean gesperrt5m;

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getFehlgeschlageneAnmeldungsVersuche() {
        return fehlgeschlageneAnmeldungsVersuche;
    }

    public void setFehlgeschlageneAnmeldungsVersuche(int fehlgeschlageneAnmeldungsVersuche) {
        this.fehlgeschlageneAnmeldungsVersuche = fehlgeschlageneAnmeldungsVersuche;
    }

    public boolean isGesperrt5m() {
        return gesperrt5m;
    }

    public void setGesperrt5m(boolean gesperrt5m) {
        this.gesperrt5m = gesperrt5m;
    }
}
