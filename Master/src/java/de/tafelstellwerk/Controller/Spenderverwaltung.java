package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CSpenderDAO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Dieser Controller steuert das Verwalten von Spendern: Erzeugen, Bearbeiten
 * Nur für Admin
 *
 * @author Eyore
 */
@Controller
@Secured({"ROLE_ADMIN"})
public class Spenderverwaltung {

    /**
     * Erzeugt die Spenderverwaltungsseite
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/spenderverwaltung", method = RequestMethod.GET)
    public String spenderverwaltung(Model model) {

        List<CSpender> liste = CSpenderDAO.getSpenderListe();
        model.addAttribute("spenderanzahl", liste.size());
        model.addAttribute("liste", liste);
        return "benutzerverwaltung/spenderverwaltung"; // Name der JSP/View
    }

    /**
     * Benutzerdetails Seite anzeigen (mit Bearbeitungsmöglichkeit)
     *
     * @param spender
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/spenderverwaltung/{spenderID}/spender-details", method = RequestMethod.GET)
    public String SpenderverwaltungDetails(@ModelAttribute("formular") CSpender spender, @PathVariable("spenderID") int id, Model model) {

        spender = CSpenderDAO.getSpenderById(id);
        model.addAttribute("formular", spender);


        return "benutzerverwaltung/spender-details";
    }

    /**
     * An diese Seite werden die bearbeiteten Benutzerdetails geschickt.
     *
     * @param spender
     * @param result
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/spenderverwaltung/{spenderID}/spender-details", method = RequestMethod.POST)
    public String SpenderverwaltungDetailsVerarbeiten(@Valid @ModelAttribute("formular") CSpender spender, BindingResult result, @PathVariable("spenderID") int id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formular", spender);
            return "benutzerverwaltung/spender-details";
        }
        try {
            CAdresseDAO adressDAO = new CAdresseDAO();
            CKontaktDAO kontaktDAO = new CKontaktDAO();
            CSpenderDAO spenderDAO = new CSpenderDAO();
            kontaktDAO.createKontakt(spender.getKontakt());
            adressDAO.createAdresse(spender.getAdresse());

            model.addAttribute("formular", spender);
            try {
                spenderDAO.createSpender(spender);
            } catch (Exception e) {
                System.out.println(e);
                model.addAttribute("nachricht", "<div class='fehler'>Der eingegebene Spender existiert bereits!</div>");
                return "benutzerverwaltung/spender-details";
            }
            model.addAttribute("nachricht", "<div class='erfolg'>Daten erfolgreich aktualisiert!</div>");
            return "benutzerverwaltung/spender-details";
        } catch (Exception e) {
            return "/fehler";
        }

    }
}
