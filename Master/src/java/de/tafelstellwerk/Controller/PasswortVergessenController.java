package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

/**
 * Dieser Controller ist für die Passwort-vergessen-Seite, das Erzeugen der
 * E-Mail und für die Seiten, die nötig sind um ein neues Passwort zu erstellen.
 *
 * @author Simon Lau
 * @author Patrick
 */
@Controller
public class PasswortVergessenController {

    boolean schluesselOK;
    String eMail;

    /**
     * Erzeugt die Passwort-vergessen-Seite, auf der die E-Mail Adresse
     * eingegeben werden kann.
     *
     * @return
     */
    @RequestMapping(value = "/passwort-vergessen", method = RequestMethod.GET)
    public String passwort_vergessen() {
        return "passwort-vergessen";
    }

    /**
     * Erzeugt die Seite, and die die E-Mailadresse gesendet wird.
     *
     * @param eMailTEMP
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/passwort-vergessen", method = RequestMethod.POST)
    public String NeuesPasswortVersenden(@RequestParam("eMail") String eMailTEMP, Model model, HttpServletRequest request) {
        //Objekte erstellen
        CBenutzer benutzer = new CBenutzer();
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
        String passwort;
        String hashpasswort = null;
        String nachricht;
        eMail = eMailTEMP;
        String bestaetigungsSchluessel;
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        try {

            //Accountinformationen laden
            try {
                benutzer.getBenutzer(eMail);
            } catch (Exception e) {
                System.out.println("Folgender Fehler ist beim Aufruf der Seite Passwort-vergessen (POST) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
                nachricht = "<div class='login fehler'>Benutzer nicht gefunden! </div>";
                model.addAttribute("nachricht", nachricht);
                return ("passwort-vergessen");
            }

            //Bestätigungscode generieren
            bestaetigungsSchluessel = benutzer.getPasswort();
            bestaetigungsSchluessel = CBenutzer.hashPasswort(bestaetigungsSchluessel);
            url = url + "pwSchluessel" + "?schluesselMail=" + bestaetigungsSchluessel + "&" + "eMailMail=" + eMail;
            //E-Mail generieren und senden
            CEMail mm = CEMail.createInstance();
            mm.setSender("tafelstellwerk@hs-osnabrueck.de");
            mm.setEmpfaenger(eMail);
            mm.setBetreff("Ihr Bestätigungsschlüssel zum Tafelstellwerk");
            mm.setNachricht("Dies ist ihr temporärer Sicherheitsschlüssel zum Ändern ihres Passworts.\n"
                    + "Sie können diesen Schlüssel nun im Portal Tafelstellwerk eingeben. Sollten Sie die Seite schon geschlossen haben oder der Link mit der Weiterleitung nicht funktionieren, können Sie über die Menüpunkte \n"
                    + "'Anmelden' -> 'Passwort vergessen' -> 'Ich habe bereits einen Sicherheitsschlüssel erhalten' erneut zu dem Eingabefeld gelangen!\n"
                    + "Geben Sie den Schlüssel ein und bestätigen Sie mit 'Sicherheitsschlüssel einlösen'! Anschließend können Sie ein neues Passwort vergeben.\n"
                    + "Sollten sie Hilfe benötigen, wenden Sie sich bitte an ihren Koordinator.\n"
                    + "\n"
                    + "Ihr neuer Schlüssel lautet: " + bestaetigungsSchluessel
                    + "\n"
                    + "Weiterleitung: " + url
                    + "\n"
                    + "\n"
                    + "Mit freundlichen Grüßen\n"
                    + "Ihr Tafelteam\n");
            mm.sendeMail();
            //Benutzer in Datenbank aktualisieren
            nachricht = "<div class='login erfolg'>Sie erhalten in kürze eine E-Mail mit einem Sicherheitsschlüssel.</div>";

            if (eMail == null) {
                model.addAttribute("eMail", eMailTEMP);
            } else {
                model.addAttribute("eMail", eMail);
            }
            model.addAttribute("nachricht", nachricht);
            return ("passwort-vergessen");
        } catch (Exception e) {
            //Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite Passwort-vergessen (POST) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName() + eMail);
            return ("fehler");

        }
    }

    /**
     * Auf dieser Seite wird der Sicherheitsschlüssel eingeben.
     *
     * @param model
     * @param schluessel
     * @param eMailTEMP
     * @return
     */
    @RequestMapping(value = "/pwSchluessel", method = RequestMethod.GET)
    public String pwSchluessel(Model model, @RequestParam(value = "schluesselMail", required = false) String schluessel, @RequestParam(value = "eMailMail", required = false) String eMailTEMP) {
        model.addAttribute("schluessel", schluessel);
        if (eMail == null) {
            model.addAttribute("eMail", eMailTEMP);
        } else {
            model.addAttribute("eMail", eMail);
        }
        return "passwort-vergessen-schluessel";
    }

    @RequestMapping(value = "/pwSchluessel", method = RequestMethod.POST)
    public String SchluesselPruefung(@RequestParam("eMail") String eMailTEMP, @RequestParam("schluessel") String schluessel, Model model) {
        CBenutzer benutzer = new CBenutzer();
        try {
            benutzer.getBenutzer(eMailTEMP);
        } catch (Exception e) {
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /pwSchluessel (POST) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName() + eMail);
        }
        if (schluessel.equals(CBenutzer.hashPasswort(benutzer.getPasswort()))) {
            schluesselOK = true;
            eMail = eMailTEMP;
            return "redirect:/passwort-vergeben";
        }
        model.addAttribute("nachricht", "<div class='login fehler'>Der Schlüssel und/oder der Benutzername ist falsch.</div>");
        return "passwort-vergessen-schluessel";
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/passwort-vergeben", method = RequestMethod.GET)
    public String NeuesPasswortVergeben(Model model) {
        model.addAttribute("schluesselOK", schluesselOK);
        return "passwort-vergeben";
    }

    /**
     * An diese Seite wird der Sicherheitsschlüssel geschickt.
     *
     * @param model
     * @param passwort
     * @param passwortCheck
     * @return
     */
    @RequestMapping(value = "/passwort-vergeben", method = RequestMethod.POST)
    public String NeuesPasswortErstellen(Model model, @RequestParam("passwort") String passwort, @RequestParam("passwort_check") String passwortCheck) {
        //Accountinformationen laden
        CBenutzer benutzer = new CBenutzer();
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
        try {
            benutzer.getBenutzer(eMail);
        } catch (Exception e) {
            System.out.println("Folgender Fehler ist beim Aufruf der Seite /passwort-vergeben (POST) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName() + eMail);
            System.out.println("Fehler: " + e);
            model.addAttribute("nachricht", "<div class='fehler'>E-Mail nicht gefunden</div>");
            return "passwort-vergessen";
        }

        if (passwort == null ? passwortCheck != null : !passwort.equals(passwortCheck)) {
            model.addAttribute("nachricht", "<div class='fehler'>Die Passwörter stimmen nicht überein</div>");
            model.addAttribute("schluesselOK", schluesselOK);
            model.addAttribute("eMail", eMail);
            return "passwort-vergeben";
        }

        if (schluesselOK == false) {
            return "fehler";
        }
        passwort = CBenutzer.hashPasswort(passwort);
        benutzer.setPasswort(passwort);
        benutzerDAO.createBenutzer(benutzer);
        model.addAttribute("nachricht", "<div class='erfolg'>Das Passwort wurde erfolgreich geändert!</div>");



        return "/login";
    }
}
