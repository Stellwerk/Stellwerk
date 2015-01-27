package de.tafelstellwerk.Threads;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Model.CEMailVorlage;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import java.util.List;

/**
 *
 * @author gpaschke
 * @param id der Mailvorlage
 */
public class TMailSendenKoordinatorMitKommentar implements Runnable {

    private int id = 0;
    private String kommentar;

    public TMailSendenKoordinatorMitKommentar(int id, String kommentar) {
        this.id = id;
        this.kommentar = kommentar;
    }

    /**
     * Sendet eine Spendenbenachrichtigungsmail an jeden Koordinator
     */
    @Override
    public void run() {
        if (id != 0) {

            //Alle Koordinatoren aus der DB in liste einlesen
            List<CBenutzer> liste = CBenutzerDAO.getBenutzerListeByRolle(CBenutzer.RolleEnum.Koordinator);
            if (liste.isEmpty()) {
                System.out.println("Fehler: Es wurde kein Koordinator in der Datenbank gefunden! Kein Senden einer Spendenbenachrichtigungsmail möglich...");
            } else {
                CEMail eMailVerteiler = CEMail.createInstance();
                eMailVerteiler.setSender("info@tafel-stellwerk-nshb.de");
                CEMailVorlage vorlagedb = CEMail.getMailinhaltausImport(id);
                eMailVerteiler.setBetreff(vorlagedb.getBetreff());
                eMailVerteiler.setNachricht(vorlagedb.getNachricht() + "\n\r\n\r" + kommentar);


                //Senden einer EMail pro Koordinator
                for (int i = 0; i < liste.size(); i++) {
                    eMailVerteiler.setEmpfaenger(liste.get(i).getEMail()); // EMail adresse des i ten Koordinators wird
                    eMailVerteiler.sendeMail();// Sende Mail
                }
            }
        } else {
            System.out.println("Keine ID übergeben.");
        }
    }
}
