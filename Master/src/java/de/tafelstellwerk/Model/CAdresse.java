package de.tafelstellwerk.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Adressangaben Organisation und Spende sollen die Variablen erben
 *
 * @author gpaschke
 * @version 1.0
 *
 * @param adressID
 * @param strasse
 * @param plz
 * @param ort
 * @param beschreibung
 *
 */
@Entity
@Table(name = "ADRESSE")
public class CAdresse implements Serializable {

    //Variablen
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int adressID;
    @Size(min = 3, max = 50)
    private String strasse;
    @Pattern(regexp = "[0-9]{5}")
    private String plz;
    @Pattern(regexp = "^[\\p{L}A-Za-z-/]+$")
    private String ort;
    private String beschreibung;

    //Konstruktor
    /**
     * Erstellt ein leeres Adress Objekt
     */
    public CAdresse() {
    }

    /**
     * Erstellt ein befülltes Adress-Objekt
     *
     * @param adressID
     * @param strasse
     * @param plz
     * @param ort
     * @param beschreibung
     */
    public CAdresse(int adressID, String strasse, String plz, String ort, String beschreibung) {
        this.adressID = adressID;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.beschreibung = beschreibung;
    }

    //Getter
    public int getAdressID() {
        return adressID;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getPlz() {
        return plz;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getOrt() {
        return ort;
    }

    //Setter
    public void setAdressID(int AdressID) {
        this.adressID = AdressID;
    }

    public void setStrasse(String Strasse) {
        this.strasse = Strasse;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public void setBeschreibung(String Beschreibung) {
        this.beschreibung = Beschreibung;
    }

    public void setOrt(String Ort) {
        this.ort = Ort;
    }

    /**
     * Override der toString() Methode
     *
     * @return
     */
    @Override
    public String toString() {
        return "CAdresse{" + "AdressID=" + adressID + ", Strasse=" + strasse + ", PLZ=" + plz + ", Ort=" + ort + ", Beschreibung=" + beschreibung + '}';
    }
}
