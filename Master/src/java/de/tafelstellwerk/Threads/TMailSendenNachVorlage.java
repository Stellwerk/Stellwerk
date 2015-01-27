/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Threads;

import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;

/**
 *
 * @author gpaschke
 */
public class TMailSendenNachVorlage implements Runnable {

    private String eMail = null;
    private int vorlage;

    public TMailSendenNachVorlage(String eMail, int vorlage) {
        this.eMail = eMail;
        this.vorlage = vorlage;
    }

    @Override
    public void run() {
        if (eMail != null && vorlage != 0) {
            //Mail erstellen und aus DB Vorlage senden
            CEMail eMailVerteiler = CEMail.createInstance();
            eMailVerteiler.setEmpfaenger(eMail);
            eMailVerteiler.setSender("info@tafel-stellwerk-nshb.de");
            eMailVerteiler.sendeMailausImport(vorlage);

        } else {
            System.out.println("EMail konnte nicht gesendet werden! Keine Werte übergeben.");
        }

    }
}
