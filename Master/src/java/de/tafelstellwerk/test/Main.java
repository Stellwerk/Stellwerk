package de.tafelstellwerk.test;

//import Repository.CPersonDAO;
//import Model.CPerson;
import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Model.CSpende;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CSpendeDAO;
import java.util.Date;

/**
 *
 * @author Gerard
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Programmstart...");
        /*
        CAdresse Adresse = new CAdresse(0, "Kaiserstrasse 10c", 49809, "Halle I/II");
        CKontakt Kontakt = new CKontakt(0, "Hans","Peters", "hans@peters.com", "0591554433", "");
        CSpende Spende = new CSpende();
        
        
        Double[] menge;
        menge = new Double[]{1.44,35000.0};
        Date time = new Date();
        time.setTime(System.currentTimeMillis());
        
        Spende.setBeschreibung("Lasange");
        Spende.setBeschreibung("vegetarisch");
        Spende.setAlkohol(false);
        Spende.setLieferung(false);
        Spende.setErstellungsdatum(time);
        Spende.setKontakt(Kontakt);
        Spende.setAdresse(Adresse);
        
        //CSpende Spende = new CSpende(0, "Lasange", "vegetarisch",menge,false,false,false,time, "keine", "Lingener Tafel", "", time, time, time, time, enum, enum, enum, enum, Adresse, Kontakt, Spender);
        
        
        
        CSpendeDAO SpendeDAO = new CSpendeDAO();
        CAdresseDAO AdresseDAO = new CAdresseDAO();
        CKontaktDAO KontaktDAO = new CKontaktDAO();
         
        //Datenbankeintrag geht noch nicht.. erst Adresse transmitten! dann Kontakt transmitten
        AdresseDAO.createAdresse(Adresse);
        KontaktDAO.createKontakt(Kontakt);
        SpendeDAO.createSpende(Spende);
        
        Spende = (CSpende) SpendeDAO.getSpendeById(5);
        System.out.println("Spende ausgewählt:");
        System.out.println(Spende.getProduktbezeichnung());
        Kontakt = (CKontakt) Spende.getKontakt();
        System.out.format("Name: %s - Vorname: %s" , Kontakt.getNachname(), Kontakt.getVorname());
        
        System.out.println("");
  
        SpendeDAO.deleteSpende(Spende);
        System.out.println("Spende gelöscht!");
        */
        
//        CAdresse Adresse = new CAdresse(0, "Kaiserstrasse 10c", 49809, "Lingen", "Halle I/II");
//        CKontakt Kontakt = new CKontakt(0, "Hans","Peters", "hans@peters.com", "0591554433", "");
//        //CSpende Spende = new CSpende(0, "Pizza Salami", "Pizza einzeln verpackt. Salami unregelmäßig verteilt.", Gesamtmenge, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "Mo-Fr 08.00 - 17.30", "keine", "Lingen, Rheine", , null, null, null, null, CSpende.ArtEnum.Food, CSpende.KuehlungEnum.keine, CSpende.TauschpaletteEnum.EURO, CSpende.StatusEnum.Abschluss, Adresse, Kontakt, null)
//        
//        CSpende Spende = new CSpende();
//        
//        Double[] menge;
//        menge = new Double[]{1.44,35000.0};
//        Date time = new Date();
//        time.setTime(System.currentTimeMillis());
//        
//        Spende.setBeschreibung("Lasange");
//        Spende.setBeschreibung("vegetarisch");
//        Spende.setAlkohol(false);
//        Spende.setLieferung(false);
//        Spende.setErstellungsdatum(time);
//        Spende.setKontakt(Kontakt);
//        Spende.setAdresse(Adresse);
//        
//        CSpendeDAO SpendeDAO = new CSpendeDAO();
//        CAdresseDAO AdresseDAO = new CAdresseDAO();
//        CKontaktDAO KontaktDAO = new CKontaktDAO();
//         
//        //Datenbankeintrag geht noch nicht.. erst Adresse transmitten! dann Kontakt transmitten
//        AdresseDAO.createAdresse(Adresse);
//        KontaktDAO.createKontakt(Kontakt);
//        SpendeDAO.createSpende(Spende);
     
//        String EMail = "gerard.paschke@online.de";
//        CBenutzer benutzer = new CBenutzer(0, EMail, EMail, EMail, EMail, CBenutzer.RolleEnum.Tafel, Boolean.TRUE, null, null);
//        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
//        
//        benutzerDAO.createBenutzer(benutzer);
        
    }
}
