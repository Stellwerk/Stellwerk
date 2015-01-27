package de.tafelstellwerk.Threads;

import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;

/**
 *
 * @author gpaschke
 * @param id der Mailvorlage
 */
public class TMailCronJob implements Runnable {

    private int ID = 0;
    private String empfaenger = "";
    private String betreff = "";

    public TMailCronJob(String empfaenger, String betreff, int id) {
        this.ID = id;
        this.empfaenger = empfaenger;
        this.betreff = betreff;
    }

    /**
     * Sendet eine Spendenbenachrichtigungsmail an jeden Koordinator
     */
    @Override
    public void run() {
        if (ID != 0 && !empfaenger.equals("") && !betreff.equals("")) {
            CEMail eMailVerteiler = CEMail.createInstance();
            eMailVerteiler.setSender("info@tafel-stellwerk-nshb.de");
            eMailVerteiler.setBetreff(betreff);
            eMailVerteiler.setEmpfaenger(empfaenger);
            eMailVerteiler.sendeMailausImport(ID);

        }

    }
}
