package de.tafelstellwerk.test;

import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CAllgemeines;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMailVorlage;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Model.CSpende;
import de.tafelstellwerk.Model.CSpendenvorgang;
import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Model.CTafel;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CEMailVorlageDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CSpendeDAO;
import de.tafelstellwerk.Repository.CSpendenvorgangDAO;
import de.tafelstellwerk.Repository.CSpenderDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
import java.util.Date;

/**
 * Klasse zum Befüllen der Datenbank mit Testdaten
 *
 * @author gpaschke
 */
public class AddDBResetTest {

    public static void main(String[] args) {

        //Adressen: Tafeln
        CAdresseDAO AdresseDAO = new CAdresseDAO();                             //Objekt für DB Verbindung
        CAdresse Adresse = new CAdresse(0, "Unterstr. 9", "28832", "Achim", ""); //Objekt lokal erstellt
        AdresseDAO.createAdresse(Adresse);                                      //und in die DB einfügen

        
        //Adressen: Spender
        CAdresse Adresse1 = new CAdresse(0, "Zum Attersee 2", "49076", "Osnabrück", "");
        AdresseDAO.createAdresse(Adresse1);

        
        //Kontakte: Tafeln
        CKontaktDAO KontaktDAO = new CKontaktDAO();
        CKontakt Kontakt = new CKontakt(0, "Vorname", "Nachname", "test@mail.de", "0123456789", "");
        KontaktDAO.createKontakt(Kontakt);
        
        
        //Kontakte: Spender
        CKontakt Kontakt1 = new CKontakt(0, "Vorname", "Nachname", "test@mail.de", "0123456789", "0123456789");
        KontaktDAO.createKontakt(Kontakt1);


        //Tafeln
        CTafelDAO TafelDAO = new CTafelDAO();
        CTafel Tafel = new CTafel(0, "Tafel eV", "2 VW Crafter Kühlwagen", "Bürozeiten: Mo 9.30 bis 13.00 Uhr, Di bis Fr 9.30 bis 15.30 Uhr, sonst AB", null, CTafel.StatusEnum.grün, Adresse, Kontakt);
        TafelDAO.createTafel(Tafel);
                
        //Spender
        CSpenderDAO SpenderDAO = new CSpenderDAO();
        CSpender Spender = new CSpender(0, "Spender eV", "Handschuhe", "Mo. - Fr.: 6.00 - 20.00 Uhr", "", CSpender.StatusEnum.grün, Adresse1, Kontakt1);
        SpenderDAO.createSpender(Spender);

        
        //Benutzer: Tafeln
        CBenutzerDAO BenutzerDAO = new CBenutzerDAO();
        CBenutzer Benutzer = new CBenutzer(0, "Vorname", "Nachname", "test@tafel.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel);
        Benutzer.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer);
        
        //Benutzer: Spender
        CBenutzer Benutzer1 = new CBenutzer(0, "Vorname", "Nachname", "test@spender.de", "passwort", CBenutzer.RolleEnum.Spender, true, Spender, null);
        Benutzer1.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer1);
        
        //Benutzer: Koordinatoren
        CBenutzer Benutzer2 = new CBenutzer(0, "Vorname", "Nachname", "test@koordinator.de", "passwort", CBenutzer.RolleEnum.Koordinator, true, null, null);
        Benutzer2.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer2);
        
        //Benutzer: Administratoren
        CBenutzer Benutzer3 = new CBenutzer(0, "Vorname", "Nachname", "test@admin.de", "passwort", CBenutzer.RolleEnum.Administrator, true, null, null);
        Benutzer3.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer3);


        //Spenden
        CSpendeDAO SpendeDAO = new CSpendeDAO();
        Date time = new Date();
        time.setTime(System.currentTimeMillis());
        CSpende Spende = new CSpende(0, "Nusskuchen", "Sahniger Nusskuchen in zartem Schokoladenmantel", time, time, time, 1234, 100, 2, 0, CSpende.TauschpaletteEnum.keine, CSpende.ArtEnum.Food, CSpende.KuehlungEnum.kühl, time, true, "Lingen", true, "keiner", Benutzer, Kontakt, Adresse, "Fr", "Handschuhe", CSpende.StatusEnum.Eingestellt);
        SpendeDAO.createSpende(Spende);

        //Spendenvorgang
        CSpendenvorgangDAO spendenvorgangDAO = new CSpendenvorgangDAO();
        CSpendenvorgang spendenvorgang = new CSpendenvorgang(0, 2.55, CSpendenvorgang.StatusEnum.ignoriert, null, Spende, Tafel);
        spendenvorgang.setZuweisungszeitpunktNow();
        spendenvorgangDAO.createSpendenvorgang(spendenvorgang);


        //Allgemeines
        CAllgemeines allgemeines = new CAllgemeines(0, "Herzlich willkommen...", "...auf den Seiten des Tafelstellwerks vom \"Landesverband der Tafeln in Niedersachsen und Bremen e. V.\" \n"
                + "Das Tafelstellwerk ist ein Projekt, dass es Spendern ermöglicht, komfortabel Spenden an die Tafeln in Niedersachsen und Bremen abzugeben. Diese werden anschließend unter den Tafeln im Einzugsbereich verteilt.\n"
                + "\n"
                + "Zugegriffen werden kann über die Benutzer: test@admin.de, test@koordinator.de, test@spender.de, test@tafel.de mit dem Passwort: passwort");
        CAllgemeinesDAO.createAllgemeines(allgemeines);
        allgemeines = new CAllgemeines(0, "Allgemeine Geschäftsbedingungen", "Hier könnten ihre AGBs stehen.");
        CAllgemeinesDAO.createAllgemeines(allgemeines);
        allgemeines = new CAllgemeines(0, "Impressum", "Hier erscheint auf der späteren Seite das Impressum.\n"
                + "\nLandesverband der Tafeln in\n"
                + "Niedersachsen und Bremen e.V.\n"
                + "Karl-Heinz Krüger (1. Vors.)\n"
                + "c/o Wittmunder Tafel e. V.\n"
                + "info@tafel-niedersachsen.de\n"
                + "<a href='http://www.tafel-niedersachsen.de'>www.tafel-niedersachsen.de</a>\n"
                + "<h3>Haftungsausschluss</h3>\n"
                + "Trotz sorgfältiger inhaltlicher Kontrolle übernehmen wir keine Haftung für die Inhalte externer Links. Für den Inhalt der verlinkten Seiten sind ausschließlich deren Betreiber verantwortlich.");
        CAllgemeinesDAO.createAllgemeines(allgemeines);
        allgemeines = new CAllgemeines(0, "EMAIL", "testumgebung@tafel-stellwerk-nshb.de");
        CAllgemeinesDAO.createAllgemeines(allgemeines);
        allgemeines = new CAllgemeines(0, "Datenbankreset", "/zugriff-verweigert/datenbank/reset/aeXq38p5");
        CAllgemeinesDAO.createAllgemeines(allgemeines);



        //EMailVorlagen
        CEMailVorlageDAO EMailVorlageDAO = new CEMailVorlageDAO();
        CEMailVorlage eMailVorlage;

//        addAkteur, addSpendeKo, addSpendeSp, changeSpendeKo, changeSpendeSp,
//            addSpendeTa, delSpendeSp, noreact24, noreact48, noreact48Ko, accSpendeSp,accSpendeTa

        eMailVorlage = new CEMailVorlage(0, "Willkommen beim Tafelstellwerk", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Herzlich willkommen auf der Plattform Tafelstellwerk.\r\nAb sofort können Sie sich mit Ihrer E-Mail Adresse und dem zugewiesenen Passwort anmelden."
                + "\r\nHinweis: Es ist aus Sicherheitsgründen ratsam, das Passwort nach der Erstanmeldung in ein beliebiges, neues Passwort zu ändern!"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spendeneingang", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine neue Spende wurde aufgenommen! \r\nBitte besuchen Sie zeitnah das Tafelstellwerk-Portal, um diese zu überprüfen und für die Tafeln freizugeben."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spendeneingang", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich im Portal Tafelstellwerk registriert. Vielen Dank!"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Änderungsbestätigung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Die Spende wurde erfolgreich geändert."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Änderungsbestätigung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich geändert."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - neue Spende", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Wir freuen uns, Ihnen mitteilen zu dürfen, dass eine neue Spende bereitsteht.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen und Ihr Feedback zu geben."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spende gelöscht", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich gelöscht."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Erinnerung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende steht seit 1nem Tag bereit.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen und Ihr Feedback zu geben.\r\nSollten Sie kein Interesse an einem Teil dieser Spende haben, möchten wir Sie bitten, uns dies mitzuteilen."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Erinnerung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende steht seit 2 Tagen bereit.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen und Ihr Feedback zu geben.\r\nSollten Sie kein Interesse an einem Teil dieser Spende haben, möchten wir Sie bitten, uns dies mitzuteilen."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Erinnerung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende steht seit 2 Tagen für diese Tafel bereit.\r\n"
                + "\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen.\r\n"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Angenommene Spende", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine freigegebene Spende wurde von einer Tafel angenommen.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen, letzte Änderungen vorzunehmen und den Verteilungsprozess zu in Gang zu setzen."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Abgelehnte Spende", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine freigegebene Spende wurde von einer Tafel abgelehnt.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen, Informationen zu erhalten und die Spende erneut freizugeben."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spende erfolgreich verteilt!", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende konnte erfolgreich verteilt werden! Nähere Informationen finden Sie unten in dieser Nachricht!\r\n\r\n"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spende erfolgreich verteilt!", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich verteilt! Nähere Informationen finden Sie unten in dieser Nachricht!\r\n"
                + "Die Tafel wird die Ware in den nächsten Tagen abholen. Sollten Sie die Ware liefern,\r\n"
                + "kontaktieren Sie bitte einen Koordinator für weitere Instruktionen (z.B. Lieferadressen).\r\n\r\n"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);

    }
}