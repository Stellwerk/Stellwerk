//NSA - NOT SECURE AUTHENTIFICATION
package de.tafelstellwerk.Controller;

import de.tafelstellwerk.Model.CAnmeldungen;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Dieser Controller überwacht die Anmeldungen um z.B. Bruteforce zu verhindern
 * wird die Anmeldung nach 5 falschen Anmeldeversuchen für 5 Minuten
 * deaktiviert.
 *
 * @author Simon Lau
 */
@Controller
@Component
public class NSAController implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    //Erstellen einer System-Liste zur Überwachung der Fehlgeschlagenen Login-Versuche

    List<CAnmeldungen> listeAnmdeldungen = new ArrayList<>();

    /**
     * Event: Der Benutzer hat einen falschen Benutzernamen oder ein falsches
     * Passwort eingegeben
     *
     * @param ev
     */
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent ev) {
        //Initialisieren der benötigten Objekte
        String benutzername = ev.getAuthentication().getName();
        java.util.Date date = new java.util.Date();
        long timestamp = date.getTime();
        boolean gefunden = false;
        boolean benutzerVorhanden = false;
        System.out.println(benutzername + " hat das falsche Passwort beim Login eingegeben.");
        CBenutzer benutzer = new CBenutzer();
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();


        //Überprüfung ob der Benutzer ein registriertes Mitglied ist
        try {
            benutzer = benutzerDAO.getBenutzerByEMail(benutzername);
            benutzerVorhanden = true;
        } catch (Exception e) {
            System.out.println("Es wurde versucht, sich mit folgender (nicht vorhandener E-Mailadresse einzulogen: " + benutzername);
            System.out.println(e);
            benutzerVorhanden = false;
        }

        //Die folgenden Anweisungen werden nur ausgeführt wenn der Benutzer ein registriertes Mitglied ist.
        if (benutzerVorhanden == true) {
            //Es wird der erste Eintrag in die Liste geschrieben
            if (listeAnmdeldungen == null || listeAnmdeldungen.isEmpty()) {
                System.out.println("Fehler: Keine Liste der fehlgeschlagenen Anmeldungen vorhanden. Liste wird erstellt.");
                CAnmeldungen anmeldungen = new CAnmeldungen();
                anmeldungen.seteMail(benutzername);
                anmeldungen.setTimestamp(timestamp);
                anmeldungen.setFehlgeschlageneAnmeldungsVersuche(1);
                listeAnmdeldungen.add(anmeldungen);

            } else {
                //Die Liste wird mit einer For-Schleife durchlaufen um zu überprüfen, ob der Benutzer bereits in der Liste steht
                for (int i = 0; i < listeAnmdeldungen.size(); i++) {
                    if (listeAnmdeldungen.get(i).geteMail().equals(benutzername)) {
                        //Wenn der Benutzer gefunden wurde werden folgende Anweisungen ausgeführt
                        //Anzahl der gescheiterten Anmeldungsversuche um 1 erhöhen
                        gefunden = true;
                        CAnmeldungen anmeldungen = new CAnmeldungen();
                        anmeldungen.setFehlgeschlageneAnmeldungsVersuche((listeAnmdeldungen.get(i).getFehlgeschlageneAnmeldungsVersuche()) + 1);
                        System.out.println("Der Benutzer: " + benutzername + " konnte sich aufgrund falscher Eingaben nicht einlogen. " + listeAnmdeldungen.get(i).getFehlgeschlageneAnmeldungsVersuche() + ". Versuch");
                        anmeldungen.seteMail(benutzername);

                        //wenn die Anzahl der gescheiterten CAnmeldungen < 5 ist werden wird nur die Anmeldung um 1 erhöht
                        if (listeAnmdeldungen.get(i).getFehlgeschlageneAnmeldungsVersuche() < 5) {
                            anmeldungen.setTimestamp(timestamp);
                            // Wenn der User sich zum 5. mal falsch einlogt, wird er in der Datenbank deaktiviert  und in der Liste die Variable "Gesperrt5m" auf true gesetzt.
                        } else if (listeAnmdeldungen.get(i).getFehlgeschlageneAnmeldungsVersuche() == 5) {
                            System.out.println("Anmeldung gesperrt: Der Benutzer " + benutzername + " wird für 5 Minuten gesperrt!");
                            anmeldungen.setGesperrt5m(true);
                            anmeldungen.setTimestamp(timestamp);
                            benutzer.setAktiviert(Boolean.FALSE);
                            benutzerDAO.createBenutzer(benutzer);
                        }
                        //Aktualisierung des Listenelements
                        listeAnmdeldungen.set(i, anmeldungen);
                    }
                }
                //Wenn der benutzer nicht in der Liste gefunden wurde wird ein neuer Eintrag erzeugt
                if (gefunden == false) {
                    System.out.println("Der Benutzer: " + benutzername + " konnte sich aufgrund falscher Eingaben nicht einlogen. 1. Versuch");
                    CAnmeldungen anmeldungen = new CAnmeldungen();
                    anmeldungen.seteMail(benutzername);
                    anmeldungen.setTimestamp(timestamp);
                    anmeldungen.setFehlgeschlageneAnmeldungsVersuche(1);
                    listeAnmdeldungen.add(anmeldungen);
                }
            }
        }
    }

    /**
     * Cronjob zum Freischalten der Anmeldungen
     */
    @Scheduled(cron = "0 */5 * * * *") //(Sekunde Minute Stunde Tag Monat Wochentag)
    public void AnmeldungenFreischalten() {


        if (listeAnmdeldungen != null || !listeAnmdeldungen.isEmpty()) {
            java.util.Date date = new java.util.Date();
            long timestamp = date.getTime();

            for (int i = 0; i < listeAnmdeldungen.size(); i++) {

                if (listeAnmdeldungen.get(i).isGesperrt5m()) {
                    if ((timestamp - listeAnmdeldungen.get(i).getTimestamp()) >= 300000) {

                        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
                        CBenutzer benutzer;
                        benutzer = benutzerDAO.getBenutzerByEMail(listeAnmdeldungen.get(i).geteMail());
                        benutzer.setAktiviert(Boolean.TRUE);
                        benutzerDAO.createBenutzer(benutzer);
                        listeAnmdeldungen.remove(i);
                        System.out.println("Anmeldung freigeschaltet: Der Benutzer " + benutzer.getEMail() + " wurde wieder freigeschaltet!");
                    }
                } else if ((timestamp - listeAnmdeldungen.get(i).getTimestamp()) >= 300000) {
                    listeAnmdeldungen.remove(i);
                }
            }
        }
    }
}