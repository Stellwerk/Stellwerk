/*
 * gpaschke
 */
package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Model.CEMailVorlage;
import de.tafelstellwerk.Model.CSpende;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CEMailVorlageDAO;
import de.tafelstellwerk.Repository.CSpendeDAO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
 * Dieser Controller erstellt Seiten, über die E-Mails an Akteure generiert
 * werden.
 *
 * @author gpaschke
 */
@Controller
//@Secured({"ROLE_ADMIN" ,"ROLE_KOORD", "ROLE_SPENDER", "ROLE_TAFEL"})
public class MailController {

    private CSpende tempspende;

    /**
     * Das Formular zum Reklamieren einer Spende. Nur für Spender und Tafel.
     *
     * @param spendeID
     * @param email
     * @param model
     * @return
     */
    @Secured({"ROLE_SPENDER", "ROLE_TAFEL"})
    @RequestMapping(value = "/meine-spenden/{spendeID}/reklamieren", method = RequestMethod.GET)
    public String Reklamation(@PathVariable("spendeID") int spendeID, @ModelAttribute("email") CEMail email, Model model) {
        //Authentifizierung, Benutzer soll die Spende erstellt haben
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authMail = auth.getName();

        CSpende spende = CSpendeDAO.getSpendeById(spendeID);
        if (spende == null || !spende.getBenutzer().getEMail().equals(authMail)) {
            model.addAttribute("Fehler", "Es wurde keine passende Spende gefunden, die reklamiert werden kann.");
            return "fehler";
        }
        tempspende = spende;
        email = new CEMail();
        model.addAttribute("email", email);
        model.addAttribute("spendeID", spendeID);
        model.addAttribute("spendenname", spende.getProduktbezeichnung());
        model.addAttribute("spendenerstellungsdatum", spende.getErstellungsdatum());

        return "mail/reklamieren";
    }

    /**
     * Erzeugt die Seite an die das Reklamieren-Formular geschickt wird. Nur für
     * Admin.
     *
     * @param spendeID
     * @param email
     * @param result
     * @param redirect
     * @param model
     * @return
     */
    @Secured({"ROLE_SPENDER", "ROLE_TAFEL"})
    @RequestMapping(value = "/meine-spenden/{spendeID}/reklamieren", method = RequestMethod.POST)
    public String ReklamationSenden(@PathVariable("spendeID") int spendeID, @ModelAttribute("email") @Valid CEMail email, BindingResult result, RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            CSpende spende = CSpendeDAO.getSpendeById(spendeID);
            tempspende = spende;
            model.addAttribute("email", email);
            model.addAttribute("spendeID", spendeID);
            model.addAttribute("spendenname", spende.getProduktbezeichnung());
            model.addAttribute("spendenerstellungsdatum", spende.getErstellungsdatum());
            return "mail/reklamieren";
        }

        //EMail Objekt wird generiert und übernimmt die Inhalte der Formularfelder
        CEMail emailInstance = CEMail.createInstance();
        emailInstance.setNachricht(email.getNachricht() + "\n\nHier die Zugehörige Spende:\n" + tempspende.getProduktbezeichnung().toString());
        emailInstance.setBetreff(email.getBetreff());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authMail = auth.getName();
        emailInstance.setSender(authMail);

        List<CBenutzer> liste = CBenutzerDAO.getBenutzerListeByRolle(CBenutzer.RolleEnum.Koordinator);
        if (liste.isEmpty()) {
            System.out.println("Fehler: Es wurde kein Koordinator in der Datenbank gefunden! Kein Benachrichtigung an einen Koordinator möglich.");
            model.addAttribute("Fehler", "Es wurde kein Koordinator in der Datenbank gefunden! Kein Benachrichtigung an einen Koordinator möglich.");
            return "fehler";
        } else {
            for (int i = 0; i < liste.size(); i++) {
                emailInstance.setEmpfaenger(liste.get(i).getEMail());
                //EMail wird gesendet
                try {
                    emailInstance.sendeMail();
                } catch (Exception e) {
                    System.out.println("Fehler beim Senden einer Reklamations-Mail" + e);
                    return "fehler";
                }
            }
        }

        //Ausgabe auf der nächsten Seite, dass EMail gesendet wurde
        String Ausgabe = "<div class='erfolg'>Ihre Mail wird nun gesendet...</div>";
        redirect.addFlashAttribute("Ausgabe", Ausgabe);

        return "redirect:/meine-spenden";
    }

    /**
     * Erzeugt die Liste der E-Mail-Vorlagen. Nur für Admin.
     *
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/emailverwaltung", method = RequestMethod.GET)
    public String EMailverwaltung(Model model) {
        //Es wird eine Liste aller E-Mail-Vorlagen generiert
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + "versucht die Seite /emailverwaltung (GET) zu öffnen");
        try {
            List<CEMailVorlage> liste = CEMailVorlageDAO.getEMailVorlagenListe();
            if (liste == null) {
                model.addAttribute("nachricht", "<div class='fehler'>Es sind keine E-Mail-Vorlagen vorhanden!</div>");
            }
            model.addAttribute("liste", liste);
            return "emailverwaltung/emailverwaltung";
        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /emailverwaltung (GET) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
            return "fehler";
        }
    }

    /**
     * Erzeugt die Detailseite, auf der die Vorlage bearbeitet werden kann. Nur
     * für Admin.
     *
     * @param eMailVorlage
     * @param result
     * @param vorlageID
     * @param model
     * @param redirect
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/emailverwaltung/{vorlageID}/details", method = RequestMethod.GET)
    public String EMailverwaltungDetails(@ModelAttribute("eMailVorlage") CEMailVorlage eMailVorlage, BindingResult result, @PathVariable("vorlageID") int vorlageID, Model model, RedirectAttributes redirect) {
        //Es wird die ausgewählte E-Mail Vorlage angezeigt
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + "versucht die Seite /emailverwaltung/{vorlageID}/details (GET) zu öffnen");
        try {
            //Variablen initialisieren und entsprechende Vorlage aus der Datenbank laden
            CEMailVorlageDAO MailVorlageDAO = new CEMailVorlageDAO();
            CEMailVorlage eMailvorlage;
            eMailvorlage = MailVorlageDAO.getEMailVorlageById(vorlageID);

            // Prüfung, ist die ausgewählte Vorlage in der Datenbank vorhanden? Wenn nicht, dann Fehlerausgabe und Weiterleitung an die Vorlagenübersicht
            if (eMailvorlage == null) {
                redirect.addFlashAttribute("nachricht", "<div class='fehler'>Die Vorlage konnte nicht geladen werden!</div>");
                return "redirect:../";
            } else {
                model.addAttribute("formular", eMailvorlage);
                return "emailverwaltung/details";
            }
        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /emailverwaltung/{vorlageID}/details (GET) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("nachricht", "<div class='fehler'>Die Vorlage konnte nicht geladen werden!</div>");
            return "fehler";
        }
    }

    /**
     * Erzeugt die Seite, an die das Vorlage-Formular nach Bearbeitung geschickt
     * wird. Nur für Admin
     *
     * @param eMailVorlage
     * @param result
     * @param model
     * @param request
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/emailverwaltung/{vorlageID}/details", method = RequestMethod.POST)
    public String EMailverwaltungDetailsAbsenden(@ModelAttribute("eMailVorlage") CEMailVorlage eMailVorlage, BindingResult result, Model model, HttpServletRequest request) {
        //E-Mail Vorlage aktualisieren
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + "versucht die Seite /emailverwaltung/{vorlageID}/details (POST) zu öffnen");
        try {
            //Datenbankobjekt aktualisieren, Erfolgsmeldung generieren und an die Vorlagenübersicht weiterleiten
            CEMailVorlageDAO MailVorlageDAO = new CEMailVorlageDAO();
            MailVorlageDAO.createEMailVorlage(eMailVorlage);
            model.addAttribute("nachricht", "<div class='erfolg'>Die Vorlage wurde erfolgreich aktualisiert!</div>");
            return "redirect:../";
        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /emailverwaltung/{vorlageID}/details (POST) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("nachricht", "<div class='fehler'>Die Vorlage konnte nicht aktualisiert werden!</div>");
            return "emailverwaltung/emailverwaltung";
        }
    }
}
