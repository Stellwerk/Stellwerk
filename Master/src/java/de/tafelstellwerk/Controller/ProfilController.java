package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Forms.ProfilFormular;
import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Model.CTafel;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CSpenderDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;

/**
 * Dieser Controller ist für die Verwaltung des Profils
 *
 * @author Simon Lau
 */
@Controller
@RequestMapping(value = "/mein-profil")
@Secured({"ROLE_ADMIN", "ROLE_KOORD", "ROLE_SPENDER", "ROLE_TAFEL"})
public class ProfilController {

    String rolle;

    /**
     * Diese Seite zeigt das eigene Profil an.
     *
     * @param formular
     * @param benutzer
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String ProfilAnzeigen(ProfilFormular formular, CBenutzer benutzer, BindingResult result, Model model) {
        System.out.println("Der Benutzer " + SecurityContextHolder.getContext().getAuthentication().getName() + " versucht die Seite Profil zu öffnen");
        try {
            //Accountinformationen laden
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String eMail = auth.getName();
            benutzer.getBenutzer(eMail);
            benutzer.setPasswort(null);
            rolle = benutzer.getRolle().name();
            //Model bilden
            formular.setBenutzer(benutzer);
            //Prüfung -> Ist Benutzer angehöriger einer Tafel?
            if (benutzer.getTafel() != null) {
                //Prüfung TafelKoord wurde implementiert falls ein Koordinator zusätzlich eine Tafel zugewiesen bekommt. Diese Funktion wurde im Verlauf des Projektes wurde diese Funktion nicht realisiert, wurde aber für evtl. nachstehende Entwicklungen beibehalten.
                if (rolle == "Koordinator") {
                    rolle = "TafelKoord";
                }
                formular.setTafel(benutzer.getTafel());
                formular.setAdresse(benutzer.getTafel().getAdresse());
                formular.setKontakt(benutzer.getTafel().getKontakt());
            } else if (benutzer.getSpender() != null) {
                formular.setAdresse(benutzer.getSpender().getAdresse());
                formular.setKontakt(benutzer.getSpender().getKontakt());
                formular.setSpender(benutzer.getSpender());
            }
            formular.setRolle(rolle);
            if ((rolle.equals("Spender") && benutzer.getSpender() == null)
                    || (rolle == "Tafel" && benutzer.getTafel() == null)
                    || (rolle == "TafelKoord" && benutzer.getTafel() == null)) {
                model.addAttribute("textDeaktiviert", "<div class='fehler'>Ihr Profil wurde noch nicht durch den Administrator freigegeben!</div>");
                model.addAttribute("Profil", formular);
                return "profil_gesperrt";
            }
            model.addAttribute("Profil", formular);
            return "profil";
        } catch (Exception e) {
            // Fehlerbehandlung
            System.out.println("Folgender Fehler ist beim Aufruf der Seite Profil (GET) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
            return "fehler";
        }

    }

    /**
     * An diese Seite wird das eigene Profil nach Bearbeitung geschickt.
     *
     * @param formular
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String ProfilAendern(@Valid @ModelAttribute("Profil") ProfilFormular formular, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println("result.hasErrors()");
            formular.setRolle(rolle);
            model.addAttribute("Profil", formular);
            return "profil";
        } else {
            //Variablen initialisieren
            CBenutzer benutzer = new CBenutzer();
            CTafel tafel = new CTafel();
            CSpender spender = new CSpender();
            CKontakt kontakt = new CKontakt();
            CAdresse adresse = new CAdresse();
            CAdresseDAO adressDAO = new CAdresseDAO();
            CKontaktDAO kontaktDAO = new CKontaktDAO();
            CTafelDAO tafelDAO = new CTafelDAO();
            CSpenderDAO spenderDAO = new CSpenderDAO();
            CBenutzerDAO benutzerDAO = new CBenutzerDAO();
            try {
                //Accountinformationen laden
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String eMail = auth.getName();
                benutzer.getBenutzer(eMail);
                formular.setRolle(rolle);
                //Passwortkontrolle
                //Das auf dem Formular eingegebene Passwort zur Bestätigung der Identität wird in einen MD5-Hash umgewandelt und mit dem Datenbankpasswort (MD5-Verschlüsselung) verglichen.
                if (CBenutzer.hashPasswort(formular.getPasswortkontrolle()).equals(benutzer.getPasswort())) {
                    //Accountinformation aus dem Formular in das Objekt Benutzer übernehmen
                    //Prüfung -> Ist Benutzer Angehöriger einer Tafel oder Angehöriger eines Spenders?
                    //-> Dann entsprechende Adress-, Kontakt- und Firmen- bzw. Tafeldaten in das Objekt Benutzer übernehmen

                    if (benutzer.getTafel() != null) {
                        //Adressänderungen-------------------
                        if ((!benutzer.getTafel().getAdresse().getBeschreibung().equals(formular.getAdresse().getBeschreibung()))
                                || (!benutzer.getTafel().getAdresse().getOrt().equals(formular.getAdresse().getOrt()))
                                || (!benutzer.getTafel().getAdresse().getPlz().equals(formular.getAdresse().getPlz()))
                                || (!benutzer.getTafel().getAdresse().getStrasse().equals(formular.getAdresse().getStrasse()))) {
                            tafel = benutzer.getTafel();
                            adresse = formular.getAdresse();
                            adresse.setAdressID(0);
                            adressDAO.createAdresse(adresse);
                            tafel.setAdresse(adresse);
                            tafelDAO.createTafel(tafel);
                            benutzer.setTafel(tafel);
                        }
                        //--------------------------------------
                        //Kontaktänderungen
                        if ((!benutzer.getTafel().getKontakt().geteMail().equals(formular.getKontakt().geteMail()))
                                || (!benutzer.getTafel().getKontakt().getFax().equals(formular.getKontakt().getFax()))
                                || (!benutzer.getTafel().getKontakt().getNachname().equals(formular.getKontakt().getNachname()))
                                || (!benutzer.getTafel().getKontakt().getTelefon().equals(formular.getKontakt().getTelefon()))
                                || (!benutzer.getTafel().getKontakt().getVorname().equals(formular.getKontakt().getVorname()))) {

                            tafel = benutzer.getTafel();
                            kontakt = formular.getKontakt();
                            tafel.setKontakt(kontakt);
                            benutzer.setTafel(tafel);
                            kontaktDAO.createKontakt(kontakt);
                            tafelDAO.createTafel(tafel);
                        }
                        //---------------------------------------
                        //Tafeländerungen
                        if ((!benutzer.getTafel().getOeffnungszeiten().equals(formular.getTafel().getOeffnungszeiten()))
                                || (!benutzer.getTafel().getTransporter().equals(formular.getTafel().getTransporter()))) {
                            tafel = benutzer.getTafel();
                            tafel.setOeffnungszeiten(formular.getTafel().getOeffnungszeiten());
                            tafel.setTransporter(formular.getTafel().getTransporter());
                            benutzer.setTafel(tafel);
                            tafelDAO.createTafel(tafel);
                        }
                        //---------------------------------------
                    } else if (benutzer.getSpender() != null) {
                        //Adressänderungen-------------------
                        if ((!benutzer.getSpender().getAdresse().getBeschreibung().equals(formular.getAdresse().getBeschreibung()))
                                || (!benutzer.getSpender().getAdresse().getOrt().equals(formular.getAdresse().getOrt()))
                                || (!benutzer.getSpender().getAdresse().getPlz().equals(formular.getAdresse().getPlz()))
                                || (!benutzer.getSpender().getAdresse().getStrasse().equals(formular.getAdresse().getStrasse()))) {
                            spender = benutzer.getSpender();
                            adresse = formular.getAdresse();
                            adresse.setAdressID(0);
                            adressDAO.createAdresse(adresse);
                            spender.setAdresse(adresse);
                            benutzer.setSpender(spender);
                            spenderDAO.createSpender(spender);
                        }
                        //--------------------------------------
                        //Kontaktänderungen
                        if ((!benutzer.getSpender().getKontakt().geteMail().equals(formular.getKontakt().geteMail()))
                                || (!benutzer.getSpender().getKontakt().getFax().equals(formular.getKontakt().getFax()))
                                || (!benutzer.getSpender().getKontakt().getNachname().equals(formular.getKontakt().getNachname()))
                                || (!benutzer.getSpender().getKontakt().getTelefon().equals(formular.getKontakt().getTelefon()))
                                || (!benutzer.getSpender().getKontakt().getVorname().equals(formular.getKontakt().getVorname()))) {
                            spender = benutzer.getSpender();
                            kontakt = formular.getKontakt();
                            //kontakt.setKontaktID(0);
                            kontaktDAO.createKontakt(kontakt);
                            spender.setKontakt(kontakt);
                            benutzer.setSpender(spender);
                        }
                        //---------------------------------------
                        //Spenderänderungen
                        if ((!benutzer.getSpender().getOeffnungszeiten().equals(formular.getSpender().getOeffnungszeiten()))
                                || (!benutzer.getSpender().getSicherheitsbestimmungen().equals(formular.getSpender().getSicherheitsbestimmungen()))
                                || (!benutzer.getSpender().getFirmenname().equals(formular.getSpender().getFirmenname()))) {
                            spender = benutzer.getSpender();
                            spender.setSicherheitsbestimmungen(formular.getSpender().getSicherheitsbestimmungen());
                            spender.setFirmenname(formular.getSpender().getFirmenname());
                            spender.setOeffnungszeiten(formular.getSpender().getOeffnungszeiten());
                            benutzer.setSpender(spender);
                            spenderDAO.createSpender(spender);
                        }
                        //---------------------------------------
                    }
                    //Benutzeränderungen
                    //Soll das Passwort geändert werden?
                    //Prüfung Felder "Neues Passwort" und "Passwort wiederholen" stimmen überein und sind nicht leer
                    if (formular.getNeuesPasswort().equals(formular.getBestNeuesPasswort()) && !"".equals(formular.getNeuesPasswort())) {
                        benutzer.setPasswort(CBenutzer.hashPasswort(formular.getNeuesPasswort()));
                    } //Die Felder "Neues Passwort und "Passwort sind nicht leer, stimmen aber nicht überein
                    else if (!formular.getNeuesPasswort().equals(formular.getBestNeuesPasswort())
                            && (!"".equals(formular.getNeuesPasswort())
                            || !"".equals(formular.getBestNeuesPasswort()))) {
                        // Fehlernachricht und Aufrufung des View
                        String nachricht = "<div class='fehler'>Das neue Passwort stimmt nicht überein!</div>";
                        formular.setNachricht(nachricht);
                        model.addAttribute("Profil", formular);
                        return "profil";
                    }
                    // Datenbankeintrag erzeugen
                    System.out.println(benutzer.toString());
                    benutzerDAO.createBenutzer(benutzer);

                    // Erfolgsnachricht und Aufrufung des View
                    String nachricht = "<div class='erfolg'>Daten erfolgreich aktualisiert</div>";
                    formular.setNachricht(nachricht);
                    model.addAttribute("Profil", formular);
                    return "profil";
                } //Passwort wurde falsch eingegeben
                else if (!CBenutzer.hashPasswort(formular.getPasswortkontrolle()).equals(benutzer.getPasswort())) {
                    // Fehlernachricht und Aufrufung des View
                    String nachricht = "<div class='fehler'>Das Passwort zur Bestätigung wurde falsch eingegeben</div>";
                    formular.setNachricht(nachricht);
                    model.addAttribute("Profil", formular);
                    return "profil";
                } else {
                    return "fehler";
                }
            } catch (Exception e) {
                // Fehlerbehandlung
                System.out.println("Folgender Fehler ist beim Aufruf der Seite Profil (POST) aufgetreten:" + "\n" + e + "\n" + "Der Fehler wurde durch folgenden Benutzer hervorgerufen: " + SecurityContextHolder.getContext().getAuthentication().getName());
                System.out.println("Fehler: " + e);
                return "fehler";

            }
        }
    }
}
