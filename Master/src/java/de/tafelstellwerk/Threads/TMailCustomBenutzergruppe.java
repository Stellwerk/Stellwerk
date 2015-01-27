package de.tafelstellwerk.Threads;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import java.util.List;

/**
 *
 * @author gpaschke
 * @param rolle
 * @param betreff
 * @param nachricht
 */
public class TMailCustomBenutzergruppe implements Runnable {

    private CBenutzer.RolleEnum rolle;
    private String betreff;
    private String nachricht;

    /**
     *
     * @param rolle
     * @param betreff
     * @param nachricht
     */
    public TMailCustomBenutzergruppe(CBenutzer.RolleEnum rolle, String betreff, String nachricht) {
        this.rolle = rolle;
        this.betreff = betreff;
        this.nachricht = nachricht;
    }

    /**
     * Sendet eine benutzerdefinierte Mail an jeden Benutzer der Rolle
     */
    @Override
    public void run() {
        if (rolle != null && betreff != null && nachricht != null) {
            CEMail eMailVerteiler = CEMail.createInstance();
            eMailVerteiler.setSender("info@tafel-stellwerk-nshb.de");
            eMailVerteiler.setBetreff(betreff);
            eMailVerteiler.setNachricht(nachricht);
            //Alle Koordinatoren aus der DB in liste einlesen
            List<CBenutzer> liste = CBenutzerDAO.getBenutzerListeByRolle(rolle);
            if (liste.isEmpty()) {
                System.out.println("Fehler: Es wurde kein Koordinator in der Datenbank gefunden! Kein Senden einer Spendenbenachrichtigungsmail möglich...");
            } else {
                //Senden einer EMail pro Koordinator
                for (int i = 0; i < liste.size(); i++) {
                    eMailVerteiler.setEmpfaenger(liste.get(i).getEMail()); // EMail adresse des i ten Benutzers wird festgelegt
                    eMailVerteiler.sendeMail(); // EMail senden
                }
            }
        }
    }
}
