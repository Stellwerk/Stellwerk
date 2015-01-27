package de.tafelstellwerk.test;

import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Model.CEMailVorlage;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CEMailVorlageDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
//import de.web.Repository.CEMailVorlageDAO;

/**
 *
 * @author gpaschke
 */
public class MailVorlageSenden {

    public static void main(String[] args) {

        /* Vorlagen in die Datenbank einlesen:
         CEMailVorlage Vorlage;
         CEMailVorlageDAO VorlageDAO = new CEMailVorlageDAO();

         Vorlage = new CEMailVorlage(0, CEMailVorlage.AnlassEnum.addAkteur, "Willkommen im Tafelstellwerk", "Hallo!\n Sie wurden in das System aufgenommen.");
         VorlageDAO.createEMailVorlage(Vorlage);
         Vorlage = new CEMailVorlage(0, CEMailVorlage.AnlassEnum.noreact24, "Ihnen wurde eine Spende bereitgestellt!", "Hallo!\n Ihnen wurde eine Spende bereitgestellt! Wir bitten um eine Reaktion ihrerseits.");
         VorlageDAO.createEMailVorlage(Vorlage);
         */




//        CAdresseDAO adresseDAO = new CAdresseDAO();
//        CAdresse adresse = adresseDAO.getAdresseById(1);
//



//        String EMail = "gerard.paschke@online.de";
//        CBenutzer Benutzer = new CBenutzer();
//        long start = System.currentTimeMillis();
//        Benutzer.getBenutzer(EMail);
//        long ende = System.currentTimeMillis();
//        System.out.println(ende- start);
//        System.out.println(Benutzer.toString());
//
//        CKontaktDAO cKontaktDAO = new CKontaktDAO();
//        CKontakt cKontakt = cKontaktDAO.getKontaktById(1);
//



        CEMail mail = CEMail.createInstance();
        mail.setSender("info@tafel-stellwerk-nshb.de");
        mail.setEmpfaenger("gerard.paschke@online.de");
        mail.sendeMailausImport(1);
        System.out.println(mail.getSender());
        System.out.println(mail.getBetreff()); //- dient zum überprüfen
        System.out.println(mail.getNachricht());


    }
}
