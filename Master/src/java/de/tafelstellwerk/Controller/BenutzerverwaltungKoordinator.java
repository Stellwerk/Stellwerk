package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CSpenderDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
import de.tafelstellwerk.Threads.TMailCustomBenutzergruppe;
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
 * Dieser Controller erstellt die meisten Webseiten, auf die der Koordinator
 * zugreifen kann. Alle Seiten sind nur als Koordinator erreichbar.
 *
 * @author gpaschke
 */
@Controller
@Secured("ROLE_KOORD")
public class BenutzerverwaltungKoordinator {

    /**
     * Alle Benutzer anzeigen
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/benutzerverwaltung-koordinator", method = RequestMethod.GET)
    public String BenutzerverwaltungKoordinator(Model model) {
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
        List<CBenutzer> liste = benutzerDAO.getBenutzerListe();
        model.addAttribute("liste", liste);
        return "benutzerverwaltung/benutzerverwaltung-koordinator"; // Name der JSP/View
    }

    /**
     * Benutzer Status Seite anzeigen
     *
     * @param benutzerID
     * @param benutzer
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/benutzerverwaltung-koordinator/status/{benutzerID}/", method = RequestMethod.GET)
    public String BenutzerverwaltungKoordinatorStatus(@PathVariable("benutzerID") int benutzerID, @ModelAttribute("benutzer") CBenutzer benutzer, Model model, HttpServletRequest request) {
        //Get Benutzer by ID + Fehlerabfangen
        try {
            benutzer.getBenutzer(benutzerID);
        } catch (Exception e) {
            System.out.println("Benutzer nicht vorhanden- falsche ID in der http");
            model.addAttribute("Fehler", "Dieser Benutzer konnte nicht gefunden werden.");
            return "fehler";
        }

        if (benutzer.getTafel() == null && benutzer.getSpender() == null) {
            System.out.println("Benutzer werder Spender noch Tafel");
            model.addAttribute("Fehler", "Dieser Benutzer ist werder einer Tafel, noch einem Spender zugeordnet");
            return "fehler";
        }
        model.addAttribute("benutzer", benutzer);
        return "benutzerverwaltung/statusaendern"; // Name der JSP/View
    }

    /**
     * Benutzer Status verarbeiten
     *
     * @param benutzer
     * @param model
     * @param result
     * @param redirect
     * @return
     */
    @RequestMapping(value = "/benutzerverwaltung-koordinator/status/*", method = RequestMethod.POST)
    public String BenutzerverwaltungKoordinatorStatusVerarbeiten(@ModelAttribute("benutzer") CBenutzer benutzer, Model model, BindingResult result, RedirectAttributes redirect) {
        CBenutzer tbenutzer = new CBenutzer();
        if (benutzer.getTafel() != null) {

            System.out.println(benutzer.getTafel().toString());
            CTafelDAO tafelDAO = new CTafelDAO();
            tbenutzer.setTafel(CTafelDAO.getTafelById(benutzer.getTafel().getId())); //Zunächst die Tafel des Benuzters auslesen
            tbenutzer.getTafel().setStatus(benutzer.getTafel().getStatus());        //Dann den Status und Koommentar ändern
            tbenutzer.getTafel().setNotizen(benutzer.getTafel().getNotizen());
            try {
                tafelDAO.createTafel(tbenutzer.getTafel());
            } catch (Exception e) {
                System.out.println(e);
                return "fehler";
            } //Dann die Tafel updaten
        } else if (benutzer.getSpender() != null) {

            System.out.println(benutzer.getSpender().toString());
            CSpenderDAO spenderDAO = new CSpenderDAO();
            tbenutzer.setSpender(CSpenderDAO.getSpenderById(benutzer.getSpender().getId())); // Zunächst den Spender des Benutzers auslesen
            tbenutzer.getSpender().setStatus(benutzer.getSpender().getStatus());        //Dann den Status und Koommentar ändern
            tbenutzer.getSpender().setNotizen(benutzer.getSpender().getNotizen());
            try {
                spenderDAO.createSpender(tbenutzer.getSpender());
            } catch (Exception e) {
                System.out.println(e);
                return "fehler";
            } //Dann den Spender updaten
        } else {
            model.addAttribute("Fehler", "Benutzer ist weder Tafel noch Spender.");
            return "fehler";
        }

        System.out.println("Benutzerstatus wurder erfolgreich geändert. Benutzerid: " + benutzer.getBenutzerID());
        redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Status / Kommentar wurden erfolgreich geändert.</div>");
        return "redirect:/benutzerverwaltung-koordinator"; // Name der JSP/View
    }

    /**
     * EMail an Benutzergruppe senden anzeigen
     *
     * @param email
     * @param model
     * @return
     */
    @RequestMapping(value = "/benutzerverwaltung-koordinator/email", method = RequestMethod.GET)
    public String BenutzerverwaltungKoordinatorEMail(@ModelAttribute("email") CEMail email, Model model) {
        model.addAttribute("email", email);
        return "mail/mailanrolle"; // Name der JSP/View
    }

    /**
     * EMail an Benutzergruppe senden verarbeiten
     *
     * @param email
     * @param result
     * @param redirect
     * @param model
     * @return
     */
    @RequestMapping(value = "/benutzerverwaltung-koordinator/email", method = RequestMethod.POST)
    public String BenutzerverwaltungKoordinatorEMailSenden(@Valid @ModelAttribute("email") CEMail email, BindingResult result, RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("email", email);
            return "mail/mailanrolle"; // Name der JSP/View
        } else {
            CBenutzer.RolleEnum rolle;
            switch (email.getEmpfaenger()) {
                case "Administrator":
                    rolle = CBenutzer.RolleEnum.Administrator;
                    break;
                case "Koordinator":
                    rolle = CBenutzer.RolleEnum.Koordinator;
                    break;
                case "Spender":
                    rolle = CBenutzer.RolleEnum.Spender;
                    break;
                case "Tafel":
                    rolle = CBenutzer.RolleEnum.Tafel;
                    break;
                default:
                    System.out.println("Fehler bei der Rollenauswahl");
                    return "fehler";
            }


            try {
                Runnable mailThread = new TMailCustomBenutzergruppe(rolle, email.getBetreff(), email.getNachricht()); //rolle, nachricht, betreff
                new Thread(mailThread).start();
            } catch (Exception e) {
                System.out.println("Fehler beim Ausführen des Mailthreads custombenutzergruppe" + e);
                model.addAttribute("Fehler", "Fehler beim Ausführen des Mailthreads custombenutzergruppe");
                return "fehler";
            }

            redirect.addFlashAttribute("Ausgabe", "<div class='erfolg'>Ihre E-Mail wird nun an die ausgewählte Benutzergruppe gesendet...</div>");
            return "redirect:/benutzerverwaltung-koordinator"; // Name der JSP/View
        }
    }
}
