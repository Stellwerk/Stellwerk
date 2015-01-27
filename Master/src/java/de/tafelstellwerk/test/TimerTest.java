package de.tafelstellwerk.test;

import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Model.CSpendenvorgang;
import de.tafelstellwerk.Repository.CSpendenvorgangDAO;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Eyore
 */
@Service
public class TimerTest {

    //Ausführung: Montag bis Freitag jeweils um 00:30 Uhr Systemzeit (Sekunde Minute 	Stunde 	Tag 	Monat 	Wochentag)
    //@Scheduled(cron="30 0 * * 1-5")
    //@Scheduled(cron="0 35 14 * * 1-5")
    public static void main(String[] args) {
        //public void  SpendenvorgaengeAnalysieren() {
        System.out.println("--------------------Starte CronJob: Es werden die Tafeln ermittelt, die nicht innerhalb von 24 Stunden auf eine Spende reagiert haben------------------------");
        List<CSpendenvorgang> liste = CSpendenvorgangDAO.getSpendenvorgangListe();
        CSpendenvorgang spendenvorgang;
        Date zuweisungsDatum;
        Date heutigesDatum = new Date();
        long zeitunterschied;
        int tage;

        CEMail mail = CEMail.createInstance();
        mail.setSender("tafelstellwerk@hs-osnabrueck.de");





        if (liste.isEmpty()) {
            System.out.println("Fehler: Es wurden keine Spendenvorgänge in der Datenbank gefunden!");
        } else {

            for (int i = 0; i < liste.size(); i++) {
                spendenvorgang = liste.get(i);
                zuweisungsDatum = spendenvorgang.getZuweisungszeitpunkt();
                zeitunterschied = heutigesDatum.getTime() - zuweisungsDatum.getTime();
                tage = (int) (zeitunterschied / (1000 * 60 * 60 * 24));

                if (tage > 2) {
                    String empfaenger = liste.get(i).getTafel().getKontakt().geteMail();
                    mail.setEmpfaenger(empfaenger);
                    System.out.println("CronJob: Empfänger " + i + " E-Mail an Empfänger: " + empfaenger);
                    mail.sendeMailausImport(8);

                }
            }
        }
    }
}
