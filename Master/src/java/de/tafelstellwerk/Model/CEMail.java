/*
 * Keine Klasse, die in der Datenbank steht!
 * Dient zum Senden von Mails
 *
 * Inizialisierung mit Instanz!
 * CEMail x = CEMail.createInstance();
 *
 * sender, empfaenger, betreff, nachricht
 * Objekt.sendeMail();
 * Objekt.sendeMail(sender, empfaenger, betreff, nachricht);
 * Objekt.sendeMailausImport(EnumAnlass)
 *
 */
package de.tafelstellwerk.Model;

import de.tafelstellwerk.Repository.CEMailVorlageDAO;
import javax.validation.constraints.Size;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Diese Klasse dient zum Senden von Mails EMail mit allen wichtigen Parametern
 * inc methoden zum Senden Diese Klasse steht nicht der Datenbank! Sie muss mit
 * .createInstance(); erstellt werden! -------------- CEMail x =
 * CEMail.createInstance();
 *
 * @author Simon
 * @author Gérard
 * @version 2.0- Konfiguration in die CEMail verlegt + zweite sendeMail(ohne def
 * an dieser Stelle)
 *
 * @param sender
 * @param empfaenger
 * @param betreff
 * @param nachricht
 */
public class CEMail {

    private MailSender mailSender;
    private static ApplicationContext context;
    private String sender = null;
    private String empfaenger = null;
    @Size(min = 3, max = 200)
    private String betreff = null;
    @Size(min = 3, max = 10000)
    private String nachricht = null;

    //Konstruktor, der die Konfiguration aus der xml Datei ausliest
    public static CEMail createInstance() {
        if (context == null) {
            context = new FileSystemXmlApplicationContext("classpath:Spring-Mail.xml");
        }
        return (CEMail) context.getBean("mailMail");
    }

//Methoden
    /**
     * M1- Direktes übergeben der Werte und senden der Mail
     *
     * @param from
     * @param to
     * @param subject
     * @param msg
     */
    public void sendeMail(String from, String to, String subject, String msg) {
        if (sender != null && empfaenger != null && betreff != null && nachricht != null) {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(msg);
            try {
                 mailSender.send(message);   //Senden der EMail
                System.out.println("Mail gesendet:" + subject);
            } catch (Exception e) {
                System.out.println("Fehler: Mail senden fehlgeschlagen!!:" + subject + " " + to);
            }
           
        } else {   //Prüfung ob alle Daten angegeben wurden
            System.out.println("M1: Sender, Empfänger, Betreff oder Nachricht nicht definiert! Keine Mail");
        }
    }

    /**
     * M2- Vorherige definition der Werte benötigt und senden der Mail, sonst
     * Exception
     */
    public void sendeMail() {
        if (sender != null && empfaenger != null && betreff != null && nachricht != null) {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(sender);
            message.setTo(empfaenger);
            message.setSubject(betreff);
            message.setText(nachricht);
            try {
                mailSender.send(message);   //Senden der EMail
                System.out.println("Mail gesendet:" + betreff);
            } catch (Exception e) {
                System.out.println("Fehler: Mail senden fehlgeschlagen:" + betreff +" " + empfaenger); 
            }
            
        } else {   //Prüfung ob alle Daten angegeben wurden
            System.out.println("M2: Sender, Empfänger, Betreff oder Nachricht nicht definiert! Keine Mail");
        }
    }

    /**
     * M3- Senden der Mail über EMailvorlage aus der DB; SENDER und EMPFÄNGER
     * vorher definieren!!! Importiert die EMailvorlagen aus der DB in das
     * Objekt- nach ID
     *
     * @param id int
     */
    public void sendeMailausImport(int ID) {
        CEMailVorlageDAO VorlageDAO = new CEMailVorlageDAO();
        CEMailVorlage Vorlage = (CEMailVorlage) VorlageDAO.getEMailVorlageById(ID);
        //CEMailVorlage Vorlage = (CEMailVorlage) VorlageDAO.getEMailVorlageByBetreff(betreff);

        if (sender != null && empfaenger != null && Vorlage.getBetreff() != null && Vorlage.getNachricht() != null) {
            this.betreff = Vorlage.getBetreff();
            //this.nachricht = nachricht.format(Vorlage.getNachricht(),"Test");
            this.nachricht = Vorlage.getNachricht();


            SimpleMailMessage message = new SimpleMailMessage(); //kein Aufruf der Methode this.sendeMail(), um zweite Kontrolle (if) zu vermeiden- performance
            message.setFrom(sender);
            message.setTo(empfaenger);
            message.setSubject(betreff);
            message.setText(nachricht);
            try {
                mailSender.send(message);   //Senden der EMail
                System.out.println("Mail gesendet:" + betreff);
            } catch (Exception e) {
                System.out.println("Fehler: Mail senden fehlgeschlagen:" + betreff + " "+ empfaenger );
            }
            
        } else {   //Prüfung ob Eintrag existierte und Werte ungleich null
            System.out.println("M3: Sender oder Empfänger oder Betreff oder Nachricht nicht definiert! Keine Mail! Evtl. kein Eintrag in der DB, Enum nicht vorhanden oder Sender und Empfänger zuvor nicht inizialisiert.");
        }
    }

    /**
     * M4- Senden der Mail über EMailvorlage aus der DB; SENDER, EMPFÄNGER und
     * BETREFF vorher definieren!!! Importiert die EMailvorlagen aus der DB in
     * das Objekt- nach ID
     *
     * @param id int
     * @param betreff String
     */
    public void sendeMailausImportohneBetreff(int id, String betreff) {
        CEMailVorlageDAO VorlageDAO = new CEMailVorlageDAO();
        CEMailVorlage Vorlage = (CEMailVorlage) VorlageDAO.getEMailVorlageById(id);
        //CEMailVorlage Vorlage = (CEMailVorlage) VorlageDAO.getEMailVorlageByBetreff(betreff);

        if (betreff != null && sender != null && empfaenger != null && Vorlage.getNachricht() != null) {
            this.nachricht = Vorlage.getNachricht();

            SimpleMailMessage message = new SimpleMailMessage(); //kein Aufruf der Methode this.sendeMail(), um zweite Kontrolle (if) zu vermeiden- performance
            message.setFrom(sender);
            message.setTo(empfaenger);
            message.setSubject(betreff);
            message.setText(nachricht);
            try {
                mailSender.send(message);   //Senden der EMail
                System.out.println("Mail gesendet:" + betreff);
            } catch (Exception e) {
                System.out.println("Fehler: Mail senden fehlgeschlagen:" + betreff + " " + empfaenger);
            }
            
        } else {   //Prüfung ob Eintrag existierte und Werte ungleich null
            System.out.println("M3: Sender oder Empfänger oder Betreff oder Nachricht nicht definiert! Keine Mail! Evtl. kein Eintrag in der DB, Enum nicht vorhanden oder Sender und Empfänger zuvor nicht inizialisiert.");
        }
    }

    public static CEMailVorlage getMailinhaltausImport(int id) {
        CEMailVorlage vorlage = new CEMailVorlage();
        CEMailVorlageDAO vorlageDAO = new CEMailVorlageDAO();
        try {
            vorlage = vorlageDAO.getEMailVorlageById(id);
        } catch (Exception e) {
            System.out.println("Fehler: Laden der Mailvorlagen fehlgeschlagen. vorlage = null");
        }
        return vorlage;
    }

    //Getter
    public String getSender() {
        return sender;
    }

    public String getEmpfaenger() {
        return empfaenger;
    }

    public String getBetreff() {
        return betreff;
    }

    public String getNachricht() {
        return nachricht;
    }

    //Setter
    public void setSender(String Sender) {
        this.sender = Sender;
    }

    public void setEmpfaenger(String Empfaenger) {
        this.empfaenger = Empfaenger;
    }

    public void setBetreff(String Betreff) {
        this.betreff = Betreff;
    }

    public void setNachricht(String Nachricht) {
        this.nachricht = Nachricht;
    }

    // Keinen Schimmer, ob man das hier braucht
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public MailSender getMailSender() {
        return mailSender;
    }
}

//String.format("Line:%2$d. Value:%1$s. Result: Hello %1$s at line %2$d", aString, aInt );