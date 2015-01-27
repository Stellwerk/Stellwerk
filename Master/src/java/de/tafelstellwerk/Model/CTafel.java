package de.tafelstellwerk.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Tafelklasse - Tafel als Organisation gedacht, der Benutzer zugeordnet werden
 * können, die im Sinne der Tafel handeln
 *
 * @author gpaschke
 * @see AOrganisation
 *
 * @param id
 * @param tafelname
 * @param transporter
 * @param oeffnungszeiten
 * @param notizen
 * @param status
 * @param adresse
 * @param kontakt
 */
@Entity
@Table(name = "TAFEL")
public class CTafel implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Size(min = 1, max = 50)
    @Column(unique = true)
    private String tafelname;
    @Size(max = 255)
    private String transporter;
    @Size(max = 255)
    private String oeffnungszeiten;
    @Size(max = 4095)
    @Column(length = 4095)
    private String notizen;

    public enum StatusEnum {

        grün, gelb
    };
    private StatusEnum status;
    @Valid
    @OneToOne
    private CAdresse adresse;
    @Valid
    @OneToOne
    private CKontakt kontakt;
    @Transient
    private Boolean ausgewaehlt = false; //nur für die spendenverwaltung, steht nicht in der db - so können Tafeln ausgewählt werden

// Konstruktor zum Erstellen der Klasse
    /**
     * Erstellt leeres Tafel Objekt
     */
    public CTafel() {
    }

    /**
     * Erstellt befülltes Tafel Objekt
     *
     * @param id
     * @param tafelname
     * @param transporter
     * @param oeffnungszeiten
     * @param notizen
     * @param status
     * @param adresse
     * @param kontakt
     */
    public CTafel(int id, String tafelname, String transporter, String oeffnungszeiten, String notizen, StatusEnum status, CAdresse adresse, CKontakt kontakt) {
        this.id = id;
        this.tafelname = tafelname;
        this.transporter = transporter;
        this.oeffnungszeiten = oeffnungszeiten;
        this.notizen = notizen;
        this.status = status;
        this.adresse = adresse;
        this.kontakt = kontakt;
        this.ausgewaehlt = false;
    }

    //nur für die spendenverwaltung
    public CTafel(int id, String tafelname, String transporter, String oeffnungszeiten, String notizen, StatusEnum status, CAdresse adresse, CKontakt kontakt, Boolean ausgewaehlt) {
        this.id = id;
        this.tafelname = tafelname;
        this.transporter = transporter;
        this.oeffnungszeiten = oeffnungszeiten;
        this.notizen = notizen;
        this.status = status;
        this.adresse = adresse;
        this.kontakt = kontakt;
        this.ausgewaehlt = ausgewaehlt;
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getTafelname() {
        return tafelname;
    }

    public String getTransporter() {
        return transporter;
    }

    public String getOeffnungszeiten() {
        return oeffnungszeiten;
    }

    public String getNotizen() {
        return notizen;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public CAdresse getAdresse() {
        return adresse;
    }

    public CKontakt getKontakt() {
        return kontakt;
    }

    //Setter 
    public void setId(int id) {
        this.id = id;
    }

    public void setTafelname(String tafelname) {
        this.tafelname = tafelname;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public void setOeffnungszeiten(String oeffnungszeiten) {
        this.oeffnungszeiten = oeffnungszeiten;
    }

    public void setNotizen(String notizen) {
        this.notizen = notizen;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setAdresse(CAdresse adresse) {
        this.adresse = adresse;
    }

    public void setKontakt(CKontakt kontakt) {
        this.kontakt = kontakt;
    }

    //weitere  - nur für spendenverwaltung
    public Boolean getAusgewaehlt() {
        return ausgewaehlt;
    }

    public void setAusgewaehlt(Boolean ausgewaehlt) {
        this.ausgewaehlt = ausgewaehlt;
    }

    //ToString
    @Override
    public String toString() {
        return "CTafel{" + "id=" + id + ", tafelname=" + tafelname + ", transporter=" + transporter + ", oeffnungszeiten=" + oeffnungszeiten + ", notizen=" + notizen + ", status=" + status + ", adresse=" + adresse + ", kontakt=" + kontakt + ", ausgewaehlt=" + ausgewaehlt + '}';
    }
}
