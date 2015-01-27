package de.tafelstellwerk.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 * Kontaktdaten - für Benutzer, Spender, Tafel, Spende
 *
 * @author gpaschke
 *
 * @param kontaktID
 * @param vorname
 * @param nachname
 * @param eMail
 * @param telefon
 * @param fax
 */
@Entity
@Table(name = "KONTAKT")
public class CKontakt implements Serializable {

    //Variablen
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int kontaktID;
    @Pattern(regexp = "^[\\p{L}A-Za-z- ]+$")
    private String vorname;
    @Pattern(regexp = "^[\\p{L}A-Za-z- ]+$")
    private String nachname;
    @Pattern(regexp = "^$|[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}")
    private String eMail;
    @Pattern(regexp = "^$|^[0-9+-/() ]+$")
    private String telefon;
    @Pattern(regexp = "^$|^[0-9+-/() ]+$")
    private String fax;

    //Konstruktor
    public CKontakt() {
    }

    /**
     * Erstellt befülltes Kontakt Objekt
     *
     * @param kontaktID
     * @param vorname
     * @param nachname
     * @param eMail
     * @param telefon
     * @param fax
     */
    public CKontakt(int kontaktID, String vorname, String nachname, String eMail, String telefon, String fax) {
        this.kontaktID = kontaktID;
        this.vorname = vorname;
        this.nachname = nachname;
        this.eMail = eMail;
        this.telefon = telefon;
        this.fax = fax;
    }

    //Getter
    public int getKontaktID() {
        return kontaktID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String geteMail() {
        return eMail;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getFax() {
        return fax;
    }

    //Setter
    public void setKontaktID(int KontaktID) {
        this.kontaktID = KontaktID;
    }

    public void setVorname(String Vorname) {
        this.vorname = Vorname;
    }

    public void setNachname(String Nachname) {
        this.nachname = Nachname;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setTelefon(String Telefon) {
        this.telefon = Telefon;
    }

    public void setFax(String Fax) {
        this.fax = Fax;
    }

    //ToString
    @Override
    public String toString() {
        return "CKontakt{" + "KontaktID=" + kontaktID + ", Vorname=" + vorname + ", Nachname=" + nachname + ", EMail=" + eMail + ", Telefon=" + telefon + ", Fax=" + fax + '}';
    }
}
