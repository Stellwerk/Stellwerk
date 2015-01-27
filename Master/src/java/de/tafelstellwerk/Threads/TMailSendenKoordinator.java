package de.tafelstellwerk.Threads;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import java.util.List;

/**
 *
 * @author gpaschke
 * @param id der Mailvorlage
 */
public class TMailSendenKoordinator implements Runnable {

    private int ID = 0;

    public TMailSendenKoordinator(int id) {
        this.ID = id;
    }

    /**
     * Sendet eine Spendenbenachrichtigungsmail an jeden Koordinator
     */
    @Override
    public void run() {
        if (ID != 0) {
            CEMail eMailVerteiler = CEMail.createInstance();
            eMailVerteiler.setSender("info@tafel-stellwerk-nshb.de");
            //Alle Koordinatoren aus der DB in liste einlesen
            List<CBenutzer> liste = CBenutzerDAO.getBenutzerListeByRolle(CBenutzer.RolleEnum.Koordinator);
            if (liste.isEmpty()) {
                System.out.println("Fehler: Es wurde kein Koordinator in der Datenbank gefunden! Kein Senden einer Spendenbenachrichtigungsmail möglich...");
            } else {
                //Senden einer EMail pro Koordinator
                for (int i = 0; i < liste.size(); i++) {
                    eMailVerteiler.setEmpfaenger(liste.get(i).getEMail()); // EMail adresse des i ten Koordinators wird
                    eMailVerteiler.sendeMailausImport(ID); // Spendenbestätigungsmail
                }
            }
        }
    }
}
