/*
 * Die übliche Klasse um irgendwelche neuen Funktionen zu testen...
 */
package de.tafelstellwerk.test;

import de.tafelstellwerk.Model.CAllgemeines;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Model.CSpendenarchiv;
import de.tafelstellwerk.Model.CSpendenvorgang;
import de.tafelstellwerk.Model.CTafel;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;
import de.tafelstellwerk.Repository.CSpendenarchivDAO;
import de.tafelstellwerk.Repository.CSpendenvorgangDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
import de.tafelstellwerk.Threads.TMailSendenNachVorlageMitZusatz;
import java.util.List;

/**
 *
 * @author gpaschke
 */
public class test {

    public static void main(String[] args) {
//        CAllgemeinesDAO allgemeinesDAO = new CAllgemeinesDAO();
//        CAllgemeines allgemeines = allgemeinesDAO.getAllgemeinesById(4);
//        allgemeines.getInhalt();
        
        Runnable mailThread = new TMailSendenNachVorlageMitZusatz("gerard.paschke@online.de", 1, "Das hier soll drin Stehen in der ersten Mail"); 
        //new Thread(mailThread).start(); // Hier ist der Fehler
        
        //Inizialisierung - Wichtig! createInstance nicht vergessen!
        CEMail mm = CEMail.createInstance();

        //Methode 2
        mm.setSender("info@tafel-stellwerk-nshb.de");
        mm.setEmpfaenger("gerard.paschke@online.de");
        mm.setBetreff("Testnachricht4heimpc");
        mm.setNachricht("Dies ist der Text der E-Mail");
        //mm.sendeMail();
        System.out.println("Hier ist es zu ende......");
        
//        String EMail = "patrick.niemann@gmail.com"; //
//        CSpendeDAO SpendeDAO = new CSpendeDAO();
//        CSpende Spende = SpendeDAO.getLastSpendeByEMail(EMail);
//        System.out.println(Spende.toString());
//        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
//        CBenutzer benutzer = benutzerDAO.getBenutzerByEMail("gerard.paschke@online.de");
//
//        System.out.println(benutzer.toString());
        //System.out.println(benutzer.getTafel().toString());
        //System.out.println(benutzer.getSpender().toString());
        //System.out.println(benutzer.getSpender().getAdresse().toString());
        //System.out.println(benutzer.getTafel().getAdresse().toString());
//        String EMail = "gerard.paschke@online.de";
//
//        CEMail EMailVerteiler = CEMail.createInstance();
//        EMailVerteiler.setSender("tafelstellwerk@hs-osnabrueck.de");
//        EMailVerteiler.setEmpfaenger(EMail);
//        //EMailVerteiler.sendeMailausImport(CEMailVorlage.AnlassEnum.addSpendeSp);
//
//
//        //Test alle Benutzer aus Tablle auslesen...
//        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
//        CBenutzer benutzer = new CBenutzer();
//        List<CBenutzer> list;
//        list = benutzerDAO.getBenutzerListeIVNER();
//        System.out.println(list.size());
//        System.out.println(list.get(0).toString());
//        System.out.println(list.get(1).toString());
//        System.out.println(list.get(2).toString());
//
//        CBenutzer benutzer = new CBenutzer(0, "hans", "peter", "at", "abc", CBenutzer.RolleEnum.Tafel, Boolean.TRUE, null, null);
//        System.out.println(benutzer.toString());
//        benutzer.hashPasswort();
//        System.out.println(benutzer.toString());
//        CSpendenvorgangDAO spendenvorgangDAO = new CSpendenvorgangDAO();
//        List <CSpendenvorgang> liste = spendenvorgangDAO.getMeineSpenden("lau@lau.de");
//        System.out.println(liste.size());
//
//        CBenutzer benutzer = new CBenutzer();
//        benutzer.getBenutzer(1);
//        System.out.println(benutzer.toString());
//        //CBenutzerDAO get Koordinator
//        List<CBenutzer> liste = CBenutzerDAO.getBenutzerListeByRolle(CBenutzer.RolleEnum.Koordinator);
//        if (liste.isEmpty()){
//            System.out.println("Fehler: Es wurde kein Koordinator in der Datenbank gefunden! Kein Senden einer Spendenbenachrichtigungsmail möglich...");
//        } else {
//            for (int i = 0; i < liste.size(); i++) {
//            System.out.println(liste.get(i).toString());
//            }
//        }
        //  List<Tafelauswahl> templiste = CTafelDAO.getTafelAuswahlListe(); //Liste mit allen Tafeln in ein neues Objekt der Klasse Tafelauswahl(extends CTafel + boolean auswahl)
//        Tafelauswahl temp = (Tafelauswahl) templiste.get(0);
//        System.out.println(temp);
//        CTafel tafel = new  CTafel();
//        System.out.println(tafel);
//        CSpendenvorgangDAO spendenvorgangDAO = new CSpendenvorgangDAO();
//        List<CSpendenvorgang> liste = CSpendenvorgangDAO.getSpendenvorgangListe();
//        System.out.println(liste.size());
//        List<CSpendenvorgang> liste = CSpendenvorgangDAO.getTafelListeAusgewaehlt(1);
//          for (int i = 0; i < liste.size(); i++) {
//          liste.get(i).toString();
//        }
//
//          System.out.println(liste.size());
//
//          List<CTafel> liste = CTafelDAO.getTafelListeAusgewaehlt(2);
//          for (int i = 0; i < liste.size(); i++) {
//              System.out.println(liste.get(i).toString());
//        }
//
//          System.out.println(liste.size());
//        CSpendenvorgang spendenvorgang = CSpendenvorgangDAO.getSpendenvorgangBySpendeAndTafel(2, 2);
//        System.out.println(spendenvorgang.toString());
//
//        List<CSpendenarchiv> archivliste = CSpendenarchivDAO.getArchivListeBySpende(11);
//
//        System.out.println("Größe: "+archivliste.size());
//
//        for (int i = 0; i < archivliste.size(); i++) {
//            System.out.println(archivliste.get(i).toString());
//        }
    }
}
