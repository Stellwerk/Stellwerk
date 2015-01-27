package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Model.CTafel;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
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
 * Dieser Controller steuert das Verwalten von Tafeln: Erzeugen, Bearbeiten Nur
 * für Admin
 *
 * @author Simon Lau
 */
@Controller
@Secured({"ROLE_ADMIN"})
public class Tafelverwaltung {

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/tafelverwaltung", method = RequestMethod.GET)
    public String tafelverwaltung(Model model) {
        List<CTafel> liste = CTafelDAO.getTafelListe();
        model.addAttribute("tafelanzahl", liste.size());
        model.addAttribute("liste", liste);
        return "benutzerverwaltung/tafelverwaltung"; // Name der JSP/View
    }

    /**
     * Benutzer Details Seite anzeigen
     *
     * @param tafel
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/tafelverwaltung/{tafelID}/tafel-details", method = RequestMethod.GET)
    public String TafelverwaltungDetails(@ModelAttribute("formular") CTafel tafel, @PathVariable("tafelID") int id, Model model) {
        tafel = CTafelDAO.getTafelById(id);
        model.addAttribute("formular", tafel);
        return "benutzerverwaltung/tafel-details";
    }

    /**
     *
     * @param tafel
     * @param result
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/tafelverwaltung/{tafelID}/tafel-details", method = RequestMethod.POST)
    public String TafelverwaltungDetailsVerarbeiten(@Valid @ModelAttribute("formular") CTafel tafel, BindingResult result, @PathVariable("tafelID") int id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formular", tafel);
            return "benutzerverwaltung/tafel-details";
        }
        try {
            CAdresseDAO adressDAO = new CAdresseDAO();
            CKontaktDAO kontaktDAO = new CKontaktDAO();
            CTafelDAO tafelDAO = new CTafelDAO();
            kontaktDAO.createKontakt(tafel.getKontakt());
            adressDAO.createAdresse(tafel.getAdresse());

            model.addAttribute("formular", tafel);
            try {
                tafelDAO.createTafel(tafel);
            } catch (Exception e) {
                System.out.println(e);
                model.addAttribute("nachricht", "<div class='fehler'>Der eingegebene Tafelname existiert bereits!</div>");
                return "benutzerverwaltung/tafel-details";
            }
            model.addAttribute("nachricht", "<div class='erfolg'>Daten erfolgreich aktualisiert!</div>");
            return "benutzerverwaltung/tafel-details";
        } catch (Exception e) {
            return "fehler";
        }
    }

}
