package de.tafelstellwerk.test;

import de.tafelstellwerk.Model.CEMailVorlage;
import de.tafelstellwerk.Repository.CEMailVorlageDAO;

/**
 *
 * @author gpaschke
 */
public class AddDBMail {

    public static void main(String[] args) {

        //EMailVorlagen
        CEMailVorlageDAO EMailVorlageDAO = new CEMailVorlageDAO();
        CEMailVorlage eMailVorlage;

//        addAkteur, addSpendeKo, addSpendeSp, changeSpendeKo, changeSpendeSp,
//        addSpendeTa, delSpendeSp, noreact24, noreact48, noreact48Ko, accSpendeKo, decSpendeKo
//        add = hinzufügen, change = ändern, comp = reklamieren, del = löschen, noreact = keine Reaktion, acc = annehmen, dec = ablehnen

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
