/*
 * Benutzerverwaltung für Admin
 */
package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Forms.CSpenderTafelListe;
import de.tafelstellwerk.Model.CBenutzer;
import static de.tafelstellwerk.Model.CBenutzer.RolleEnum.Spender;
import static de.tafelstellwerk.Model.CBenutzer.RolleEnum.Tafel;
import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Model.CTafel;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CSpenderDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
import de.tafelstellwerk.Threads.TMailSendenNachVorlageMitZusatz;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Dieser Controller erstellt die meisten Webseiten, auf die der Admin zugreifen
 * kann. Alle Seiten sind nur als Admin erreichbar.
 *
 * @author Gérard Paschke
 * @version 1.0
 */
@Controller
@Secured("ROLE_ADMIN")
public class BenutzerverwaltungAdmin {
    //Globale Variablen

    private CBenutzer tempbenutzer;

    /**
     * Benutzerverwaltung anzeigen - Alle Benutzer auflisten
     *
     * @param model
     * @return benutzerverwaltung/benutzerverwaltung
     */
    @RequestMapping(value = "/benutzerverwaltung", method = RequestMethod.GET)
    public String Benutzerverwaltung(Model model) {
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
        List<CBenutzer> liste = benutzerDAO.getBenutzerListeIVNER();
        model.addAttribute("benutzeranzahl", liste.size());
        model.addAttribute("liste", liste);
        return "benutzerverwaltung/benutzerverwaltung"; // Name der JSP/View
    }

//Hinzufügen-----------------
    /**
     * Benutzer hinzufügen anzeigen
     *
     * @param benutzer
     * @param model
     * @param request
     * @return benutzerverwaltung/hinzufuegen-benutzer
     */
    @RequestMapping(value = "/benutzerverwaltung/benutzer/hinzufuegen", method = RequestMethod.GET)
    public String BenutzerverwaltungNeuerBenutzer(@ModelAttribute("benutzer") CBenutzer benutzer, Model model, HttpServletRequest request) {
        return "benutzerverwaltung/hinzufuegen-benutzer";
    }

    /**
     * Benutzer hinzufügen verarbeiten
     *
     * @param benutzer
     * @param result
     * @param redirect
     * @param request
     * @param model
     * @return redirect:../
     */
    @RequestMapping(value = "/benutzerverwaltung/benutzer/hinzufuegen", method = RequestMethod.POST)
    public String BenutzerverwaltungNeuerBenutzerVerarbeiten(@ModelAttribute("benutzer") @Valid CBenutzer benutzer, BindingResult result, RedirectAttributes redirect, HttpServletRequest request, Model model) {

        if (result.hasErrors()) {
            return "benutzerverwaltung/hinzufuegen-benutzer";
        }
        String bpasswort = benutzer.getPasswort();
        benutzer.setAktiviert(Boolean.TRUE);

        benutzer.hashPasswort();
        System.out.println("Benutzer: " + benutzer.toString());
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
        //Test ob EMail bereits in der DB ist
        try {
            benutzerDAO.createBenutzer(benutzer);
        } catch (Exception e) {
            benutzer.setPasswort(null);
            String Fehler = "EMail";
            System.out.println("Benutzer konnte nicht erstellt werden! ggf ist die EMail bereits vorhanden. Kein gravierender Fehler \n\n" + e);
            model.addAttribute("Ausgabe", "<div class='fehler'>Fehler beim hinzufügen des Benutzers. (ggf. ist diese E-Mail bereits vorhanden)</div>");
            model.addAttribute("Fehler", Fehler);
            return "benutzerverwaltung/hinzufuegen-benutzer";
        }
        String mailzusatz = "Hier Ihre Logindaten:\n\r"
                + "E-Mail Adresse: " + benutzer.getEMail()
                + "\n\rPasswort: " + bpasswort
                + "\n\r\n\rBitte notieren Sie sich Ihre Logindaten und bewahren diese an einem sicheren Ort auf. Das Passwort wird verschlüsselt in unserer Datenbank hinterlegt und kann Ihnen nicht erneut mitgeteilt werden."
                + "\n\r-Ein Zettel am Monitor oder unter der Tastatur ist kein sicherer Ort!-";
        Runnable mailThread = new TMailSendenNachVorlageMitZusatz(benutzer.getEMail(), 1, mailzusatz); // 12- Mail an Koordinator abgelehnt
        new Thread(mailThread).start();

        String Ausgabe = "<div class='erfolg'>Benutzer wurde erfolgreich angelegt.</div>";
        redirect.addFlashAttribute("Ausgabe", Ausgabe);
        return "redirect:../";
    }

    /**
     * Tafel hinzufügen anzeigen
     *
     * @param tafel
     * @param model
     * @param request
     * @return benutzerverwaltung/hinzufuegen-tafel
     */
    @RequestMapping(value = "/tafelverwaltung/tafel/hinzufuegen", method = RequestMethod.GET)
    public String BenutzerverwaltungNeueTafel(@ModelAttribute("tafel") CTafel tafel, Model model, HttpServletRequest request) {
        model.addAttribute("tafel", tafel);
        return "benutzerverwaltung/hinzufuegen-tafel";
    }

    /**
     * Tafel hinzufügen verarbeiten
     *
     * @param tafel
     * @param result
     * @param redirect
     * @param model
     * @return redirect:../
     */
    @RequestMapping(value = "/tafelverwaltung/tafel/hinzufuegen", method = RequestMethod.POST)
    public String BenutzerverwaltungNeueTafelVerarbeiten(@ModelAttribute("tafel") @Valid CTafel tafel, BindingResult result, RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tafel", tafel);
            return "benutzerverwaltung/hinzufuegen-tafel";
        } else {
            CKontaktDAO kontaktDAO = new CKontaktDAO();
            CAdresseDAO adresseDAO = new CAdresseDAO();
            CTafelDAO tafelDAO = new CTafelDAO();
            tafel.setStatus(CTafel.StatusEnum.grün);

            try {
                adresseDAO.createAdresse(tafel.getAdresse());
                kontaktDAO.createKontakt(tafel.getKontakt());
                tafelDAO.createTafel(tafel);
            } catch (Exception e) {
                model.addAttribute("Fehler", "Neue Tafel konnte nicht in die DB aufgenommen werden.");
                return "fehler";
            }

            redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Neue Tafel erfolgreich hinzugefügt!</div>");
            return "redirect:../";
        }
    }

    /**
     * Spender hinzufügen anzeigen
     *
     * @param spender
     * @param model
     * @param request
     * @return benutzerverwaltung/hinzufuegen-spender
     */
    @RequestMapping(value = "/spenderverwaltung/spender/hinzufuegen", method = RequestMethod.GET)
    public String BenutzerverwaltungNeuerSpender(@ModelAttribute("spender") CSpender spender, Model model, HttpServletRequest request) {
        model.addAttribute("spender", spender);
        return "benutzerverwaltung/hinzufuegen-spender";
    }

    /**
     * Spender hinzufügen verarbeiten
     *
     * @param spender
     * @param result
     * @param redirect
     * @param model
     * @return redirect:../
     */
    @RequestMapping(value = "/spenderverwaltung/spender/hinzufuegen", method = RequestMethod.POST)
    public String BenutzerverwaltungNeuerSpenderVerarbeiten(@ModelAttribute("spender") @Valid CSpender spender, BindingResult result, RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("spender", spender);
            return "benutzerverwaltung/hinzufuegen-spender";
        } else {
            CKontaktDAO kontaktDAO = new CKontaktDAO();
            CAdresseDAO adresseDAO = new CAdresseDAO();
            CSpenderDAO spenderDAO = new CSpenderDAO();
            spender.setStatus(CSpender.StatusEnum.grün);

            try {
                adresseDAO.createAdresse(spender.getAdresse());
                kontaktDAO.createKontakt(spender.getKontakt());
                spenderDAO.createSpender(spender);
            } catch (Exception e) {
                model.addAttribute("Fehler", "Neuer Spender konnte nicht in die DB aufgenommen werden.");
                return "fehler";
            }

            redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Neuer Spender erfolgreich hinzugefügt!</div>");
            return "redirect:../";
        }
    }

//Bearbeiten-----------------
    //Benutzer Details Seite anzeigen
    /**
     *
     * @param benutzer
     * @param id
     * @param model
     * @return benutzerverwaltung/details
     */
    @RequestMapping(value = "/benutzerverwaltung/{benutzerID}/details", method = RequestMethod.GET)
    public String BenutzerverwaltungDetails(@ModelAttribute("benutzer") CBenutzer benutzer, @PathVariable("benutzerID") int id, Model model) {
        //Get Benutzer by ID + Fehlerabfangen
        try {
            benutzer.getBenutzer(id);
        } catch (Exception e) {
            System.out.println("Benutzer nicht vorhanden- falsche ID in der http");
            model.addAttribute("Fehler", "Dieser Benutzer konnte nicht gefunden werden.");
            return "fehler";
        }

        //Benutzer einer Tafel/ einem Spender zuordnen
        CSpenderTafelListe orgaliste = new CSpenderTafelListe();
        switch (benutzer.getRolle()) {
            case Spender:
                orgaliste.setSpender(CSpenderDAO.getSpenderListe());
                break;
            case Tafel:
                orgaliste.setTafel(CTafelDAO.getTafelListe());
                break;
            default:
                System.out.println("Die Rolle des Benutzers wurde nicht auf Spender/Tafel festgelegt");
        }

        model.addAttribute("orgaliste", orgaliste);
        model.addAttribute("benutzer", benutzer);
        tempbenutzer = benutzer; // Übergabe des alten objects
        return "benutzerverwaltung/details";
    }

    /**
     * Benutzer Details verarbeiten
     *
     * @param benutzer
     * @param result
     * @param id
     * @param orgaliste
     * @param redirectAttributes
     * @param request
     * @param model
     * @return redirect:../
     * @throws Exception
     */
    @RequestMapping(value = "/benutzerverwaltung/{benutzerID}/details", method = RequestMethod.POST)
    public String BenutzerverwaltungDetailsVerarbeiten(@Valid @ModelAttribute("benutzer") CBenutzer benutzer, BindingResult result, @PathVariable("benutzerID") int id, @ModelAttribute("orgaliste") CSpenderTafelListe orgaliste, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) throws Exception {
        if (result.hasErrors()) {
            System.out.println("result.hasErrors");
            model.addAttribute("benutzer", benutzer);
            return "benutzerverwaltung/details";
        } else {
            //Überprüfung ob alle Felder ausgefüllt wurden
            if ("".equals(benutzer.getVorname()) || "".equals(benutzer.getNachname()) || "".equals(benutzer.getEMail()) || benutzer.getPasswort() == null) {
                String Ausgabe = "<div class='fehler'>Fehler: Um einen Benutzer verändern zu können müssen alle Felder ausgefüllt sein!</div>";
                model.addAttribute("Ausgabe", Ausgabe);
                return "benutzerverwaltung/details";
            }

            //Für die Zuordnung einer Tafel / eines Spenders zum Benutzer
            if (orgaliste != null && orgaliste.getSelectedID() != 0) {
                System.out.println("Organisation einem Benutzer zuordnen");
                switch (benutzer.getRolle()) {
                    case Spender:
                        //Hole den Spender aus der DB anhand der ausgewählten ID (in der orgaliste)
                        CSpender spender = CSpenderDAO.getSpenderById(orgaliste.getSelectedID());
                        benutzer.setSpender(spender);
                        break;

                    case Tafel:
                        //Hole die Tafel aus der DB anhand der ausgewählten ID (in der orgaliste)
                        CTafel tafel = CTafelDAO.getTafelById(orgaliste.getSelectedID());
                        benutzer.setTafel(tafel);
                        break;
                }
            }

            String Ausgabe;
            //Nur wenn was verändert wurde soll dies in die DB
            if (tempbenutzer.equals(benutzer)) {
                Ausgabe = "<div class='fehler'>Es wurden keine Änderungen vorgenommen</div>";
            } else {
                //Erst abspeichern der noch nicht gesicherten Werte Adresse Kontakt, dann Spender/Tafel, dann Benutzer!
                CAdresseDAO adresseDAO = new CAdresseDAO();
                CKontaktDAO kontaktDAO = new CKontaktDAO();
                CBenutzerDAO benutzerDAO = new CBenutzerDAO();

                try {
                    //Wenn der Benutzer ein Spender ist
                    if (benutzer.getRolle() == CBenutzer.RolleEnum.Spender && benutzer.getSpender() != null) {
                        CSpenderDAO spenderDAO = new CSpenderDAO();
                        adresseDAO.createAdresse(benutzer.getSpender().getAdresse());
                        kontaktDAO.createKontakt(benutzer.getSpender().getKontakt());
                        spenderDAO.createSpender(benutzer.getSpender());
                    }
                    //Wenn der Benutzer eine Tafel ist
                    if (benutzer.getRolle() == CBenutzer.RolleEnum.Tafel && benutzer.getTafel() != null) {
                        CTafelDAO tafelDAO = new CTafelDAO();
                        adresseDAO.createAdresse(benutzer.getTafel().getAdresse());
                        kontaktDAO.createKontakt(benutzer.getTafel().getKontakt());
                        tafelDAO.createTafel(benutzer.getTafel());
                    }
                    //Benutzer aktualisieren
                    benutzerDAO.createBenutzer(benutzer);
                } catch (Exception e) {
                    Ausgabe = "<div class='fehler'>Fehler bei der Verarbeitung \nggf. ist diese EMail bereits vorhanden.</div>";
                    System.out.println(e);
                    model.addAttribute("Ausgabe", Ausgabe);
                    return "benutzerverwaltung/details";
                }
                Ausgabe = "<div class='erfolg'>Benutzer wurde erfolgreich bearbeitet.</div>";
            }
            redirectAttributes.addFlashAttribute("Ausgabe", Ausgabe);
            return "redirect:../"; // Zurück auf Benutzerverwaltung
        }
    }

    /**
     * Benutzer kennwort ändern anzeigen
     *
     * @param id
     * @param benutzer
     * @param model
     * @return benutzerverwaltung/kennwort
     */
    @RequestMapping(value = "/benutzerverwaltung/{benutzerID}/kennwort", method = RequestMethod.GET)
    public String BenutzerverwaltungKennwort(@PathVariable("benutzerID") int id, @ModelAttribute("benutzer") CBenutzer benutzer, Model model) {
        //Get Benutzer by ID + Fehlerabfangen
        try {
            benutzer.getBenutzer(id);
        } catch (Exception e) {
            System.out.println("Benutzer nicht vorhanden- falsche ID in der http");
            model.addAttribute("Fehler", "Dieser Benutzer konnte nicht gefunden werden.");
            return "fehler";
        }
        benutzer.setPasswort(null);
        return "benutzerverwaltung/kennwort";
    }

    /**
     * Benutzer kennwort ändern verarbeiten
     *
     * @param id
     * @param benutzer
     * @param result
     * @param model
     * @param redirect
     * @param request
     * @return redirect:../
     */
    @RequestMapping(value = "/benutzerverwaltung/{benutzerID}/kennwort", method = RequestMethod.POST)
    public String BenutzerverwaltungKennwortVerarbeiten(@PathVariable("benutzerID") int id, @Valid @ModelAttribute("benutzer") CBenutzer benutzer, BindingResult result, Model model, RedirectAttributes redirect, HttpServletRequest request) {
        if (result.hasErrors()) {
            benutzer.getBenutzer(id);
            return "benutzerverwaltung/kennwort";
        } else {

            //Passwort ändern

            String passwort = benutzer.getPasswort();
            //Get Benutzer by ID + Fehlerabfangen
            try {
                benutzer.getBenutzer(id);
            } catch (Exception e) {
                System.out.println("Benutzer nicht vorhanden- falsche ID in der http");
                model.addAttribute("Fehler", "Dieser Benutzer konnte nicht gefunden werden.");
                return "fehler";
            }
            benutzer.setPasswort(passwort);
            benutzer.hashPasswort();

            //Datenbankeintrag
            CBenutzerDAO benutzerDAO = new CBenutzerDAO();
            try {
                benutzerDAO.createBenutzer(benutzer);
            } catch (Exception e) {
                System.out.println("Fehler beim ändern des Passworts \n" + e);
                redirect.addFlashAttribute("Fehler", "Datenbankeintrag gescheitert. - Passwort ändern");
                return "fehler";
            }

            //Mail an den Benutzer senden
            String mailzusatz = "Ihr Passwort wurde geändert!\n\r"
                    + "Passwort:" + passwort;
            Runnable mailThread = new TMailSendenNachVorlageMitZusatz(benutzer.getEMail(), 1, mailzusatz); // 12- Mail an Koordinator abgelehnt
            new Thread(mailThread).start();

            String Ausgabe = "<div class='erfolg'>Passwort wurde erfolgreich geändert</div>";
            redirect.addFlashAttribute("Ausgabe", Ausgabe);
            return "redirect:../"; // Zurück auf Benutzerverwaltung
        }
    }
    
    /**
     * Benutzer von Organisation lösen verarbeiten
     *
     * @param benutzerID
     * @param result
     * @param model
     * @param redirect
     * @param request
     */
    @RequestMapping(value = "/benutzerverwaltung/loesen/{benutzerID}", method = RequestMethod.GET)
    public String BenutzerverwaltungBenutzerLoesen(@PathVariable("benutzerID") int id, Model model, RedirectAttributes redirect) {
        CBenutzer benutzer = new CBenutzer();
         try { 
             benutzer.getBenutzer(id);
         } catch (Exception e) {
             System.out.println("Benutzer nicht vorhanden- falsche ID in der http");
             model.addAttribute("Fehler", "Dieser Benutzer konnte nicht gefunden werden.");
             return "fehler";
         }

         //Benutzer von Organisation lösen
         benutzer.setSpender(null);
         benutzer.setTafel(null);

         //Datenbankeintrag
         CBenutzerDAO benutzerDAO = new CBenutzerDAO();
         try {
             benutzerDAO.createBenutzer(benutzer);
         } catch (Exception e) {
             System.out.println("Fehler beim Lösen des Benutzers von der Organisation \n" + e);
             redirect.addFlashAttribute("Fehler", "Datenbankeintrag gescheitert. - Benutzer Lösen");
             return "fehler";
         }

         
         String Ausgabe = "<div class='erfolg'>Benutzer wurde erfolgreich von seiner Organisation gelöst.</div>";
         redirect.addFlashAttribute("Ausgabe", Ausgabe);
        return "redirect:../"; // Zurück auf Benutzerverwaltung
    }
}

