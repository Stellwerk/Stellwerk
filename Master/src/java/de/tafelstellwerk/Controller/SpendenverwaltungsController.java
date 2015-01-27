package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Forms.CSpendenvorgangsListe;
import de.tafelstellwerk.Forms.CTafelListe;
import de.tafelstellwerk.Forms.SpendenFormular;
import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Model.CSpende;
import de.tafelstellwerk.Model.CSpendenarchiv;
import de.tafelstellwerk.Model.CSpendenvorgang;
import de.tafelstellwerk.Model.CTafel;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CSpendeDAO;
import de.tafelstellwerk.Repository.CSpendenarchivDAO;
import de.tafelstellwerk.Repository.CSpendenvorgangDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
import de.tafelstellwerk.Threads.TMailSendenKoordinator;
import de.tafelstellwerk.Threads.TMailSendenKoordinatorMitKommentar;
import de.tafelstellwerk.Threads.TMailSendenNachVorlage;
import de.tafelstellwerk.Threads.TMailSendenNachVorlageMitZusatz;
import de.tafelstellwerk.Threads.TMailSendenSpender;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller für die Spende (einreichen, bearbeiten, verarbeiten, abschließen
 * etc)
 *
 * @author gpaschke
 */
@Controller
public class SpendenverwaltungsController {

    private CSpende tempspende;
    private List<CTafel> temptafelliste;

//    //Datum dd.MM.yyyy
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.toString();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");        
//            dateFormat.setLenient(false);
//            binder.registerCustomEditor(Date.class, new CustomDateEditor(
//            dateFormat, false));
//    }   
    
//Spende einreichen-------------------------------
    /**
     * Spende anlegen anzeigen. Nur Spender und Tafeln.
     *
     * @param formular
     * @param benutzer
     * @param model
     * @param request
     * @return
     */
    @Secured({"ROLE_SPENDER", "ROLE_TAFEL"})
    @RequestMapping(value = "/spenden", method = RequestMethod.GET)
    public String Spenden(SpendenFormular formular, CBenutzer benutzer, Model model, HttpServletRequest request) {
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + "versucht die Seite /spenden zu öffnen");

        try {
            //Accountinformationen laden
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String eMail = auth.getName();
            benutzer.getBenutzer(eMail);
            benutzer.setPasswort(null);
            //Model bilden
            //Prüfung -> Ist Benutzer angehöriger einer Tafel?
            if (benutzer.getTafel() != null) {
                formular.setTafel(benutzer.getTafel());
                formular.setAdresse(benutzer.getTafel().getAdresse());
                formular.setKontakt(benutzer.getTafel().getKontakt());
                System.out.println("Tafel: " + formular.getTafel());
            } else if (benutzer.getSpender() != null) {
                formular.setAdresse(benutzer.getSpender().getAdresse());
                formular.setKontakt(benutzer.getSpender().getKontakt());
                formular.setSpender(benutzer.getSpender());
                System.out.println("Spender: " + formular.getSpender());
            } else {
                String nachricht = "Ihnen wurde keine Spender- oder Tafel-Identifikationsnummer zugewiesen! <br> Bitte wenden Sie sich an Ihren Koordinator";
                System.out.println(nachricht);
                model.addAttribute("Fehler", nachricht);
                return "fehler";
            }
            model.addAttribute("Spende", formular);
            return "spenden/spenden";


        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /spenden aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());

            return "fehler";
        }
    }

    /**
     * Spende anlegen bearbeiten. Nur für Spender und Tafeln. Es wird auf bestaetigung-spende weitergeleitet um zu testen, ob die Spende richtig in der DB steht
     *
     * @param formular
     * @param result
     * @param model
     * @return
     */
    @Secured({"ROLE_SPENDER", "ROLE_TAFEL"})
    @RequestMapping(value = "/spenden", method = RequestMethod.POST)
    public String SpendenVerarbeiten(@ModelAttribute("Spende") @Valid SpendenFormular formular, BindingResult result, Model model) {
        
        Date now = new Date();
        now.setTime(System.currentTimeMillis());
        //Fehler wenn Eingabewerten nicht den Vorgaben entsprachen oder keine Mengenangabe getroffen wurde
        if (result.hasErrors() || 
                formular.getSpende().getAnzahl() == 0 && formular.getSpende().getGewicht()== 0 && formular.getSpende().getPaletten()== 0 && formular.getSpende().getVolumen()== 0 ||
                //Check angegebenes Datum vor heute
                (formular.getSpende().getMhd() != null && formular.getSpende().getMhd().before(now)) ||
                (formular.getSpende().getRueckmeldefrist()!= null && formular.getSpende().getRueckmeldefrist().before(now)) ||
                (formular.getSpende().getAbholfrist()!= null && formular.getSpende().getAbholfrist().before(now))
                ) {
            //Accountinformationen laden
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String eMail = auth.getName();
            CBenutzer benutzer = new CBenutzer();
            benutzer.getBenutzer(eMail);
            benutzer.setPasswort(null);
            //Model bilden
            //Prüfung -> Ist Benutzer angehöriger einer Tafel?
            if (benutzer.getTafel() != null) {
                formular.setTafel(benutzer.getTafel());
                formular.setAdresse(benutzer.getTafel().getAdresse());
                formular.setKontakt(benutzer.getTafel().getKontakt());
                System.out.println("Tafel: " + formular.getTafel());
            } else if (benutzer.getSpender() != null) {
                formular.setAdresse(benutzer.getSpender().getAdresse());
                formular.setKontakt(benutzer.getSpender().getKontakt());
                formular.setSpender(benutzer.getSpender());
                System.out.println("Spender: " + formular.getSpender());
            } else {
                String nachricht = "Ihnen wurde keine Spender- oder Tafel-Identifikationsnummer zugewiesen! <br> Bitte wenden Sie sich an Ihren Koordinator";
                System.out.println(nachricht);
                model.addAttribute("Fehler", nachricht);
                return "fehler";
            }
            
            //Datum ausgabe
            if((formular.getSpende().getMhd() != null && formular.getSpende().getMhd().before(now)) ||
                (formular.getSpende().getRueckmeldefrist()!= null && formular.getSpende().getRueckmeldefrist().before(now)) ||
                (formular.getSpende().getAbholfrist()!= null && formular.getSpende().getAbholfrist().before(now)))
            {
            model.addAttribute("Ausgabe", "<div class='fehler'>Datumsangaben dürfen nicht in der Vergangenheit liegen!</div>"); 
            }
            
            
            if(formular.getSpende().getAnzahl() == 0 && formular.getSpende().getGewicht()== 0 && formular.getSpende().getPaletten()== 0 && formular.getSpende().getVolumen()== 0){
                model.addAttribute("Ausgabe", "<div class='fehler'>Bitte geben Sie die Menge der Spende an. (mindestens eines der Felder: Anzahl, Gewicht, Paletten, Volumen)</div>");
            }
            model.addAttribute("Spende", formular);
            return "spenden/spenden";
        } else {

            //Im Folgenden wird die Spende entgegengenommen und verarbeitet

            // Setzt das Erstellungsdatum auf das derzeitige Datum und den Status auf erstellt
            formular.getSpende().setErstellungsdatumNow();
            formular.getSpende().setStatus(CSpende.StatusEnum.Eingestellt);

            //Einlesen des Benutzernamens und zuweisung des Benutzers zur Spende
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String eMail = auth.getName();
            CBenutzer benutzer = new CBenutzer();
            benutzer.getBenutzer(eMail);
            formular.getSpende().setBenutzer(benutzer);

            //----------------------------------------------------------------------
            //Einfügen der seperaten Objekte in das Objekt Spende: Adresse, Kontakt
            //Vorher wir überprüft, ob der alte Wert beibehalten werden soll
            //oder ein neuer in der Datenbank angelegt wird

            //Check Adresse
            if (formular.isCheckAdresse() == false) {
                //alte Adresse
                if (benutzer.getTafel() != null) {
                    formular.getSpende().setAdresse(benutzer.getTafel().getAdresse());
                } else if (benutzer.getSpender() != null) {
                    formular.getSpende().setAdresse(benutzer.getSpender().getAdresse());
                }
            } else {
                //neue Adresse + DB Eintrag
                formular.getSpende().setAdresse(formular.getAdresse());
                CAdresseDAO AdressDAO = new CAdresseDAO();
                try{
                    AdressDAO.createAdresse(formular.getAdresse());
                }catch (Exception e){
                    System.out.println("Spende konnte nicht eingestellt werden! Fehler beim eintragen der Spende(Adresse) in die DB");
                    model.addAttribute("Fehler", "Ihre Spende konne nicht in unserer Datenbank erfasst werden. Bitte wenden Sie sich an den Koordinator.");
                    return "fehler";
                } 
            }

            //Check Kontakt
            if (formular.isCheckKontakt() == false) {
                //alter Kontakt
                if (benutzer.getTafel() != null) {
                    formular.getSpende().setKontakt(benutzer.getTafel().getKontakt());
                } else if (benutzer.getSpender() != null) {
                    formular.getSpende().setKontakt(benutzer.getSpender().getKontakt());
                }
            } else {
                //neuer Kontakt + DB Eintrag
                formular.getSpende().setKontakt(formular.getKontakt());
                CKontaktDAO KontaktDAO = new CKontaktDAO();
                try{
                    KontaktDAO.createKontakt(formular.getKontakt());
                } catch (Exception e){
                    System.out.println("Spende konnte nicht eingestellt werden! Fehler beim eintragen der Spende(Kontakt) in die DB");
                    model.addAttribute("Fehler", "Ihre Spende konne nicht in unserer Datenbank erfasst werden. Bitte wenden Sie sich an den Koordinator.");
                    return "fehler";
                }
            }

            //Speichern der Spende in die DB
            CSpendeDAO SpendeDAO = new CSpendeDAO();
            try{
                SpendeDAO.createSpende(formular.getSpende());
            } catch (Exception e){
                System.out.println("Spende konnte nicht eingestellt werden! Fehler beim eintragen der Spende in die DB");
                model.addAttribute("Fehler", "Ihre Spende konne nicht in unserer Datenbank erfasst werden. Bitte wenden Sie sich an den Koordinator.");
                return "fehler";
            }

            //Senden der Mail an Spender
            Runnable mailThreadSpender = new TMailSendenSpender(eMail, 3); // 3 ist die Spendenbestätigung an den Spender
            new Thread(mailThreadSpender).start(); //-Mail senden

            //Senden der Mail an Spender
            Runnable mailThreadKoordinator = new TMailSendenKoordinator(2); // 3 ist die Spendenbenachrichtigung an den Koordinator
            new Thread(mailThreadKoordinator).start(); //-Mail senden

            //##Ausgabe in der Konsole
            //System.out.println(formular.getSpende().toString());

            return "redirect:bestaetigung-spende";
        }
    }

    /**
     * Spende angelegt anzeigen. Nur für Spender und Tafeln. Anzeigen der zuletzt angelegten Spende aus der DB
     *
     * @return
     */
    @Secured({"ROLE_SPENDER", "ROLE_TAFEL"})
    @RequestMapping(value = "/bestaetigung-spende", method = RequestMethod.GET)
    public ModelAndView SpendenBestaetigen() {

        //Erstellung des benötigten Klassen- bzw. Datenbankobjekte
        CSpende spende;
        CSpendeDAO spendeDAO = new CSpendeDAO();
        CAdresse adresse = new CAdresse();
        CKontakt kontakt = new CKontakt();
        SpendenFormular formular = new SpendenFormular();
        //-----------------------------------------

        //Einlesen des Benutzernamens und Suche nach der letzten Spende
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String eMail = auth.getName();
        spende = spendeDAO.getLastSpendeByEMail(eMail);


        //----------------------------------

        //Bildung des Datenmodells zur Ausgabe des View "bestaetigung-spende"
        formular.setAdresse(spende.getAdresse());
        formular.setKontakt(spende.getKontakt());
        formular.setSpende(spende);
        //---------------------------


        //Weitergabe des Modells an den View
        return new ModelAndView("spenden/bestaetigung-spende", "formular", spende);
    }

//Offene Spenden-------------------------------
    /**
     * Offene Spenden anzeigen. Nur für Tafeln.
     *
     * @param model
     * @return
     */
    @Secured("ROLE_TAFEL")
    @RequestMapping(value = "/offene-spenden", method = RequestMethod.GET)
    public String OffeneSpenden(Model model) {
        //Inizialisierung
        String ausgabe = "<div class='erfolg'>Neue Spenden zur Annahme verfügbar!</div>";

        //Accountinformationen laden
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authMail = auth.getName();
        //Liste mit Spenden
        List<CSpendenvorgang> liste = CSpendenvorgangDAO.getMeineSpenden(authMail);
        if (liste.isEmpty()) {
            ausgabe = "<div class='fehler'>Derzeit sind keine neuen Spenden verfügbar.</div>";
        }
        model.addAttribute("Ausgabe", ausgabe);
        model.addAttribute("liste", liste);
        return "spenden/offene-spenden"; // Name der JSP/View
    }

    /**
     * Offene Spenden Details anzeigen. Nur für Tafeln.
     *
     * @param spendenvorgangsID
     * @param spendenvorgang
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_TAFEL")
    @RequestMapping("/offene-spenden/spende-{spendenvorgangsID}")
    public String OffeneSpendeAnzeigen(@PathVariable("spendenvorgangsID") int spendenvorgangsID, CSpendenvorgang spendenvorgang, Model model, HttpServletRequest request) {
        CSpendenvorgangDAO spendenvorgangDAO = new CSpendenvorgangDAO();
        spendenvorgang = spendenvorgangDAO.getSpendenvorgangById(spendenvorgangsID);
        if (spendenvorgang == null) {
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler
        spendenvorgang.getSpende().setKommentar(""); // Sodass die Tafel beim Ablehnen in dieses Feld ihren Kommentar
        model.addAttribute("spendenvorgang", spendenvorgang);

        model.addAttribute("contextpath", request.getContextPath());
        return "spenden/offene-spendendetails"; // Name der JSP/View
    }

    /**
     * Offene Spenden Rückmeldung bearbeiten. Nur für Tafeln.
     *
     * @param spendenvorgangsID
     * @param feedback
     * @param spendenvorgang
     * @param redirect
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_TAFEL")
    @RequestMapping(value = "/offene-spenden/rueckmeldung-{spendenvorgangsID}/{feedback}", method = RequestMethod.POST)
    public String OffeneSpendeRueckmeldung(@PathVariable("spendenvorgangsID") int spendenvorgangsID, @PathVariable("feedback") String feedback, @ModelAttribute("spendenvorgang") CSpendenvorgang spendenvorgang, RedirectAttributes redirect, Model model, HttpServletRequest request) {

        //Übernahme der Werte aus der jsp (Website) & Inizialisierung
        model.addAttribute("contextpath", request.getContextPath());
        double anteil = spendenvorgang.getAnteil();
        String kommentar = "Es wurde kein Grund angegeben.";
        if (spendenvorgang.getSpende() != null) {
            kommentar = spendenvorgang.getSpende().getKommentar();
        }


        //Get Benutzer zur authentifizierung
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authMail = auth.getName();
        CBenutzer benutzer = new CBenutzer();
        benutzer.getBenutzer(authMail);


        //Get Spendenvorgang
        CSpendenvorgangDAO spendenvorgangDAO = new CSpendenvorgangDAO();
        spendenvorgang = spendenvorgangDAO.getSpendenvorgangById(spendenvorgangsID);

        //Kontrolle, ob spendenvorgang vonhanden und Benutzer dem Spendenvorgang zugeordnet ist.
        try {
            if (spendenvorgang == null || benutzer.getTafel() == spendenvorgang.getTafel()) {
                return "fehler";
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Annehmen/Ablehnen der Spende: " + e);
            return "fehler";
        }

        //Bearbeitung des Spendenvorgangs
        Runnable mailThread;
        switch (feedback) {
            case "annehmen": //-------------------------------------------------
                //Wurde keine menge angegeben
                System.out.println("##########" + spendenvorgang.getAnteil());
                if (anteil == 0) {
                    model.addAttribute("Fehler", "Zum Ablehnen bitte Ablehnen klicken. Zum Annehmen bitte eine Menge angeben!");
                    return "fehler";
                }

                spendenvorgang.setAnteil(anteil);
                spendenvorgang.setStatus(CSpendenvorgang.StatusEnum.zugegriffen);
                try {
                    spendenvorgangDAO.createSpendenvorgang(spendenvorgang);
                } catch (Exception e) {
                    redirect.addFlashAttribute("Ausgabe", "Fehler beim Updaten der Datenbank.");
                    System.out.println("Fehler beim updaten des Spendenvorgangs - zugriff Tafel :" + e);
                    return "fehler";
                }
                //Mail senden
                mailThread = new TMailSendenKoordinator(11); // 11- Mail an Koordinator angenommen
                new Thread(mailThread).start();
                redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Die Spende wurde erfolgreich angenommen.</div>");
                break;
            case "ablehnen": //-------------------------------------------------
                spendenvorgang.setAnteil(0);
                spendenvorgang.setStatus(CSpendenvorgang.StatusEnum.zugegriffen);
                try {
                    spendenvorgangDAO.createSpendenvorgang(spendenvorgang);
                } catch (Exception e) {
                    redirect.addFlashAttribute("Ausgabe", "Fehler beim Updaten der Datenbank.");
                    System.out.println("Fehler beim updaten des Spendenvorgangs - zugriff Tafel :" + e);
                    return "fehler";
                }
                //EMail senden
                mailThread = new TMailSendenKoordinatorMitKommentar(12, kommentar); // 12- Mail an Koordinator abgelehnt
                new Thread(mailThread).start();
                redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Die Spende wurde erfolgreich abgelehnt.</div>");
                break;
            default:
                return "fehler";
        }

        return "redirect:/offene-spenden"; // Name der JSP/View
    }

//Meine Spenden -------------------------------
    /**
     * Meine Spenden anzeigen. Nur Spender und Tafeln
     *
     * @param model
     * @return
     */
    @Secured({"ROLE_SPENDER", "ROLE_TAFEL"})
    @RequestMapping(value = "/meine-spenden", method = RequestMethod.GET)
    public String MeineSpenden(Model model) {
        //Accountinformationen laden
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authMail = auth.getName();
        //Liste mit Meinen Spenden
        CSpendeDAO spendeDAO = new CSpendeDAO();
        List<CSpende> liste = spendeDAO.getMeineSpendenByEMail(authMail);
        model.addAttribute("liste", liste);
        return "spenden/meine-spenden"; // Name der JSP/View
    }

    //Detailseite meine Spenden anzeigen
    @Secured({"ROLE_SPENDER", "ROLE_TAFEL"})
    @RequestMapping("/meine-spenden/spende-{spendeID}")
    public String MeineSpendeAnzeigen(@PathVariable("spendeID") int spendeID, CSpende spende, Model model) {

        //Folgendes wird geladen, um zu überprüfen, ob der Benutzer auch die Spende einsehen darf- andernfalls können benutzer die ID in der URL manuell manipulieren
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authMail = auth.getName();

        spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null || !spende.getBenutzer().getEMail().equals(authMail)) {
            model.addAttribute("Fehler", "Es wurde keine passende Spende gefunden.");
            return "fehler";
        }
        model.addAttribute("spende", spende);
        return "spenden/meine-spendendetails"; // Name der JSP/View
    }

//Eingegangene Spenden -------------------------------
//ab hier nur Koordinator
    /**
     * Liste eingegangener Spenden Seite anzeigen. Nur für den Koordinator.
     *
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping("/eingegangene-spenden")
    public String EingegangeneSpendenAnzeigen(Model model) {
        List<CSpende> liste = CSpendeDAO.getSpendenByStatus(CSpende.StatusEnum.Eingestellt);
        if (liste.isEmpty()) {
            model.addAttribute("Ausgabe", "<div class='erfolg'>Es sind derzeit keine neuen Spenden verfügbar.</div>");
        } else {
            model.addAttribute("Ausgabe", "<div class='fehler'>Es sind derzeit neue Spenden verfügbar! Bitte verteilen Sie diese unter \"Weitere Details\"</div>");
        }
        model.addAttribute("spendenliste", liste);
        return "spenden/eingegangene-spenden"; // Name der JSP/View
    }

    /**
     * Detailseite eingegangener Spenden Seite anzeigen
     *
     * @param spendeID
     * @param spende
     * @param request
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/eingegangene-spenden/spende-{spendeID}", method = RequestMethod.GET)
    public String EingegangeneSpendeAnzeigen(@PathVariable("spendeID") int spendeID, @ModelAttribute("spende") CSpende spende, HttpServletRequest request, Model model) {
        spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler
        model.addAttribute("spende", spende);
        //Links übergeben
        model.addAttribute("linkverteilen", request.getContextPath() + "/eingegangene-spenden/spende-" + spendeID + "/verteilen");
        model.addAttribute("linkbearbeiten", request.getContextPath() + "/eingegangene-spenden/spende-" + spendeID + "/bearbeiten");
        return "spenden/spendendetails";
    }

    /**
     * Anzeigen der zu bearbeitenden eingegangenen Spende
     *
     * @param spendeID
     * @param spende
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/eingegangene-spenden/spende-{spendeID}/bearbeiten", method = RequestMethod.GET)
    public String EingegangeneSpendeBearbeiten(@PathVariable("spendeID") int spendeID, @ModelAttribute("spende") CSpende spende, Model model, HttpServletRequest request) {
        spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler
        tempspende = spende;
        model.addAttribute("spende", spende);
        return "spenden/spende-bearbeiten";
    }

    /**
     * Verarbeitung der bearbeiteten eingegangenen Spende
     *
     * @param spende
     * @param result
     * @param redirect
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/eingegangene-spenden/spende-{spendeID}/bearbeiten", method = RequestMethod.POST)
    public String EingegangeneSpendeBearbeitenVerarbeiten(@ModelAttribute("spende") CSpende spende, BindingResult result, RedirectAttributes redirect, Model model, HttpServletRequest request) {
        //Übertragung der Wert, die nicht über das Formular übergeben werden
        spende.setErstellungsdatum(tempspende.getErstellungsdatum());
        spende.setBenutzer(tempspende.getBenutzer());
        spende.setStatus(tempspende.getStatus());
        spende.getKontakt().setKontaktID(tempspende.getKontakt().getKontaktID());
        spende.getAdresse().setAdressID(tempspende.getAdresse().getAdressID());

        CSpendeDAO spendeDAO = new CSpendeDAO();
        String ausgabe = "<div class='erfolg'>Die Spende wurde erfolgreich bearbeitet. Der Spender wird nun benachrichtigt...</div>";

        try {
            //Spende in der DB aktualisieren
            spendeDAO.createSpende(spende);
            //Mail an den Spender senden
            Runnable mailThread = new TMailSendenNachVorlage(spende.getBenutzer().getEMail(), 5); //5 zur Änderungsbestätigung
            new Thread(mailThread).start();
        } catch (Exception e) {
            ausgabe = "<div class='fehler'>Bei der Bearbeitung der Spende ist ein Fehler aufgetreten.</div>";

        }

        redirect.addFlashAttribute("rAusgabe", ausgabe);
        return "redirect:../";
    }

    /**
     * Verteilung der eingegangenen Spenden anzeigen
     *
     * @param spendeID
     * @param spende
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/eingegangene-spenden/spende-{spendeID}/verteilen", method = RequestMethod.GET)
    public String EingegangeneSpendeVerteilen(@PathVariable("spendeID") int spendeID, CSpende spende, Model model, HttpServletRequest request) {
        spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler
        List<CTafel> templist = CTafelDAO.getTafelListe(); //Liste mit allen Tafeln in ein neues Objekt der Klasse Tafelauswahl(extends CTafel + boolean auswahl)

        CTafelListe tafelliste = new CTafelListe();
        tafelliste.setTafelliste(templist);
        temptafelliste = templist;

        if (tafelliste.getTafelliste().isEmpty()) {
            System.out.println("Keine Tafeln in der Datenbank");
            model.addAttribute("Ausgabe", "<div class='fehler'>Es sind keine Tafeln in der Datenbank hinterlegt.</div>");
        }
        model.addAttribute("spende", spende);
        model.addAttribute("TafelListe", tafelliste);
        return "spenden/verteilen-tafelauswahl"; // Name der JSP/View
    }

    /**
     * Verteilung der eingegangenen Spenden bearbeiten
     *
     * @param spendeID
     * @param tafelliste
     * @param redirect
     * @param result
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/eingegangene-spenden/spende-{spendeID}/verteilen", method = RequestMethod.POST)
    public String EingegangeneSpendeVerteilenVerarbeiten(@PathVariable("spendeID") int spendeID, @ModelAttribute("TafelListe") CTafelListe tafelliste, RedirectAttributes redirect, BindingResult result) {
        //Mögliche Fehlerbehebung
        //get spendeID
        CSpende spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            redirect.addFlashAttribute("rAusgabe", "<div class='fehler'>Es wurde keine Spende dieser ID gefunden.</div>");
            return "redirect:/eingegangene-spenden/";
        }

        //Neue tafelliste, mit den Tafeln, die ausgewählt wurden
        CTafelListe tafelauswahl = new CTafelListe();
        for (int i = 0; i < temptafelliste.size(); i++) {
            if (tafelliste.getTafelliste().get(i).getAusgewaehlt()) { //wurde die Tafel ausgewählt?
                //temptafelliste.get(i).setAusgewaehlt(true);          //dann schreibe bei der befüllten Tafel Ausgewählt auf True - nicht mehr von bedeutung, da die Tafelauswahl nur ausgewählte Tafeln beinhaltet
                tafelauswahl.getTafelliste().add(temptafelliste.get(i));// und setze diese Tafel in die neue Liste tafelauswahl
                System.out.println("Tafel wurde ausgewählt: " + temptafelliste.get(i).toString());
            }
        }
        //Mögliche Fehlerbehebung
        //wenn keine Tafeln ausgewählt wurden, gehe zurück auf die vorherige Seite
        if (tafelauswahl.getTafelliste().isEmpty()) {
            redirect.addFlashAttribute("rAusgabe", "<div class='fehler'>Keine Tafel ausgewählt. Bitte wählen Sie Tafeln aus, an die Verteilt werden kann!</div>");
            return "redirect:/eingegangene-spenden/";
        }

        //Spendenvorgänge erstellen
        CSpendenvorgangDAO spendenvorgangDAO = new CSpendenvorgangDAO();
        Date now = new Date();
        now.setTime(System.currentTimeMillis());
        Runnable mailThread;
        for (int i = 0; i < tafelauswahl.getTafelliste().size(); i++) {
            //Spendenvorgang erstellen und in DB speichern
            CSpendenvorgang spendenvorgang = new CSpendenvorgang(0, 0, CSpendenvorgang.StatusEnum.ignoriert, now, spende, tafelauswahl.getTafelliste().get(i));
            try {
                spendenvorgangDAO.createSpendenvorgang(spendenvorgang);
            } catch (Exception e) {
                System.out.println("Fehler beim Schreiben des Spendenvorgangs in die DB" + e);
            }
            System.out.println(spendenvorgang.toString());

            //Senden der EMails an die Tafeln
            mailThread = new TMailSendenNachVorlage(tafelauswahl.getTafelliste().get(i).getKontakt().geteMail(), 6);
            new Thread(mailThread).start(); //-Mail senden
        }

        //Spende Status ändern
        CSpendeDAO spendeDAO = new CSpendeDAO();
        spende.setStatus(CSpende.StatusEnum.Freigegeben);
        try {
            spendeDAO.createSpende(spende);
        } catch (Exception e) {
            System.out.println("Fehler bei der Statusänderung der Spende - DB" + e);
        }

        redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Der eingegangenen Spende wurden erfolgreich Tafeln zugewiesen.</div>");
        return "redirect:/eingegangene-spenden"; // Name der JSP/View
    }

//Spenden Abschließen
    /**
     * Alle Spendenvorgänge anzeigen
     *
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/spenden/spendenvorgaenge", method = RequestMethod.GET)
    public String SpendeInBearbeitung(Model model) {
        List<CSpendenvorgang> liste = CSpendenvorgangDAO.getSpendenvorgangListe();
        if (liste.isEmpty()) {
            model.addAttribute("Ausgabe", "<div class='fehler'>Es sind derzeit keine Spendenvorgänge verfügbar.</div>");
        }
        model.addAttribute("spendenvorgangsliste", liste);
        return "spenden/spendenvorgaenge"; // Name der JSP/View
    }

    /**
     * Spende abschließen Details anzeigen
     *
     * @param spendeID
     * @param spende
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/spenden/spendenvorgang-{spendeID}/details", method = RequestMethod.GET)
    public String SpendeAbschluss(@PathVariable("spendeID") int spendeID, @ModelAttribute("spende") CSpende spende, Model model, HttpServletRequest request) {
        //Get Spende + Fehler abfangen
        spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            System.out.println("SpendeAbschluss - Spende im System nicht gefunden");
            model.addAttribute("Fehler", "Diese Spende wurde nicht gefunden!");
            return "fehler";
        }
        model.addAttribute("spende", spende);
        model.addAttribute("linkverteilen", request.getContextPath() + "/spenden/spendenvorgang-" + spendeID + "/verteilen");
        model.addAttribute("linkbearbeiten", request.getContextPath() + "/spenden/spendenvorgang-" + spendeID + "/bearbeiten");
        return "spenden/spendendetails"; // Name der JSP/View
    }

    /**
     * Spende Abschließen Tafelauswahl anzeigen
     *
     * @param spendeID
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "spenden/spendenvorgang-{spendeID}/verteilen", method = RequestMethod.GET)
    public String SpendeAbschliessenTafelauswahl(@PathVariable("spendeID") int spendeID, Model model, HttpServletRequest request) {
        CSpende spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler
        List<CTafel> tafelliste = CTafelDAO.getTafelListe(); //Liste mit allen Tafeln in eine neue Liste

        List<CTafel> tafelauswahlliste = CTafelDAO.getTafelListeAusgewaehlt(spendeID);  //Ausgewählte Tafeln

        //Vorauswahl- Welche Tafeln stehen in den Spendenvorgängen
        //Kurze Liste mit Auszuwählenden Tafeln
        for (int i = 0; i < tafelauswahlliste.size(); i++) {
            int iid = tafelauswahlliste.get(i).getId();
            //alle Tafeln - ist Tafel vorhanden?
            for (int j = 0; j < tafelliste.size(); j++) {
                if (tafelliste.get(j).getId() == iid) {
                    //Wenn Tafel vorhanden, dann Ausgewählt = true
                    tafelliste.get(j).setAusgewaehlt(Boolean.TRUE);
                }
            }
        }

        CTafelListe ctl = new CTafelListe(); //Dieses Objekt soll die liste beinhalten
        ctl.setTafelliste(tafelliste);         //Übergabe der Liste an das Object(Liste)

        if (ctl.getTafelliste().isEmpty()) {
            System.out.println("Keine Tafeln in der Datenbank");
            model.addAttribute("Ausgabe", "<div class='fehler'>Es sind keine Tafeln in der Datenbank hinterlegt.</div>");
        }

//        for (int i = 0; i < ctl.getTafelliste().size(); i++) {
//            System.out.println(ctl.getTafelliste().get(i).getTafelname());
//        }

        //JSP Objekte
        model.addAttribute("spende", spende);
        model.addAttribute("TafelListe", ctl);

        return "spenden/verteilen-tafelauswahl"; // Name der JSP/View
    }

    /**
     * Spende Abschließen Tafelauswahl verarbeiten Mengenauswahl anzeigen.
     *
     * @param spendeID
     * @param tafelliste
     * @param result
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "spenden/spendenvorgang-{spendeID}/verteilen", method = RequestMethod.POST)
    public String SpendeAbschliessenTafelauswahlVerarbeiten(@ModelAttribute("spendeID") int spendeID, @ModelAttribute("TafelListe") CTafelListe tafelliste, BindingResult result, Model model, HttpServletRequest request) {
        //Get Spende anhand der spendeID + Fehler auffangen
        CSpende spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            return "fehler";
        }

        //Spendenvorgangsliste erstellen - in diesem Objekt befindet sich die Liste, aus dem Grund, da keine Listen an eine JSP übergeben werden können
        CSpendenvorgangsListe svlistejsp = new CSpendenvorgangsListe(); //objekt für jsp
        List<CSpendenvorgang> svliste = new ArrayList<>(); //Hilfsliste die an svlistejsp übergeben wird



        //Nur ausgewählte tafeln in die neue Liste schreiben
        for (int i = 0; i < tafelliste.getTafelliste().size(); i++) {

            //Wenn die Tafel ausgewählt wurde...
            if (tafelliste.getTafelliste().get(i).getAusgewaehlt()) {

                //Neuer Spendenvorgang leer - für for schleife
                CSpendenvorgang spendenvorgang = new CSpendenvorgang(0, 0, spende, null);
                CSpendenvorgang tspendenvorgang = null;

                try { //Muss nicht existieren, daher try
                    tspendenvorgang = CSpendenvorgangDAO.getSpendenvorgangBySpendeAndTafel( //Spendenvorgang anhand Spende und Tafel
                            spendeID, tafelliste.getTafelliste().get(i).getId()//anhand Spende und Tafel (tafel ist die aus der tafelliste, i-tes elemnt)
                            );
                } catch (Exception e) {
                }

                if (tspendenvorgang != null) {
                    spendenvorgang = tspendenvorgang;
                    spendenvorgang.setSpende(spende);

                } else {

                    //Hier den Spendenvorgang anpassen, um je ausgewählter Tafel einen Spendenvorgang zu erstellen, der Tafel,Spende,Anteil enthält
                    spendenvorgang.setTafel( //Tafel ist die aus der tafelliste (ausgewählt)
                            CTafelDAO.getTafelById( //Tafel anhand der ID aus der DB laden
                            tafelliste.getTafelliste().get(i).getId()));//ID steht in der tafelliste an i-ter stelle
                }


                svliste.add(spendenvorgang);
                spendenvorgang = null;
            }
        }

        svlistejsp.setSpendenvorgang(svliste);

        //Ausgabe zum testen
//        System.out.println("Tafelauswahl übergabe an Mengenauswahl");
//        for (int i = 0; i < svlistejsp.getSpendenvorgang().size(); i++) {
//            System.out.println(svlistejsp.getSpendenvorgang().get(i).toString());
//        }

        model.addAttribute("spende", spende);
        model.addAttribute("spendenvorgangsliste", svlistejsp);
        model.addAttribute("linkpost", request.getContextPath() + "/spenden/spendenvorgang-" + spendeID + "/verteilen/absenden");

        return "spenden/verteilen-mengenauswahl"; // Name der JSP/View
    }

    /**
     * Spende Abschließen Mengenauswahl verarbeiten
     *
     * @param spendeID
     * @param svliste
     * @param result
     * @param redirect
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "spenden/spendenvorgang-{spendeID}/verteilen/absenden", method = RequestMethod.POST)
    public String SpendeAbschliessenMengenauswahlVerarbeiten(@ModelAttribute("spendeID") int spendeID, @ModelAttribute("spendenvorgangsliste") CSpendenvorgangsListe svliste, BindingResult result, RedirectAttributes redirect, Model model) {

        //Get Spende anhand der spendeID + Fehler auffangen
        CSpende spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            return "fehler";
        }

        //Inizialisierung
        //String bevorzugteTafeln = "Diese Spende wurde an die folgenden Tafeln verteilt:\n"; // würde in bevorzugte_Tafeln die Verteilung schreiben
        Runnable mailThread;
        String mailzusatztafel;
        CSpendeDAO spendeDAO = new CSpendeDAO();

        Date now = new Date();
        now.setTime(System.currentTimeMillis());


        try {
            //Löschen der Spendenvorgänge
            List<CSpendenvorgang> svlistedb = CSpendenvorgangDAO.getSpendenvorgangListeBySpende(spendeID); //Spendenvorgänge der Spende in der DB
            for (int i = 0; i < svlistedb.size(); i++) {
                try {
                    CSpendenvorgangDAO.deleteSpendenvorgang(svlistedb.get(i)); //Löscht alle Spenden
                    System.out.println("Spendenvorgang gelöscht: " + svliste.getSpendenvorgang().get(i).getSpendenvorgangsID());
                } catch (Exception e) {
                    System.out.println("Spendenvorgang konnte nicht gelöscht werden- nicht in DB");
                }

            }
//---------------------- Schleife -----------------------
            //Für jeden Spendenvorgang in der Spendenvorgangsliste
            for (int i = 0; i < svliste.getSpendenvorgang().size(); i++) {
                //Spendenarchiv erstellen - muss jedes Mal neu erstellt werden, damit alle objekte in die DB geschrieben werden
                CSpendenarchiv spendenarchiv = new CSpendenarchiv(0, spende, null, 0, 0, 0, 0);

                //Spende nicht vollständig und die eigentliche Spende - Gewicht, Anzahl,... beinhalten die ausgewählten werte, der verteilung
                CSpende tspende = svliste.getSpendenvorgang().get(i).getSpende();
                CTafel ttafel = CTafelDAO.getTafelById(
                        svliste.getSpendenvorgang().get(i).getTafel().getId());

                //----------------------
                //Speicherung der auswahl in der Spende (Bevorzugte_Tafeln)
                //Tafel: 4 Stück, 5 KG, 1 Paletten \n

//            bevorzugteTafeln = bevorzugteTafeln + ttafel.getTafelname() +": "+ tspende.getAnzahl() + " Stück";
//
//            if(tspende.getGewicht() != 0){
//            bevorzugteTafeln = bevorzugteTafeln + ", " + tspende.getGewicht()+ " KG";}
//
//            if(tspende.getPaletten()!= 0){
//            bevorzugteTafeln = bevorzugteTafeln + ", " + tspende.getPaletten()+ " Paletten";}
//
//            if(tspende.getVolumen()!= 0){
//            bevorzugteTafeln = bevorzugteTafeln + ", " + tspende.getVolumen()+ " L";}
//
//            bevorzugteTafeln = bevorzugteTafeln + "\n";

                //----------------------
                //Mail senden Tafeln

                mailzusatztafel = "Spende: " + spende.getProduktbezeichnung()
                        + "\n\nMenge: " + tspende.getAnzahl() + " Stück\n"
                        + "Gewicht: " + tspende.getGewicht() + " KG\n"
                        + "Paletten: " + tspende.getPaletten() + " Paletten\n"
                        + "Volumen: " + tspende.getVolumen() + " L";

                //Wenn der Benutzer(Spender) ein Spender ist
                if (spende.getBenutzer().getSpender() != null) {
                    mailzusatztafel = mailzusatztafel + "\n\nInfos zum Spender: " + spende.getBenutzer().getSpender().getFirmenname() + "\n"
                            + "Strasse: " + spende.getAdresse().getStrasse() + "\n"
                            + "Ort: " + spende.getAdresse().getPlz() + " " + spende.getAdresse().getOrt() + "\n"
                            + "Beschreibung: " + spende.getAdresse().getBeschreibung() + "\n"
                            + "Abholzeiten: " + spende.getAbholzeiten(); //Spenderinfo...
                }
                //Wenn der Benutzer(Spender) eine Tafel ist
                if (spende.getBenutzer().getTafel() != null) {
                    mailzusatztafel = mailzusatztafel + "\n\nInfos zur Tafel: " + spende.getBenutzer().getTafel().getTafelname() + "\n"
                            + "Strasse: " + spende.getAdresse().getStrasse() + "\n"
                            + "Ort: " + spende.getAdresse().getPlz() + " " + spende.getAdresse().getOrt() + "\n"
                            + "Beschreibung: " + spende.getAdresse().getBeschreibung() + "\n"
                            + "Abholzeiten: " + spende.getAbholzeiten(); //Spenderinfo...
                }

//                System.out.println("Sende Mail an Tafel: " + ttafel.getKontakt().geteMail() + " " + mailzusatztafel);
                try {
                    mailThread = new TMailSendenNachVorlageMitZusatz(ttafel.getKontakt().geteMail(), 13, mailzusatztafel); //Email, Vorlage, zusatz - an Tafel
                    new Thread(mailThread).start();
                } catch (Exception e) {
                    System.out.println("Senden der Mail an die Tafel Fehlgeschlagen.");
                }

                //Spendenhistorie anpassen
                spendenarchiv.setTafel(ttafel);
                spendenarchiv.setAnzahl(tspende.getAnzahl());
                spendenarchiv.setGewicht(tspende.getGewicht());
                spendenarchiv.setPaletten(tspende.getPaletten());
                spendenarchiv.setVolumen(tspende.getVolumen());

                //Spendenhistorie in die DB
                try {
                    CSpendenarchivDAO.createSpendenarchiv(spendenarchiv);
                    System.out.println("Spendenhistorie wurde in die DB eingetragen");
                } catch (Exception e) {
                    System.out.println("Spendenhistorie konnte nicht in die DB eingetragen werden.");
                }

            }
//---------------------- Schleifenende -----------------------

//        System.out.println("Bevorzugte Tafeln: " + bevorzugteTafeln);
//        System.out.println("Sende Mail an Spender: " + spende.getKontakt().geteMail());

            //Inhalt der Mail an den Spender
            String mailzusatzspender = "Produktbezeichnung: " + spende.getProduktbezeichnung() + "\n"
                    + "Eingegangen am: " + spende.getErstellungsdatum();
            //Mail an Spender
            mailThread = new TMailSendenNachVorlageMitZusatz(spende.getBenutzer().getEMail(), 14, mailzusatzspender); //Email, Vorlage, zusatz - an Spender
            new Thread(mailThread).start();

            //Spendenstatus und Bevorzugte Tafeln ändernrce
            spende.setStatus(CSpende.StatusEnum.Verteilt); //Spende auf verteilt
            //spende.setBevorzugte_Tafeln(bevorzugteTafeln);
            spende.setAbschlussdatum(now);  //Abschlussdatum auf 'heute'

            //DB eintrag der Spende ändern
            try {
                spendeDAO.createSpende(spende);
                System.out.println("Spende in der DB wurde angepasst");
            } catch (Exception e) {
            }


        } catch (Exception e) {
            System.out.println("Fehler bei der Spendenverteilung" + e);
            model.addAttribute("Fehler", "<div class='fehler'>Spende konnte nicht Verteilt werden. (ggf. wurde einer ausgewählten Tafel keine Menge zugeteilt)</div>");
            return "fehler";
        }

        redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Die Spende wurde erfolgreich verteilt. \nSpender und ausgewählte Tafeln werden benachrichtigt...</div>");
        return "redirect:../../spendenvorgaenge"; // Name der JSP/View
    }

    /**
     * Spende abschließen - bearbeiten anzeigen
     *
     * @param spendeID
     * @param spende
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "spenden/spendenvorgang-{spendeID}/bearbeiten", method = RequestMethod.GET)
    public String SpendeAbschliessenBearbeiten(@PathVariable("spendeID") int spendeID, @ModelAttribute("spende") CSpende spende, Model model, HttpServletRequest request) {
        spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler
        tempspende = spende;

        model.addAttribute("spende", spende);
        return "spenden/spende-bearbeiten";
    }

    /**
     * Spende abschließen - bearbeiten verarbeiten
     *
     * @param spendeID
     * @param spende
     * @param result
     * @param redirect
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "spenden/spendenvorgang-{spendeID}/bearbeiten", method = RequestMethod.POST)
    public String SpendeAbschliessenBearbeitenVerarbeiten(@PathVariable("spendeID") int spendeID, @ModelAttribute("spende") CSpende spende, BindingResult result, RedirectAttributes redirect, Model model) {
        //Übertragung der Wert, die nicht über das Formular übergeben werden
        spende.setErstellungsdatum(tempspende.getErstellungsdatum());
        spende.setBenutzer(tempspende.getBenutzer());
        spende.setStatus(tempspende.getStatus());
        spende.getKontakt().setKontaktID(tempspende.getKontakt().getKontaktID());
        spende.getAdresse().setAdressID(tempspende.getAdresse().getAdressID());

        CSpendeDAO spendeDAO = new CSpendeDAO();
        String ausgabe = "<div class='erfolg'>Die Spende wurde erfolgreich bearbeitet. Der Spender wird benachrichtigt...</div>";
        try {
            //Spende in der DB aktualisieren
            spendeDAO.createSpende(spende);
            //Mail an den Spender senden
            Runnable mailThread = new TMailSendenNachVorlage(spende.getBenutzer().getEMail(), 5); //5 zur Änderungsbestätigung
            new Thread(mailThread).start();
        } catch (Exception e) {
            ausgabe = "<div class='fehler'>Bei der Bearbeitung der Spende ist ein Fehler aufgetreten.</div>";
        }

        redirect.addFlashAttribute("Ausgabe", ausgabe);
        return "redirect:/spenden/spendenvorgaenge";
    }

    /**
     * Spende löschen verarbeiten
     *
     * @param spendeID
     * @param request
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "spende/{spendeID}/loeschen", method = RequestMethod.GET)
    public String SpendeLoeschen(@PathVariable("spendeID") int spendeID, HttpServletRequest request, RedirectAttributes redirect, Model model) {
        CSpende spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            model.addAttribute("Fehler", "Spende wurde nicht gefunden.");
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler

        List<CSpendenvorgang> svliste = CSpendenvorgangDAO.getSpendenvorgangListeBySpende(spende.getSpendeID());


        //Spende und dessen Spendenvorgänge löschen
        try {
            if (svliste != null) {
                for (int i = 0; i < svliste.size(); i++) {
                    CSpendenvorgangDAO.deleteSpendenvorgang(svliste.get(i));
                    System.out.println("Spendenvorgang " + svliste.get(i).getSpendenvorgangsID() + " gelöscht.");
                }
            }

            CSpendeDAO.deleteSpende(spende);
            System.out.println("Spende " + spende.getSpendeID() + " gelöscht");

            Runnable mailThread = new TMailSendenNachVorlage(spende.getBenutzer().getEMail(), 7); //7 Löschungsbestätigung
            new Thread(mailThread).start();

        } catch (Exception e) {
            System.out.println("Fehler beim löschen der Spende / Spendenvorgänge...");
            model.addAttribute("Fehler", "Löschen der Spende / Spendenvoränge fehlgeschlagen.");
            return "fehler";
        }

        redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Die ausgewählte Spende wurde gelöscht.</div>");
        return "redirect:../../";
    }

    //------------ Abgeschlossene Spenden
    /**
     * Abgeschlossene Spenden anzeigen
     *
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/spenden/abgeschlossen", method = RequestMethod.GET)
    public String SpendeAbgeschlossen(Model model) {
        List<CSpende> liste = CSpendeDAO.getSpendenByStatus(CSpende.StatusEnum.Verteilt);
        if (liste.isEmpty()) {
            model.addAttribute("Ausgabe", "<div class='fehler'>Es sind derzeit keine Spenden im System.</div>");
        }

        //Diagramm------------------------
        //Der folgende Teil ermöglicht die Auswertung - Spendenabschluss pro Monat
        //Datum
        Date now = new Date();
        now.setTime(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        Calendar calspende = Calendar.getInstance();
        cal.setTime(now);
        

        //[1, 3, 5, 2, 1, 6, 7, 4, 3, 1, 9, 8] - soll ausgegeben werden

        //Inizialisierung - diagramm wird an js übergeben
        int spendenanzahl;
        String diagramm = "[";

        //Schleife, die alle Monate durchgeht
        for (int i = 0; i < 12; i++) {
            spendenanzahl = 0;
            //Schleife, die alle abgeschlossenen Spenden durchgeht
            for (int j = 0; j < liste.size(); j++) {
                //überprüfe, ob die eine Spende im Monat liegt
                calspende.setTime(liste.get(j).getAbschlussdatum());
                int monat = calspende.get(Calendar.MONTH);
                int jahr = calspende.get(Calendar.YEAR);
                if (monat == i && jahr == cal.get(Calendar.YEAR)) {
                    spendenanzahl++; //erhöhe die Spendenanzahl
                }
            }
            diagramm = diagramm + spendenanzahl;
            if (i < 12) {
                diagramm = diagramm + ", ";
            }
        }
        diagramm = diagramm + "]";

        model.addAttribute("diagramm", diagramm);
        model.addAttribute("spendenanzahl", liste.size());
        model.addAttribute("spendenliste", liste);
        return "spenden/abgeschlossene-spenden"; // Name der JSP/View
    }

    /**
     * Abgeschlossene Spendendetails anzeigen
     *
     * @param spendeID
     * @param model
     * @return
     */
    @Secured("ROLE_KOORD")
    @RequestMapping(value = "/spenden/abgeschlossen/{spendeID}-details", method = RequestMethod.GET)
    public String SpendeAbgeschlossenDetails(@PathVariable("spendeID") int spendeID, Model model) {
        CSpende spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null) {
            model.addAttribute("Fehler", "Spende wurde nicht gefunden.");
            return "fehler";
        } // Wenn die PathVariable nicht gefunden wurde -> Fehler

        List<CSpendenarchiv> archivliste = CSpendenarchivDAO.getArchivListeBySpende(spendeID);
        System.out.println(archivliste.size());

        model.addAttribute("archivliste", archivliste);
        model.addAttribute("spende", spende);
        return "spenden/abgeschlossene-spendendetails"; // Name der JSP/View
    }

    /**
     * Abgeschlossene Spenden für Tafeln anzeigen
     *
     * @param model
     * @return
     */
    @Secured("ROLE_TAFEL")
    @RequestMapping(value = "/verteilte-spenden", method = RequestMethod.GET)
    public String AbgeschlosseneSpendenTafel(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CBenutzer benutzer = new CBenutzer();
        int tafelID;
        try {
            benutzer.getBenutzer(auth.getName());
            tafelID = benutzer.getTafel().getId();
        } catch (Exception e) {
            model.addAttribute("Fehler", "Ihrem Benutzer wurde keine Tafel hinterlegt! Bitte wenden Sie sich an Ihren Administrator und fordern eine Tafelzuweisung an.");
            return "fehler";
        }

        List<CSpendenarchiv> archiv = CSpendenarchivDAO.getArchivListeByTafel(tafelID);
        if (archiv.isEmpty()) {
            model.addAttribute("Ausgabe", "<div class='fehler'>Es sind derzeit keine Spenden im System.</div>");
        }

        model.addAttribute("spendenanzahl", archiv.size());
        model.addAttribute("archivliste", archiv);
        return "spenden/verteilte-spenden"; // Name der JSP/View
    }

    /**
     * Abgeschlossene Spendendetails für Tafeln anzeigen
     *
     * @param archivID
     * @param model
     * @return
     */
    @Secured("ROLE_TAFEL")
    @RequestMapping(value = "/verteilte-spenden/{archivID}-details", method = RequestMethod.GET)
    public String AbgeschlosseneSpendenTafelDetails(@PathVariable("archivID") int archivID, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CBenutzer benutzer = new CBenutzer();
        benutzer.getBenutzer(auth.getName());


        CSpendenarchiv archiv = CSpendenarchivDAO.getSpendenarchivById(archivID);
        if (archiv == null || archiv.getTafel().getId() != benutzer.getTafel().getId()) {
            model.addAttribute("Fehler", "Es wurde keine passende Spende gefunden oder Ihr Benutzerkonte verfügt über keine Tafel.");
            return "fehler";
        }

        model.addAttribute("archiv", archiv);
        return "spenden/verteilte-spendendetails"; // Name der JSP/View
    }
}