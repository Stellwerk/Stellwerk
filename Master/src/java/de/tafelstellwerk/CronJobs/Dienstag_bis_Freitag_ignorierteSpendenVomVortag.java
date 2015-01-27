package de.tafelstellwerk.CronJobs;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CSpendenvorgang;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CSpendenvorgangDAO;
import de.tafelstellwerk.Threads.TMailCronJob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * CronJob für das Senden von E-Mails, bei ignorierten Spenden von Dienstag bis
 * Freitag
 *
 * @author Simon Lau
 */

@Service
public class Dienstag_bis_Freitag_ignorierteSpendenVomVortag {

    /**
     * Ausführung: Dienstag - Freitag um 01:30 Uhr
     * Dienstag - Überprüfe letzten
     * Freitag
     */
    @Scheduled(cron = "0 30 1 * * 2-5") //(Sekunde Minute Stunde Tag Monat Wochentag)
    public void CronJobEmailSendenDienstag() {

        System.out.println("-------------------Starte CronJob 'CronJobEmailSendenDienstag'-------------------");
        System.out.println("-------------------Dienstag - Freitag 01:30 Uhr - überprüfe letzten Donnerstag-------------------");
        System.out.println("Es werden die Tafeln ermittelt, die nicht innerhalb von 24 Stunden auf eine Spende reagiert haben");
        List<CSpendenvorgang> listeSpendenvorgang = CSpendenvorgangDAO.getSpendenvorgangListe();
        List<CBenutzer> listeKoordinator = CBenutzerDAO.getBenutzerListeByRolle(CBenutzer.RolleEnum.Koordinator);
        Date zuweisungsDatum;
        Date heutigesDatum = new Date();
        long zeitunterschied;
        int tage;
        List<CSpendenvorgang> listeSvIgno = new ArrayList<>();
        Runnable mailThread;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if (listeKoordinator.isEmpty()) {
            System.out.println("Fehler: Es wurden keine Spendenvorgänge in der Datenbank gefunden!");
        }
        if (listeSpendenvorgang.isEmpty()) {
            System.out.println("Fehler: Es wurden keine Spendenvorgänge in der Datenbank gefunden!");
        } else {
            // Alle Spendenvorgänge mit dem Status "ignoriert" filtern
            for (int i = 0; i < listeSpendenvorgang.size(); i++) {
                zuweisungsDatum = listeSpendenvorgang.get(i).getZuweisungszeitpunkt();
                zeitunterschied = heutigesDatum.getTime() - zuweisungsDatum.getTime();
                tage = (int) (zeitunterschied / (1000 * 60 * 60 * 24));

                if (listeSpendenvorgang.get(i).getStatus() == CSpendenvorgang.StatusEnum.ignoriert && tage > 1) {
                    listeSvIgno.add(listeSpendenvorgang.get(i));
                }
            }
            //Gefilterte Spendenvorgänge überprüfen - E-Mail an Koordinator und Tafel senden, wenn der Zuweisungszeitpunkt letzten Freitag war
            for (int i = 0; i < listeSvIgno.size(); i++) {
                //Gefilterte Spendenvorgänge überprüfen - E-Mail an Tafel un
                if (listeSvIgno.get(i).getZuweisungszeitpunkt().getTime() == (cal.getTimeInMillis() - (86400000 * 1))) {
                    String empfaenger = listeSpendenvorgang.get(i).getTafel().getKontakt().geteMail();
                    String betreff = "Tafelstellwerk - Erinnerung: " + listeSpendenvorgang.get(i).getSpende().getProduktbezeichnung();

                    //E-Mail an Tafel
                    System.out.println("CronJob: Empfänger " + i + " E-Mail an Empfänger: " + empfaenger + " Betreff: " + betreff);
                    mailThread = new TMailCronJob(empfaenger, betreff, 9); // 8- Erinnerung an Tafel
                    new Thread(mailThread).start();
                }
            }
        }
        System.out.println("-------------------CronJob beendet-------------------");
    }
}
