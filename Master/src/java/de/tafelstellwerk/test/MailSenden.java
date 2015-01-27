package de.tafelstellwerk.test;

import de.tafelstellwerk.Model.CEMail;

/**
 * Dies soll beispielhaft zeigen, wie Mails zu senden sind.
 *
 * @author Gérard
 * @version 2.0- Konfiguration in die CEMail verlegt + zweite sendeMail(ohne def
 * an dieser Stelle)
 */
public class MailSenden {

    public static void main(String[] args) {

        //Inizialisierung - Wichtig! createInstance nicht vergessen!
        CEMail mm = CEMail.createInstance();

        //Methode 2
        mm.setSender("info@tafel-stellwerk-nshb.de");
        mm.setEmpfaenger("gerard.paschke@online.de");
        mm.setBetreff("Testnachricht");
        mm.setNachricht("Dies ist der Text der E-Mail");
        mm.sendeMail();

        // Methode 1
        /*mm.sendeMail("Tafelstellwerk",
         "gerard.paschke@online.de",
         "Testing123",
         "Sehr geehrte Damen und Herren, \n\n ihre Spende wurde in unserem System verzeichnet. \n\n Wir bedanken uns für ihre Unterstützung!");
         */
    }
}
//Save! Stehen lassesn! Funktionstüchtiger Teil unterhalb
//package de.web.Mail;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//
//
//public class MailSenden {
//
//public static void main( String[] args )
//    {
//
//        ############################
//        ApplicationContext context =
//             new FileSystemXmlApplicationContext("web/Spring-Mail.xml");
//        ############################
//    	CEMail mm = (CEMail) context.getBean("mailMail");
//        mm.sendMail("Tafelstellwerk",
//    		   "gerard.paschke@online.de",
//    		   "Testing123",
//    		   "Sehr geehrte Damen und Herren, \n\n ihre Spende wurde in unserem System verzeichnet. \n\n Wir bedanken uns für ihre Unterstützung!");
//
//    }
//
//}