package de.tafelstellwerk.Threads;

import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Model.CEMailVorlage;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;

/**
 *
 * @author gpaschke
 */
public class TMailSendenNachVorlageMitZusatz implements Runnable {

    private String eMail = null;
    private String zusatz = null;
    private int vorlage;

    /**
     * 
     * @param eMail
     * @param vorlage
     * @param zusatz 
     */
    public TMailSendenNachVorlageMitZusatz(String eMail, int vorlage, String zusatz) {
        this.eMail = eMail;
        this.vorlage = vorlage;
        this.zusatz = zusatz;
    }

    /**
     * Override der run() Funktion
     */
    @Override
    public void run() {
        if (eMail != null && vorlage != 0 && zusatz != null) {
            //Mail erstellen und aus DB Vorlage senden
            CEMail eMailVerteiler = CEMail.createInstance();
            eMailVerteiler.setEmpfaenger(eMail);
            eMailVerteiler.setSender("info@tafel-stellwerk-nshb.de");
            CEMailVorlage vorlagedb = CEMail.getMailinhaltausImport(vorlage);
            eMailVerteiler.setBetreff(vorlagedb.getBetreff());
            eMailVerteiler.setNachricht(vorlagedb.getNachricht() + "\n\r\n\r" + zusatz);
            eMailVerteiler.sendeMail();

        } else {
            System.out.println("EMail konnte nicht gesendet werden! Keine Werte übergeben.");
        }

    }
}
