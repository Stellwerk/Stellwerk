package de.tafelstellwerk.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Spende mit allen Attributen + adresse + kontakt + benutzer
 *
 * @version 1.2
 * @author Gérard
 *
 * @param spendeID Int, sollte beim Erstellen auf 0 gesetzt werden, damit
 * Hibernate dies automatisch zuordnet
 * @param produktbezeichnung
 * @param beschreibung
 * @param erstellungsdatum
 * @param abholfrist
 * @param lieferung
 * @param rueckmeldefrist
 * @param gewicht
 * @param anzahl
 * @param paletten
 * @param volumen
 * @param tauschpalette
 * @param art
 * @param kuehlung
 * @param mhd
 * @param alkohol
 * @param bevorzugte_Tafeln
 * @param spendenbeleg
 * @param kommentar
 * @param benutzer
 * @param kontakt
 * @param adresse
 * @param abholzeiten
 * @param sicherheitsbestimmungen
 * @param status
 */
//http://www.mkyong.com/hibernate/hibernate-named-query-examples/
@Entity
@Table(name = "SPENDE")
@NamedQueries(
        {
    @NamedQuery(name = "getLastSpendeByEMail", query = "select s from CSpende s,CBenutzer b where b.eMail = :eMail and s.benutzer = b.benutzerID ORDER BY s.spendeID DESC)"),
    @NamedQuery(name = "getMeineSpendenByEMail", query = "select s from CSpende s,CBenutzer b where s.benutzer = b.benutzerID and s.status <> :status and b.eMail = :eMail ORDER BY s.spendeID DESC)"),
    @NamedQuery(name = "getSpendenByStatus", query = "select s from CSpende s where s.status = :status ORDER BY s.spendeID DESC)")
})
public class CSpende implements Serializable {

    //Variablen
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int spendeID;
    @Size(min = 3, max = 30)
    private String produktbezeichnung;
    @Size(min = 0, max = 500)
    @Column(length = 500)
    private String beschreibung;
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date erstellungsdatum;
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date abholfrist;
    private Boolean lieferung = false;          //True - Spende wird geliefert, False - muss abgeholt werden
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rueckmeldefrist;       //Zeitpunkt bis zu dem entschieden werden muss, ob die Spende angenommen wird
    private double gewicht;      //Gewicht in KG, anzahl, paletten, volumen in L
    private int anzahl;
    private int paletten;
    private double volumen;
    @Enumerated
    private TauschpaletteEnum tauschpalette;
    @Enumerated
    private ArtEnum art;
    @Enumerated
    private KuehlungEnum kuehlung;
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mhd;
    private Boolean alkohol;            //True- alkohol vorhanden, FALSE- nicht vorhanden
    private String bevorzugte_Tafeln;
    private Boolean spendenbeleg;       //True- erwünscht, FALSE- nicht nötig
    private String kommentar;
    @ManyToOne
    private CBenutzer benutzer;
    @ManyToOne
    private CKontakt kontakt;
    @ManyToOne
    private CAdresse adresse;
    private String abholzeiten;
    private String sicherheitsbestimmungen;
    @Enumerated
    private StatusEnum status;
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date abschlussdatum;

    // Enum Felder - neuer TYP muss public!
    public enum ArtEnum {

        Food, nonFood, Getraenke_mitPfandGespendet, Getraenke_mitPfandZurueck, Getraenke_ohnePfand
    };

    public enum KuehlungEnum {

        keine, kühl, tiefgekühlt
    };

    public enum TauschpaletteEnum {

        keine, EURO, CHEP
    };

    public enum StatusEnum {

        Eingestellt, Freigegeben, Verteilt
    }; // Bearbeitung, Abschluss,

    // Konstruktor zum Erstellen der Klasse
    public CSpende() {
    }

    //Für erstellungsdatum: Date time = new Date();
    //time.setTime(System.currentTimeMillis());
    /**
     * Erstellt ein befülltes Spenden Objekt
     *
     * @param spendeID
     * @param produktbezeichnung
     * @param beschreibung
     * @param erstellungsdatum
     * @param abholfrist
     * @param rueckmeldefrist
     * @param gewicht
     * @param anzahl
     * @param paletten
     * @param volumen
     * @param tauschpalette
     * @param art
     * @param kuehlung
     * @param mhd
     * @param alkohol
     * @param bevorzugte_Tafeln
     * @param spendenbeleg
     * @param kommentar
     * @param benutzer
     * @param kontakt
     * @param adresse
     * @param abholzeiten
     * @param sicherheitsbestimmungen
     * @param status
     */
    public CSpende(int spendeID, String produktbezeichnung, String beschreibung, Date erstellungsdatum, Date abholfrist, Date rueckmeldefrist, double gewicht, int anzahl, int paletten, double volumen, TauschpaletteEnum tauschpalette, ArtEnum art, KuehlungEnum kuehlung, Date mhd, Boolean alkohol, String bevorzugte_Tafeln, Boolean spendenbeleg, String kommentar, CBenutzer benutzer, CKontakt kontakt, CAdresse adresse, String abholzeiten, String sicherheitsbestimmungen, StatusEnum status) {
        this.spendeID = spendeID;
        this.produktbezeichnung = produktbezeichnung;
        this.beschreibung = beschreibung;
        this.erstellungsdatum = erstellungsdatum;
        this.abholfrist = abholfrist;
        this.rueckmeldefrist = rueckmeldefrist;
        this.gewicht = gewicht;
        this.anzahl = anzahl;
        this.paletten = paletten;
        this.volumen = volumen;
        this.tauschpalette = tauschpalette;
        this.art = art;
        this.kuehlung = kuehlung;
        this.mhd = mhd;
        this.alkohol = alkohol;
        this.bevorzugte_Tafeln = bevorzugte_Tafeln;
        this.spendenbeleg = spendenbeleg;
        this.kommentar = kommentar;
        this.benutzer = benutzer;
        this.kontakt = kontakt;
        this.adresse = adresse;
        this.abholzeiten = abholzeiten;
        this.sicherheitsbestimmungen = sicherheitsbestimmungen;
        this.status = status;
    }

    public CSpende(int spendeID, String produktbezeichnung, String beschreibung, Date erstellungsdatum, Date abholfrist, Date rueckmeldefrist, double gewicht, int anzahl, int paletten, double volumen, TauschpaletteEnum tauschpalette, ArtEnum art, KuehlungEnum kuehlung, Date mhd, Boolean alkohol, String bevorzugte_Tafeln, Boolean spendenbeleg, String kommentar, CBenutzer benutzer, CKontakt kontakt, CAdresse adresse, String abholzeiten, String sicherheitsbestimmungen, StatusEnum status, Date abschlussdatum) {
        this.spendeID = spendeID;
        this.produktbezeichnung = produktbezeichnung;
        this.beschreibung = beschreibung;
        this.erstellungsdatum = erstellungsdatum;
        this.abholfrist = abholfrist;
        this.rueckmeldefrist = rueckmeldefrist;
        this.gewicht = gewicht;
        this.anzahl = anzahl;
        this.paletten = paletten;
        this.volumen = volumen;
        this.tauschpalette = tauschpalette;
        this.art = art;
        this.kuehlung = kuehlung;
        this.mhd = mhd;
        this.alkohol = alkohol;
        this.bevorzugte_Tafeln = bevorzugte_Tafeln;
        this.spendenbeleg = spendenbeleg;
        this.kommentar = kommentar;
        this.benutzer = benutzer;
        this.kontakt = kontakt;
        this.adresse = adresse;
        this.abholzeiten = abholzeiten;
        this.sicherheitsbestimmungen = sicherheitsbestimmungen;
        this.status = status;
        this.abschlussdatum = abschlussdatum;
    }

    //Methoden
    /**
     * setzt das erstellungsdatum auf das derzeitige Datum
     */
    public void setErstellungsdatumNow() {
        Date now = new Date();
        now.setTime(System.currentTimeMillis());
        this.erstellungsdatum = now;
    }

    //Getter
    public int getSpendeID() {
        return spendeID;
    }

    public String getProduktbezeichnung() {
        return produktbezeichnung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public Date getErstellungsdatum() {
        return erstellungsdatum;
    }

    public Date getAbholfrist() {
        return abholfrist;
    }

    public Boolean getLieferung() {
        return lieferung;
    }

    public Date getRueckmeldefrist() {
        return rueckmeldefrist;
    }

    public double getGewicht() {
        return gewicht;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public int getPaletten() {
        return paletten;
    }

    public double getVolumen() {
        return volumen;
    }

    public TauschpaletteEnum getTauschpalette() {
        return tauschpalette;
    }

    public ArtEnum getArt() {
        return art;
    }

    public KuehlungEnum getKuehlung() {
        return kuehlung;
    }

    public Date getMhd() {
        return mhd;
    }

    public Boolean getAlkohol() {
        return alkohol;
    }

    public String getBevorzugte_Tafeln() {
        return bevorzugte_Tafeln;
    }

    public Boolean getSpendenbeleg() {
        return spendenbeleg;
    }

    public String getKommentar() {
        return kommentar;
    }

    public CBenutzer getBenutzer() {
        return benutzer;
    }

    public CKontakt getKontakt() {
        return kontakt;
    }

    public CAdresse getAdresse() {
        return adresse;
    }

    public String getAbholzeiten() {
        return abholzeiten;
    }

    public String getSicherheitsbestimmungen() {
        return sicherheitsbestimmungen;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Date getAbschlussdatum() {
        return abschlussdatum;
    }

    // Setter
    public void setMhd(Date mhd) {
        this.mhd = mhd;
    }

    public void setSpendeID(int SpendeID) {
        this.spendeID = SpendeID;
    }

    public void setProduktbezeichnung(String Produktbezeichnung) {
        this.produktbezeichnung = Produktbezeichnung;
    }

    public void setBeschreibung(String Beschreibung) {
        this.beschreibung = Beschreibung;
    }

    public void setErstellungsdatum(Date Erstellungsdatum) {
        this.erstellungsdatum = Erstellungsdatum;
    }

    public void setAbholfrist(Date Abholfrist) {
        this.abholfrist = Abholfrist;
    }

    public void setLieferung(Boolean Lieferung) {
        this.lieferung = Lieferung;
    }

    public void setRueckmeldefrist(Date Rueckmeldefrist) {
        this.rueckmeldefrist = Rueckmeldefrist;
    }

    public void setGewicht(double Gewicht) {
        this.gewicht = Gewicht;
    }

    public void setAnzahl(int Anzahl) {
        this.anzahl = Anzahl;
    }

    public void setPaletten(int Paletten) {
        this.paletten = Paletten;
    }

    public void setVolumen(double Volumen) {
        this.volumen = Volumen;
    }

    public void setTauschpalette(TauschpaletteEnum Tauschpalette) {
        this.tauschpalette = Tauschpalette;
    }

    public void setArt(ArtEnum Art) {
        this.art = Art;
    }

    public void setKuehlung(KuehlungEnum Kuehlung) {
        this.kuehlung = Kuehlung;
    }

    public void setAlkohol(Boolean Alkohol) {
        this.alkohol = Alkohol;
    }

    public void setBevorzugte_Tafeln(String Bevorzugte_Tafeln) {
        this.bevorzugte_Tafeln = Bevorzugte_Tafeln;
    }

    public void setSpendenbeleg(Boolean Spendenbeleg) {
        this.spendenbeleg = Spendenbeleg;
    }

    public void setKommentar(String Kommentar) {
        this.kommentar = Kommentar;
    }

    public void setBenutzer(CBenutzer Benutzer) {
        this.benutzer = Benutzer;
    }

    public void setKontakt(CKontakt Kontakt) {
        this.kontakt = Kontakt;
    }

    public void setAdresse(CAdresse Adresse) {
        this.adresse = Adresse;
    }

    public void setAbholzeiten(String Abholzeiten) {
        this.abholzeiten = Abholzeiten;
    }

    public void setSicherheitsbestimmungen(String Sicherheitsbestimmungen) {
        this.sicherheitsbestimmungen = Sicherheitsbestimmungen;
    }

    public void setStatus(StatusEnum Status) {
        this.status = Status;
    }

    public void setAbschlussdatum(Date abschlussdatum) {
        this.abschlussdatum = abschlussdatum;
    }

    @Override
    public String toString() {
        return "CSpende{" + "spendeID=" + spendeID + ", produktbezeichnung=" + produktbezeichnung + ", beschreibung=" + beschreibung + ", erstellungsdatum=" + erstellungsdatum + ", abholfrist=" + abholfrist + ", lieferung=" + lieferung + ", rueckmeldefrist=" + rueckmeldefrist + ", gewicht=" + gewicht + ", anzahl=" + anzahl + ", paletten=" + paletten + ", volumen=" + volumen + ", tauschpalette=" + tauschpalette + ", art=" + art + ", kuehlung=" + kuehlung + ", mhd=" + mhd + ", alkohol=" + alkohol + ", bevorzugte_Tafeln=" + bevorzugte_Tafeln + ", spendenbeleg=" + spendenbeleg + ", kommentar=" + kommentar + ", benutzer=" + benutzer + ", kontakt=" + kontakt + ", adresse=" + adresse + ", abholzeiten=" + abholzeiten + ", sicherheitsbestimmungen=" + sicherheitsbestimmungen + ", status=" + status + ", abschlussdatum=" + abschlussdatum + '}';
    }
}
