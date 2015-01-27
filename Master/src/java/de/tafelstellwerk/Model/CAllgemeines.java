/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Angaben zu den Inhalten
 *
 * @author gpaschke
 */
@Entity
@Table(name = "ALLGEMEINES")
public class CAllgemeines implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(length = 60)
    private String titel;
    @Column(length = 6000)
    private String inhalt;

    //Konstruktor
    /**
     * Erstellt ein leeres Allgemeines Objekt
     */
    public CAllgemeines() {
    }

    /**
     * Erstellt ein befülltes Allgemeines Objekt
     *
     * @param id
     * @param titel
     * @param inhalt
     */
    public CAllgemeines(int id, String titel, String inhalt) {
        this.id = id;
        this.titel = titel;
        this.inhalt = inhalt;
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getInhalt() {
        return inhalt;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }
}
