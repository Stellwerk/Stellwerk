/*
 * Für Auswertungen
 */
package de.tafelstellwerk.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Erstellt Objekte, die für das Spendenarchiv und die statistische Auswertung
 * benötigt werden.
 *
 * @version 1.0
 * @author gpaschke
 */
@Entity
@Table(name = "ARCHIV")
public class CSpendenarchiv implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @ManyToOne
    private CSpende spende;
    @ManyToOne
    private CTafel tafel;
    private double gewicht;      //Gewicht in KG, anzahl, paletten, volumen in L
    private int anzahl;
    private int paletten;
    private double volumen;

    //Konstruktor
    /**
     * Erstellt ein leeres Spendenarchiv Objekt
     */
    public CSpendenarchiv() {
    }

    /**
     * Erstellt ein gefülltes Spendenarchiv Objekt
     *
     * @param id
     * @param spende
     * @param tafel
     * @param gewicht
     * @param anzahl
     * @param paletten
     * @param volumen
     */
    public CSpendenarchiv(int id, CSpende spende, CTafel tafel, double gewicht, int anzahl, int paletten, double volumen) {
        this.id = id;
        this.spende = spende;
        this.tafel = tafel;
        this.gewicht = gewicht;
        this.anzahl = anzahl;
        this.paletten = paletten;
        this.volumen = volumen;
    }

    //Getter
    public int getId() {
        return id;
    }

    public CSpende getSpende() {
        return spende;
    }

    public CTafel getTafel() {
        return tafel;
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

    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setSpende(CSpende spende) {
        this.spende = spende;
    }

    public void setTafel(CTafel tafel) {
        this.tafel = tafel;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void setPaletten(int paletten) {
        this.paletten = paletten;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }
}
