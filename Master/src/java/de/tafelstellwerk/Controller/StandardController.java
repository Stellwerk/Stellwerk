package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Forms.Kontaktformular;
import de.tafelstellwerk.Model.CAllgemeines;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.test.AddDBReset;
import de.tafelstellwerk.test.AddDBResetTest;
import de.tafelstellwerk.test.test;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Der Controller steuert allgemeine Seiten, wie Startseite, Impressum, AGB und
 * Kontakt
 *
 * @author Patrick
 */
@Controller
public class StandardController {

    /**
     * Erzeugt die Startseite
     *
     * @param allgemeines
     * @param model
     * @return
     */
    @RequestMapping("/") // Die URL
    public String index(CAllgemeines allgemeines, Model model) {
        try {
            allgemeines = CAllgemeinesDAO.getAllgemeinesById(1);
            String titel = allgemeines.getTitel();
            String inhalt = allgemeines.getInhalt();
            model.addAttribute("titel", titel);
            model.addAttribute("inhalt", inhalt);
        } catch (Exception e) {
            return "fehler404";
        }
        return "inhalt"; // Name der JSP/View
    }

    /**
     * Erzeugt die Zugriff-verweigert-Seite
     *
     * @return
     */
    @RequestMapping("/zugriff-verweigert")
    public String ZugriffVerweigert() {
        return "zugriff-verweigert"; // Name der JSP/View
    }

    /**
     * Erzeugt die Login-Seite
     *
     * @return
     */
    @RequestMapping("/login")
    public String Login() {
        return "login"; // Name der JSP/View
    }

    /**
     *
     * @param allgemeines
     * @param model
     * @return
     */
    @RequestMapping("/impressum")
    public String ImpressumAnzeigen(CAllgemeines allgemeines, Model model) {
        try {
            allgemeines = CAllgemeinesDAO.getAllgemeinesById(3);
            String titel = allgemeines.getTitel();
            String inhalt = allgemeines.getInhalt();
            model.addAttribute("titel", titel);
            model.addAttribute("inhalt", inhalt);
        } catch (Exception e) {
            return "fehler404";
        }
        return "inhalt"; // Name der JSP/View
    }

    /**
     * Erzeugt die allgemeinen Geschäftsbedingungen
     *
     * @param allgemeines
     * @param model
     * @return
     */
    @RequestMapping("/allgemeine-geschaeftsbedingungen")
    public String AGBsAnzeigen(CAllgemeines allgemeines, Model model) {
        try {
            allgemeines = CAllgemeinesDAO.getAllgemeinesById(2);
            String titel = allgemeines.getTitel();
            String inhalt = allgemeines.getInhalt();
            model.addAttribute("titel", titel);
            model.addAttribute("inhalt", inhalt);
        } catch (Exception e) {
            return "fehler404";
        }
        return "inhalt"; // Name der JSP/View
    }

    /**
     * Erzeugt die Kontaktseite
     *
     * @param formular
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/kontakt", method = RequestMethod.GET)
    public String kontakt(Kontaktformular formular, BindingResult result, Model model) {
        model.addAttribute("Kontakt", formular);
        return "kontakt";
    }

    /**
     * Erzeugt die POST-Methode, an die das Kontaktformular gesendet wird.
     *
     * @param formular
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/kontakt", method = RequestMethod.POST)
    public String kontaktAbsenden(@Valid @ModelAttribute("Kontakt") Kontaktformular formular, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("Kontakt", formular);
            return "kontakt";
        }

        List<CBenutzer> listeKoordinator = CBenutzerDAO.getBenutzerListeByRolle(CBenutzer.RolleEnum.Koordinator);

        try {
            if (listeKoordinator.isEmpty()) {
                System.out.println("Fehler: Es wurden keine Koordinatroren in der Datenbank gefunden!");
            }

            //E-Mail an Koordinator
            String betreff = "Tafelstellwerk - Kontaktformular: ";
            String text = "Kontakt: \n\n" + "Name: " + formular.getName()
                    + "\nStraße: " + formular.getStrasse()
                    + "\nPLZ / Ort: "
                    + formular.getPlz() + " " + formular.getOrt()
                    + "\nE-Mail: " + formular.getEmail()
                    + "\nTelefon " + formular.getTel()
                    + "\nFax: " + formular.getFax()
                    + "\n\n Nachricht: \n\n" + formular.getText();

            for (int j = 0; j < listeKoordinator.size(); j++) {
                CEMail mm = CEMail.createInstance();
                mm.setSender("tafelstellwerk@hs-osnabrueck.de");
                mm.setEmpfaenger(listeKoordinator.get(j).getEMail());
                mm.setBetreff("Tafelstellwerk - Kontaktformular");
                mm.setNachricht(text);
                mm.sendeMail();
            }



            model.addAttribute("Kontakt", formular);
            model.addAttribute("nachricht", "<div class='erfolg'>Die Nachricht wurde erfolgreich gesendet.</div>");
            return "kontakt";
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Das Kontaktformular konnte nicht abgeschickt werden!");
            model.addAttribute("nachricht", "<div class='fehler'>Die Nachricht konnte nicht gesendet werden.</div>");
            return "kontakt";
        }

    }

    /**
     * Erzeugt die Verwaltungsseite für die allgemeinen Inhalte. Nur für Admin.
     *
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/allgemeines-verwalten", method = RequestMethod.GET)
    public String Allgemeinverwaltung(Model model) {
        //Es wird eine Liste aller E-Mail-Vorlagen generiert
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + "versucht die Seite /emailverwaltung (GET) zu öffnen");
        try {
            List<CAllgemeines> liste = CAllgemeinesDAO.getAllgemeinesListe();
            if (liste == null) {
                model.addAttribute("nachricht", "<div class='fehler'>Es sind keine Vorlagen vorhanden!</div>");
            }
            model.addAttribute("liste", liste);
            return "allgemeines-verwalten/allgemeines-verwalten";
        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /allgemeines-verwalten (GET) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
            return "fehler";
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/allgemeines-verwalten/{id}/details", method = RequestMethod.GET)
    public String AllgemeinverwaltungDetails(@ModelAttribute("allgemeines") CAllgemeines allgemeines, BindingResult result, @PathVariable("id") int vorlageID, Model model, RedirectAttributes redirect) {
        //Es wird die ausgewählte E-Mail Vorlage angezeigt
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + "versucht die Seite /emailverwaltung/{vorlageID}/details (GET) zu öffnen");
        try {
            //Variablen initialisieren und entsprechende Vorlage aus der Datenbank laden

            allgemeines = CAllgemeinesDAO.getAllgemeinesById(vorlageID);


            // Prüfung, ist die ausgewählte Vorlage in der Datenbank vorhanden? Wenn nicht, dann Fehlerausgabe und Weiterleitung an die Vorlagenübersicht
            if (allgemeines == null) {
                redirect.addFlashAttribute("nachricht", "<div class='fehler'>Die Vorlage konnte nicht geladen werden!</div>");
                return "redirect:../";
            } else {
                model.addAttribute("formular", allgemeines);
                return "allgemeines-verwalten/details";
            }
        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /allgemeines-verwalten/{vorlageID}/details (GET) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("nachricht", "<div class='fehler'>Die Vorlage konnte nicht geladen werden!</div>");
            return "fehler";
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/allgemeines-verwalten/{id}/details", method = RequestMethod.POST)
    public String EMailverwaltungDetailsAbsenden(@ModelAttribute("allgemeines") CAllgemeines allgemeines, BindingResult result, Model model, HttpServletRequest request) {
        //Vorlage aktualisieren
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + "versucht die Seite /emailverwaltung/{vorlageID}/details (POST) zu öffnen");
        try {
            //Datenbankobjekt aktualisieren, Erfolgsmeldung generieren und an die Vorlagenübersicht weiterleiten
            CAllgemeinesDAO.createAllgemeines(allgemeines);
            model.addAttribute("nachricht", "<div class='erfolg'>Die Vorlage wurde erfolgreich aktualisiert!</div>");
            return "redirect:../";
        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /allgemeines-verwalten/{id}/details (POST) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("nachricht", "<div class='fehler'>Die Vorlage konnte nicht aktualisiert werden!</div>");
            return "allgemeines-verwalten/allgemeines-verwalten";
        }
    }
    
    /**
     * Vorsicht !!!!
     * diese Seite ist für die inizialisierung der Datenbank gedacht und erzeugt alle
     *
     * @return
     */
//    @RequestMapping("/zugriff-verweigert/datenbank/reset/aeXq38p5")
//    public String DatenbankRESET() {
//        AddDBResetTest.main(null);
//        return "zugriff-verweigert"; // Name der JSP/View
//    }
    
    
    @RequestMapping("/mailtest")
    public String Mailtest() {
        System.out.println("");
        System.out.println("----------------------------Starte Mailtest----------------------");
        test.main(null);
        return "zugriff-verweigert"; // Name der JSP/View
    }
}
