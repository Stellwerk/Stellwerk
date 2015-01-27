package de.tafelstellwerk.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Spenderklasse - gedacht als Firma, dem Benutzer zugewiesen werden und im
 * Sinne der Firma handeln
 *
 * @author gpaschke
 *
 * @param id
 * @param firmenname
 * @param sicherheitsbestimmungen
 * @param oeffnungszeiten
 * @param notizen
 * @param status
 * @param adresse
 * @param kontakt
 */
@Entity
@Table(name = "SPENDER")
public class CSpender implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Size(min = 1, max = 50)
    @Column(unique = true)
    private String firmenname;
    @Size(max = 255)
    private String sicherheitsbestimmungen;
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

// Konstruktor zum Erstellen der Klasse
    /**
     * Erstellt leeres Spender Objekt
     */
    public CSpender() {
    }

    /**
     * Erstellt befülltes Spender Objekt
     *
     * @param ID
     * @param Firmenname
     * @param Sicherheitsbestimmungen
     * @param Oeffnungszeiten
     * @param Notizen
     * @param Status
     * @param Adresse
     * @param Kontakt
     */
    public CSpender(int id, String firmenname, String sicherheitsbestimmungen, String oeffnungszeiten, String notizen, StatusEnum status, CAdresse adresse, CKontakt kontakt) {
        this.id = id;
        this.firmenname = firmenname;
        this.sicherheitsbestimmungen = sicherheitsbestimmungen;
        this.oeffnungszeiten = oeffnungszeiten;
        this.notizen = notizen;
        this.status = status;
        this.adresse = adresse;
        this.kontakt = kontakt;
    }

    //Getter
    public int getId() {
        return id;
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

    public String getFirmenname() {
        return firmenname;
    }

    public String getSicherheitsbestimmungen() {
        return sicherheitsbestimmungen;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setOeffnungszeiten(String Oeffnungszeiten) {
        this.oeffnungszeiten = Oeffnungszeiten;
    }

    public void setNotizen(String Notizen) {
        this.notizen = Notizen;
    }

    public void setStatus(StatusEnum Status) {
        this.status = Status;
    }

    public void setAdresse(CAdresse Adresse) {
        this.adresse = Adresse;
    }

    public void setKontakt(CKontakt Kontakt) {
        this.kontakt = Kontakt;
    }

    public void setFirmenname(String Firmenname) {
        this.firmenname = Firmenname;
    }

    public void setSicherheitsbestimmungen(String Sicherheitsbestimmungen) {
        this.sicherheitsbestimmungen = Sicherheitsbestimmungen;
    }

    //ToString
    @Override
    public String toString() {
        return "CSpender{" + "ID=" + id + ", Firmenname=" + firmenname + ", Sicherheitsbestimmungen=" + sicherheitsbestimmungen + ", Oeffnungszeiten=" + oeffnungszeiten + ", Notizen=" + notizen + ", Status=" + status + ", Adresse=" + adresse + ", Kontakt=" + kontakt + '}';
    }
}
