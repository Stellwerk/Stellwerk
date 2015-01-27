package de.tafelstellwerk.test;

import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CAllgemeines;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMailVorlage;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Model.CSpende;
import de.tafelstellwerk.Model.CSpendenvorgang;
import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Model.CTafel;
import de.tafelstellwerk.Repository.CAdresseDAO;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;
import de.tafelstellwerk.Repository.CBenutzerDAO;
import de.tafelstellwerk.Repository.CEMailVorlageDAO;
import de.tafelstellwerk.Repository.CKontaktDAO;
import de.tafelstellwerk.Repository.CSpendeDAO;
import de.tafelstellwerk.Repository.CSpendenvorgangDAO;
import de.tafelstellwerk.Repository.CSpenderDAO;
import de.tafelstellwerk.Repository.CTafelDAO;
import java.util.Date;

/**
 * Klasse zum Befüllen der Datenbank mit Testdaten
 *
 * @author gpaschke
 */
public class AddDBReset {

    public static void main(String[] args) {

        //Adressen: Tafeln
        CAdresseDAO AdresseDAO = new CAdresseDAO();                             //Objekt für DB Verbindung
        CAdresse Adresse = new CAdresse(0, "Unterstr. 9", "28832", "Achim", ""); //Objekt lokal erstellt
        AdresseDAO.createAdresse(Adresse);                                      //und in die DB einfügen
        CAdresse Adresse1 = new CAdresse(0, "Meistersingerweg 30", "30890", "Barsinghausen", "");
        AdresseDAO.createAdresse(Adresse1);
        CAdresse Adresse2 = new CAdresse(0, "Marktstraße 5", "38700", "Braunlage", "");
        AdresseDAO.createAdresse(Adresse2);
        CAdresse Adresse3 = new CAdresse(0, "Goslarsche Straße 93", "38118", "Braunschweig", "");
        AdresseDAO.createAdresse(Adresse3);
        CAdresse Adresse4 = new CAdresse(0, "Brauerstraße 13", "28309", "Bremen", "");
        AdresseDAO.createAdresse(Adresse4);
        CAdresse Adresse5 = new CAdresse(0, "Surfeldstrasse 29", "27568", "Bremerhaven", "");
        AdresseDAO.createAdresse(Adresse5);
        CAdresse Adresse6 = new CAdresse(0, "Dammstraße 11", "31675", "Bückeburg", "");
        AdresseDAO.createAdresse(Adresse6);
        CAdresse Adresse7 = new CAdresse(0, "Wallgartenstarsse 18a", "31303", "Burgdorf", "");
        AdresseDAO.createAdresse(Adresse7);
        CAdresse Adresse8 = new CAdresse(0, "Gartenstr. 8", "30938", "Burgwedel", "");
        AdresseDAO.createAdresse(Adresse8);
        CAdresse Adresse9 = new CAdresse(0, "Leinstraße 16", "31582", "Nienburg", "");
        AdresseDAO.createAdresse(Adresse9);
        CAdresse Adresse10 = new CAdresse(0, "Cheruskerring 53", "31137", "Hildesheim", "");
        AdresseDAO.createAdresse(Adresse10);
        CAdresse Adresse11 = new CAdresse(0, "Marienburger Str. 1", "29633", "Munster", "Büro: Kronsbeerweg 2, 29633 Munster");
        AdresseDAO.createAdresse(Adresse11);
        CAdresse Adresse12 = new CAdresse(0, "Alte Poststraße 3", "27404", "Zeven", "");
        AdresseDAO.createAdresse(Adresse12);
        CAdresse Adresse13 = new CAdresse(0, "Großstr. 42 - 46", "26789", "Leer", "");
        AdresseDAO.createAdresse(Adresse13);
        CAdresse Adresse14 = new CAdresse(0, "Unterbachstraße 29", "37603", "Holzminden", "");
        AdresseDAO.createAdresse(Adresse14);
        CAdresse Adresse15 = new CAdresse(0, "Jacobikirchhof 1", "37073", "Göttingen", "");
        AdresseDAO.createAdresse(Adresse15);
        CAdresse Adresse16 = new CAdresse(0, "Langschmidtsweg 17a", "49808", "Lingen", "");  
        AdresseDAO.createAdresse(Adresse16);
        
        //Adressen: Spender
        CAdresse Adresse17 = new CAdresse(0, "Zum Attersee 2", "49076", "Osnabrück", "");
        AdresseDAO.createAdresse(Adresse17);
        CAdresse Adresse18 = new CAdresse(0, "Bonifatiusstraße 305", "48432", "Rheine", "");
        AdresseDAO.createAdresse(Adresse18);
        CAdresse Adresse19 = new CAdresse(0, "Brunnenstraße 37", "26789", "Leer/Ostfriesland", "");
        AdresseDAO.createAdresse(Adresse19);
        CAdresse Adresse20 = new CAdresse(0, "Am Seitenkanal 1", "49811", "Lingen", "");
        AdresseDAO.createAdresse(Adresse20);
        CAdresse Adresse21 = new CAdresse(0, "Lindenstraße 2", "49808", "Lingen", "Büro: Rheiner Str. 111A, 49809 Lingen");
        AdresseDAO.createAdresse(Adresse21);
        
        //Kontakte: Tafeln
        CKontaktDAO KontaktDAO = new CKontaktDAO();
        CKontakt Kontakt = new CKontakt(0, "Büro", "Büro", "niemann.patrick@gmail.com", "04202-953176", "");
        KontaktDAO.createKontakt(Kontakt);
        CKontakt Kontakt1 = new CKontakt(0, "Peter", "Radike", "niemann.patrick@gmail.com", "01608863298", "");
        KontaktDAO.createKontakt(Kontakt1);
        CKontakt Kontakt2 = new CKontakt(0, "Frau", "Holze-Schüler", "niemann.patrick@gmail.com", "05520/93020", "05520/92251");
        KontaktDAO.createKontakt(Kontakt2);
        CKontakt Kontakt3 = new CKontakt(0, "Gerhard", "Stier-Friedland", "niemann.patrick@gmail.com", "0531 - 302040", "0531 - 302094");
        KontaktDAO.createKontakt(Kontakt3);
        CKontakt Kontakt4 = new CKontakt(0, "Wilfred", "Runge", "niemann.patrick@gmail.com", "0421 / 4341959", "0421/ 9607965");
        KontaktDAO.createKontakt(Kontakt4);
        CKontakt Kontakt5 = new CKontakt(0, "Eva-Maria", "Wagner", "niemann.patrick@gmail.com", "0160-99895265", "0471/9818371");
        KontaktDAO.createKontakt(Kontakt5);
        CKontakt Kontakt6 = new CKontakt(0, "Frau", "Gerstenberg", "niemann.patrick@gmail.com", "05722 905758", "05722 905760");
        KontaktDAO.createKontakt(Kontakt6);
        CKontakt Kontakt7 = new CKontakt(0, "Wolfgang", "Bock", "niemann.patrick@gmail.com", "0152-09907117", "051367970954");
        KontaktDAO.createKontakt(Kontakt7);
        CKontakt Kontakt8 = new CKontakt(0, "Gerd", "Duckstein", "niemann.patrick@gmail.com", "0172 / 5117550", "");
        KontaktDAO.createKontakt(Kontakt8);
        CKontakt Kontakt9 = new CKontakt(0, "Beate", "Kiehl", "niemann.patrick@gmail.com", "0157-74706412", "05021/916730");
        KontaktDAO.createKontakt(Kontakt9);
        CKontakt Kontakt10 = new CKontakt(0, "Doreen", "Kreykenbohm", "niemann.patrick@gmail.com", "05121-2984821", "05121-2984823");
        KontaktDAO.createKontakt(Kontakt10);
        CKontakt Kontakt11 = new CKontakt(0, "Jörg", "Weydling", "niemann.patrick@gmail.com", "0172-1742433", "");
        KontaktDAO.createKontakt(Kontakt11);
        CKontakt Kontakt12 = new CKontakt(0, "Norbert", "Wolf", "niemann.patrick@gmail.com", "04761 - 92 4567", "");
        KontaktDAO.createKontakt(Kontakt12);
        CKontakt Kontakt13 = new CKontakt(0, "Lothar", "Ortmann", "niemann.patrick@gmail.com", "0175-8529471", "0491-92779-18");
        KontaktDAO.createKontakt(Kontakt13);
        CKontakt Kontakt14 = new CKontakt(0, "Uwe", "Uecker", "niemann.patrick@gmail.com", "016090215703", "55317047006");
        KontaktDAO.createKontakt(Kontakt14);
        CKontakt Kontakt15 = new CKontakt(0, "Carsten", "Hinrichs", "niemann.patrick@gmail.com", "0551 51030", "0551 51085");
        KontaktDAO.createKontakt(Kontakt15);
        CKontakt Kontakt16 = new CKontakt(0, "Elvira", "Enders", "niemann.patrick@gmail.com", "0591 831666", "0591 9777983");
        KontaktDAO.createKontakt(Kontakt16);
        
        //Kontakte: Spender
        CKontakt Kontakt17 = new CKontakt(0, "Martin", "Möllmann", "niemann.patrick@gmail.com", "+49 5 41/91 62-0", "+49 5 41/91 62-355");
        KontaktDAO.createKontakt(Kontakt17);
        CKontakt Kontakt18 = new CKontakt(0, "Diane", "Bitzel", "niemann.patrick@gmail.com", "(05971) 799-0", "(05971) 799-9350");
        KontaktDAO.createKontakt(Kontakt18);
        CKontakt Kontakt19 = new CKontakt(0, "Raimund", "Mecke", "niemann.patrick@gmail.com", "0491-808-0", "0491-808-519");
        KontaktDAO.createKontakt(Kontakt19);
        CKontakt Kontakt20 = new CKontakt(0, "Elke", "Kohnen", "niemann.patrick@gmail.com", "+49 591 9145 0", "");
        KontaktDAO.createKontakt(Kontakt20);
        CKontakt Kontakt21 = new CKontakt(0, "Chocolate", "Bros", "niemann.patrick@gmail.com", "0591 40699820", "");
        KontaktDAO.createKontakt(Kontakt21);

        //Tafeln
        CTafelDAO TafelDAO = new CTafelDAO();
        CTafel Tafel = new CTafel(0, "Achimer Tafel e.V.", "2 VW Crafter Kühlwagen", "Bürozeiten: Mo 9.30 bis 13.00 Uhr, Di bis Fr 9.30 bis 15.30 Uhr, sonst AB", null, CTafel.StatusEnum.grün, Adresse, Kontakt);
        TafelDAO.createTafel(Tafel);
        CTafel Tafel1 = new CTafel(0, "Barsinghäuser Tafel", "2 Kühlfahrzeuge, Ford Transit kl. und groß", "", null, CTafel.StatusEnum.grün, Adresse1, Kontakt1);
        TafelDAO.createTafel(Tafel1);
        CTafel Tafel2 = new CTafel(0, "Braunlager Tafel", "1 Ford Fahrzeug mit Kühlschrank", "Dienstag & Freitag", null, CTafel.StatusEnum.grün, Adresse2, Kontakt2);
        TafelDAO.createTafel(Tafel2);
        CTafel Tafel3 = new CTafel(0, "Braunschweiger Tafel e.V.", "1 Mercedes Kühlfahrzeug, 1 Mercedes Transporter, 1 VW Fox", "Bürozeiten: tgl. 8.00 Uhr bis 13.30 Uhr, sonst AB", null, CTafel.StatusEnum.grün, Adresse3, Kontakt3);
        TafelDAO.createTafel(Tafel3);
        CTafel Tafel4 = new CTafel(0, "Bremer Tafel e.V.", "6 Kühlfahrzeuge, 1 Anhänger", "Bürozeiten: Mo - Fr, 08.00 Uhr bis 16.00 Uhr", null, CTafel.StatusEnum.grün, Adresse4, Kontakt4);
        TafelDAO.createTafel(Tafel4);
        CTafel Tafel5 = new CTafel(0, "Bremerhavener Tafel", "1 Mercedes Kühlfahrzeug, eckig", "Bürozeiten: Mo-Fr.8:00 bis 14:00Uhr", null, CTafel.StatusEnum.grün, Adresse5, Kontakt5);
        TafelDAO.createTafel(Tafel5);
        CTafel Tafel6 = new CTafel(0, "Bückeburger Tafel e.V.", "2 Mercedes Kühlfahrzeuge", "Bürozeiten: Mo. Mi. Fr. von 8:00 bis 17:00", null, CTafel.StatusEnum.grün, Adresse6, Kontakt6);
        TafelDAO.createTafel(Tafel6);
        CTafel Tafel7 = new CTafel(0, "Burgdorfer Tafel e.V.", "VW Bus, VW Kühlfahrzeug", "Bürozeiten: tgl. 16:00 - 19:00 Uhr Mobil", null, CTafel.StatusEnum.grün, Adresse7, Kontakt7);
        TafelDAO.createTafel(Tafel7);
        CTafel Tafel8 = new CTafel(0, "Burgwedeler Tafel e.V.", "", "Bürozeiten: Mittwoch 10.00 Uhr bis 13.00 Uhr", null, CTafel.StatusEnum.grün, Adresse8, Kontakt8);
        TafelDAO.createTafel(Tafel8);
        CTafel Tafel9 = new CTafel(0, "Göttinger Tafel e.V.", "1 T5, 1 Merc. Kühlwagen, 1 VW Caddy", "Bürozeiten: Tagl. 9:00 - 15:00 Uhr", null, CTafel.StatusEnum.grün, Adresse15, Kontakt15);
        TafelDAO.createTafel(Tafel9);
        CTafel Tafel10 = new CTafel(0, "Hildesheimer Tafel e.V.", "2 Kühlfahrzeuge (VW T 5 und VW Crafter)", "Bürozeiten: tägl. 08:00 - 14:00 Uhr; sonst AB", null, CTafel.StatusEnum.grün, Adresse10, Kontakt10);
        TafelDAO.createTafel(Tafel10);
        CTafel Tafel11 = new CTafel(0, "Holzmindener Tafel e. V.", "2 Citroen Kastenfahrzeuge, klein", "Bürozeiten: tgl. 9.00 Uhr bis 12.00 Uhr sonst Email", null, CTafel.StatusEnum.grün, Adresse14, Kontakt14);
        TafelDAO.createTafel(Tafel11);
        CTafel Tafel12 = new CTafel(0, "Leeraner Tafel", "Mercedes Sprinter m. Kühlkoffer", "Bürozeiten: tgl. 9.00 Uhr bis 16.00 Uhr", null, CTafel.StatusEnum.grün, Adresse13, Kontakt13);
        TafelDAO.createTafel(Tafel12);
        CTafel Tafel13 = new CTafel(0, "Lingener Tafel e. V.", "3 Mercedes -Benz Sprinter mit Kühlausbau", "Bürozeiten: Mo - Fr 08:00 - 17:00 Uhr, Sa 12:00 - 16:00 Uhr", null, CTafel.StatusEnum.grün, Adresse16, Kontakt16);
        TafelDAO.createTafel(Tafel13);
        CTafel Tafel14 = new CTafel(0, "Munsteraner Tafel", "1 VW-Caddy mit Kühlbox", "no limit", null, CTafel.StatusEnum.grün, Adresse11, Kontakt11);
        TafelDAO.createTafel(Tafel14);
        CTafel Tafel15 = new CTafel(0, "Nienburger Tafel", "2 Mercedes Kühlfahrzeuge, eckig", "Bürozeiten: tgl. 9.00 Uhr bis 16.00 Uhr sonst AB", null, CTafel.StatusEnum.grün, Adresse9, Kontakt9);
        TafelDAO.createTafel(Tafel15);
        CTafel Tafel16 = new CTafel(0, "Zevener Tafel", "1 Ford Kühl Klein Lkw, 1 VW  Kühl Caddy", "", null, CTafel.StatusEnum.grün, Adresse12, Kontakt12);
        TafelDAO.createTafel(Tafel16);
                
        //Spender
        CSpenderDAO SpenderDAO = new CSpenderDAO();
        CSpender Spender = new CSpender(0, "Advanced Nuclear Fuels GmbH", "Strahlenschutzanzug & Geigerzähler", "Mo. - Fr.: 6.00 - 20.00 Uhr", "", CSpender.StatusEnum.grün, Adresse20, Kontakt20);
        SpenderDAO.createSpender(Spender);
        CSpender Spender1 = new CSpender(0, "apetito AG", "Schutzkleidung", "Mo. - Fr.: 07.00 - 17.00 Uhr", "linkes Tor", CSpender.StatusEnum.grün, Adresse18, Kontakt18);
        SpenderDAO.createSpender(Spender1);
        CSpender Spender2 = new CSpender(0, "Coppenrath & Wiese GmbH & Co. KG", "Schutzkleidung", "Mo. - Fr.: 06.00 - 18.00 Uhr", "", CSpender.StatusEnum.grün, Adresse17, Kontakt17);
        SpenderDAO.createSpender(Spender2);
        CSpender Spender3 = new CSpender(0, "FAMILA Verbrauchermarkt", "", "Mo. - Fr.: 08.00 - 21.00 Uhr", "Am Info-Schalter fragen", CSpender.StatusEnum.grün, Adresse19, Kontakt19);
        SpenderDAO.createSpender(Spender3);
        CSpender Spender4 = new CSpender(0, "Fritten Schlitten", "", "Mo - So: 11:00 - 20:00", "", CSpender.StatusEnum.grün, Adresse21, Kontakt21);
        SpenderDAO.createSpender(Spender4);

        //Benutzer: Tafeln
        CBenutzerDAO BenutzerDAO = new CBenutzerDAO();
        CBenutzer Benutzer = new CBenutzer(0, "Büro", "Büro", "test@tafel.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel);
        Benutzer.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer);
        CBenutzer Benutzer1 = new CBenutzer(0, "Peter", "Radike", "test@tafel1.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel1);
        Benutzer1.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer1);
        CBenutzer Benutzer2 = new CBenutzer(0, "Frau", "Holze-Schüler", "test@tafel2.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel2);
        Benutzer2.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer2);
        CBenutzer Benutzer3 = new CBenutzer(0, "Gerhard", "Stier-Friedland", "test@tafel3.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel3);
        Benutzer3.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer3);
        CBenutzer Benutzer4 = new CBenutzer(0, "Wilfred", "Runge", "test@tafel4.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel4);
        Benutzer4.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer4);
        CBenutzer Benutzer5 = new CBenutzer(0, "Eva-Maria", "Wagner", "test@tafel5.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel5);
        Benutzer5.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer5);
        CBenutzer Benutzer6 = new CBenutzer(0, "Frau", "Gerstenberg", "test@tafel6.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel6);
        Benutzer6.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer6);
        CBenutzer Benutzer7 = new CBenutzer(0, "Wolfgang", "Bock", "test@tafel7.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel7);
        Benutzer7.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer7);
        CBenutzer Benutzer8 = new CBenutzer(0, "Gerd", "Duckstein", "test@tafel8.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel8);
        Benutzer8.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer8);
        CBenutzer Benutzer9 = new CBenutzer(0, "Beate", "Kiehl", "test@tafel15.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel15);
        Benutzer9.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer9);
        CBenutzer Benutzer10 = new CBenutzer(0, "Doreen", "Kreykenbohm", "test@tafel10.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel10);
        Benutzer10.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer10);
        CBenutzer Benutzer11 = new CBenutzer(0, "Jörg", "Weydling", "test@tafel14.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel14);
        Benutzer11.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer11);
        CBenutzer Benutzer12 = new CBenutzer(0, "Norbert", "Wolf", "test@tafel16.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel16);
        Benutzer12.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer12);
        CBenutzer Benutzer13 = new CBenutzer(0, "Lothar", "Ortmann", "test@tafel12.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel12);
        Benutzer13.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer13);
        CBenutzer Benutzer14 = new CBenutzer(0, "Uwe", "Uecker", "test@tafel111.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel11);
        Benutzer14.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer14);
        CBenutzer Benutzer15 = new CBenutzer(0, "Carsten", "Hinrichs", "test@tafel9.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel9);
        Benutzer15.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer15);
        CBenutzer Benutzer16 = new CBenutzer(0, "Elvira", "Enders", "test@tafel13.de", "passwort", CBenutzer.RolleEnum.Tafel, true, null, Tafel13);
        Benutzer16.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer16);
        
        //Benutzer: Spender
        CBenutzer Benutzer17 = new CBenutzer(0, "Martin", "Möllmann", "test@spender2.de", "passwort", CBenutzer.RolleEnum.Spender, true, Spender2, null);
        Benutzer17.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer17);
        CBenutzer Benutzer18 = new CBenutzer(0, "Diane", "Bitzel", "test@spender1.de", "passwort", CBenutzer.RolleEnum.Spender, true, Spender1, null);
        Benutzer18.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer18);
        CBenutzer Benutzer19 = new CBenutzer(0, "Raimund", "Mecke", "test@spender3.de", "passwort", CBenutzer.RolleEnum.Spender, true, Spender3, null);
        Benutzer19.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer19);
        CBenutzer Benutzer20 = new CBenutzer(0, "Elke", "Kohnen", "test@spender.de", "passwort", CBenutzer.RolleEnum.Spender, true, Spender, null);
        Benutzer20.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer20);
        CBenutzer Benutzer21 = new CBenutzer(0, "Chocolate", "Bros", "test@spender4.de", "passwort", CBenutzer.RolleEnum.Spender, true, Spender4, null);
        Benutzer21.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer21);
        
        //Benutzer: Koordinatoren
        CBenutzer Benutzer22 = new CBenutzer(0, "Gérard", "Paschke", "gerard.paschke@online.de", "passwort", CBenutzer.RolleEnum.Koordinator, true, null, null);
        Benutzer22.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer22);
        
        //Benutzer: Administratoren
        CBenutzer Benutzer23 = new CBenutzer(0, "Simon", "Lau", "herr.simon.lau@googlemail.com", "passwort", CBenutzer.RolleEnum.Administrator, true, null, null);
        Benutzer23.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer23);
        CBenutzer Benutzer24 = new CBenutzer(0, "Reinhard", "Rauscher", "r.rauscher@hs-osnabrueck.de", "W5ZG3Pzz", CBenutzer.RolleEnum.Administrator, true, null, null);
        Benutzer23.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer24);
        CBenutzer Benutzer25 = new CBenutzer(0, "Michael", "Ryba", "m.ryba@hs-osnabrueck.de", "UqHNy74Y", CBenutzer.RolleEnum.Administrator, true, null, null);
        Benutzer23.hashPasswort();
        BenutzerDAO.createBenutzer(Benutzer25);

        //Spenden
        CSpendeDAO SpendeDAO = new CSpendeDAO();
        Date time = new Date();
        time.setTime(System.currentTimeMillis());
        CSpende Spende = new CSpende(0, "Nusskuchen", "Sahniger Nusskuchen in zartem Schokoladenmantel", time, time, time, 1234, 100, 2, 0, CSpende.TauschpaletteEnum.keine, CSpende.ArtEnum.Food, CSpende.KuehlungEnum.kühl, time, true, "Lingen", true, "keiner", Benutzer, Kontakt, Adresse, "Fr", "Handschuhe", CSpende.StatusEnum.Eingestellt);
        SpendeDAO.createSpende(Spende);

        //Spendenvorgang
        CSpendenvorgangDAO spendenvorgangDAO = new CSpendenvorgangDAO();
        CSpendenvorgang spendenvorgang = new CSpendenvorgang(0, 2.55, CSpendenvorgang.StatusEnum.ignoriert, null, Spende, Tafel);
        spendenvorgang.setZuweisungszeitpunktNow();
        spendenvorgangDAO.createSpendenvorgang(spendenvorgang);


        //Allgemeines
        CAllgemeines allgemeines = new CAllgemeines(0, "Herzlich willkommen...", "...auf den Seiten des Tafelstellwerks vom \"Landesverband der Tafeln in Niedersachsen und Bremen e. V.\" \n"
                + "Das Tafelstellwerk ist ein Projekt, dass es Spendern ermöglicht, komfortabel Spenden an die Tafeln in Niedersachsen und Bremen abzugeben. Diese werden anschließend unter den Tafeln im Einzugsbereich verteilt.\n"
                + "\n"
                + "Wenn Sie an dem Projekt teilnehmen möchten, nutzen Sie bitte unser Kontaktformular.");
        CAllgemeinesDAO.createAllgemeines(allgemeines);
        allgemeines = new CAllgemeines(0, "Allgemeine Geschäftsbedingungen", "Hier könnten ihre AGBs stehen.");
        CAllgemeinesDAO.createAllgemeines(allgemeines);
        allgemeines = new CAllgemeines(0, "Impressum", "Hier erscheint auf der späteren Seite das Impressum.\n"
                + "\nLandesverband der Tafeln in\n"
                + "Niedersachsen und Bremen e.V.\n"
                + "Karl-Heinz Krüger (1. Vors.)\n"
                + "c/o Wittmunder Tafel e. V.\n"
                + "info@tafel-niedersachsen.de\n"
                + "<a href='http://www.tafel-niedersachsen.de'>www.tafel-niedersachsen.de</a>\n"
                + "<h3>Haftungsausschluss</h3>\n"
                + "Trotz sorgfältiger inhaltlicher Kontrolle übernehmen wir keine Haftung für die Inhalte externer Links. Für den Inhalt der verlinkten Seiten sind ausschließlich deren Betreiber verantwortlich.");
        CAllgemeinesDAO.createAllgemeines(allgemeines);
        allgemeines = new CAllgemeines(0, "EMAIL", "info@tafel-stellwerk-nshb.de");
        CAllgemeinesDAO.createAllgemeines(allgemeines);



        //EMailVorlagen
        CEMailVorlageDAO EMailVorlageDAO = new CEMailVorlageDAO();
        CEMailVorlage eMailVorlage;

//        addAkteur, addSpendeKo, addSpendeSp, changeSpendeKo, changeSpendeSp,
//            addSpendeTa, delSpendeSp, noreact24, noreact48, noreact48Ko, accSpendeSp,accSpendeTa

        eMailVorlage = new CEMailVorlage(0, "Willkommen beim Tafelstellwerk", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Herzlich willkommen auf der Plattform Tafelstellwerk.\r\nAb sofort können Sie sich mit Ihrer E-Mail Adresse und dem zugewiesenen Passwort anmelden."
                + "\r\nHinweis: Es ist aus Sicherheitsgründen ratsam, das Passwort nach der Erstanmeldung in ein beliebiges, neues Passwort zu ändern!"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spendeneingang", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine neue Spende wurde aufgenommen! \r\nBitte besuchen Sie zeitnah das Tafelstellwerk-Portal, um diese zu überprüfen und für die Tafeln freizugeben."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spendeneingang", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich im Portal Tafelstellwerk registriert. Vielen Dank!"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Änderungsbestätigung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Die Spende wurde erfolgreich geändert."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Änderungsbestätigung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich geändert."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - neue Spende", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Wir freuen uns, Ihnen mitteilen zu dürfen, dass eine neue Spende bereitsteht.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen und Ihr Feedback zu geben."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spende gelöscht", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich gelöscht."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Erinnerung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende steht seit 1nem Tag bereit.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen und Ihr Feedback zu geben.\r\nSollten Sie kein Interesse an einem Teil dieser Spende haben, möchten wir Sie bitten, uns dies mitzuteilen."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Erinnerung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende steht seit 2 Tagen bereit.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen und Ihr Feedback zu geben.\r\nSollten Sie kein Interesse an einem Teil dieser Spende haben, möchten wir Sie bitten, uns dies mitzuteilen."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Erinnerung", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende steht seit 2 Tagen für diese Tafel bereit.\r\n"
                + "\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen.\r\n"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Angenommene Spende", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine freigegebene Spende wurde von einer Tafel angenommen.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen, letzte Änderungen vorzunehmen und den Verteilungsprozess zu in Gang zu setzen."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Abgelehnte Spende", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine freigegebene Spende wurde von einer Tafel abgelehnt.\r\nBitte nutzen Sie das Portal Tafelstellwerk, um diese einzusehen, Informationen zu erhalten und die Spende erneut freizugeben."
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spende erfolgreich verteilt!", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Eine Spende konnte erfolgreich verteilt werden! Nähere Informationen finden Sie unten in dieser Nachricht!\r\n\r\n"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);
        eMailVorlage = new CEMailVorlage(0, "Tafelstellwerk - Spende erfolgreich verteilt!", "Sehr geehrte Damen und Herren, \r\n\r\n"
                + "Ihre Spende wurde erfolgreich verteilt! Nähere Informationen finden Sie unten in dieser Nachricht!\r\n"
                + "Die Tafel wird die Ware in den nächsten Tagen abholen. Sollten Sie die Ware liefern,\r\n"
                + "kontaktieren Sie bitte einen Koordinator für weitere Instruktionen (z.B. Lieferadressen).\r\n\r\n"
                + "\r\n\r\nMit freundlichen Grüßen\r\nIhr Tafelteam\r\n\r\n\r\n\r\n-Diese Mail wurde automatisch vom Tafelstellwerk generiert-");
        EMailVorlageDAO.createEMailVorlage(eMailVorlage);

    }
}
