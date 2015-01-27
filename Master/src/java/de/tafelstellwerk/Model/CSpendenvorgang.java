package de.tafelstellwerk.Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Klasse zur Spendenverwaltung - setzt eine spende mit einer Tafel in
 * Verbindung
 *
 * @author gpaschke
 * @version 1.0
 *
 * @param spendenvorgangsID
 * @param anteil
 * @param status zugegriffen, ignoriert
 * @param zuweisungszeitpunkt Date
 * @param spende
 * @param tafel
 */
@Entity
@Table(name = "SPENDENVORGANG")
@NamedQueries(
        {
    @NamedQuery(name = "getMeineSpenden", query = "select v from CBenutzer b, CSpendenvorgang v where b.eMail = :eMail and v.tafel = b.tafel and v.status = :status ORDER BY v.spendenvorgangsID DESC)")
})
public class CSpendenvorgang implements Serializable {

    public enum StatusEnum {

        zugegriffen, ignoriert
    };
    //Variablendeklaration
    @Id
    @GeneratedValue
    @Column(name = "SPENDENVORGANGS_ID")
    private int spendenvorgangsID;
    private double anteil; //Prozentualer anteil an Menge, Gewicht, Anzahl der spende
    private StatusEnum status; //Wurde auf den Spendenvorgang eingegangen
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zuweisungszeitpunkt;
    @ManyToOne
    private CSpende spende;
    @OneToOne
    private CTafel tafel;

    //Konstruktor
    public CSpendenvorgang() {
    }

    /**
     * Konstruktor
     *
     * @param spendenvorgangsID
     * @param anteil
     * @param status zugegriffen, ignoriert
     * @param zuweisungszeitpunkt Date
     * @param spende
     * @param tafel
     */
    public CSpendenvorgang(int spendenvorgangsID, double anteil, StatusEnum status, Date zuweisungszeitpunkt, CSpende spende, CTafel tafel) {
        this.spendenvorgangsID = spendenvorgangsID;
        this.anteil = anteil;
        this.status = status;
        this.zuweisungszeitpunkt = zuweisungszeitpunkt;
        this.spende = spende;
        this.tafel = tafel;
    }

    /**
     * Spendenvorgang für den Spendenabschluss, Tafel auswählen und verteilen
     *
     * @param spendenvorgangsID
     * @param anteil
     * @param tafel
     */
    public CSpendenvorgang(int spendenvorgangsID, double anteil, CTafel tafel) {
        this.spendenvorgangsID = spendenvorgangsID;
        this.anteil = anteil;
        this.tafel = tafel;
    }

    /**
     * Spendenvorgang bei der Zuweisung der Ware an die Tafeln
     *
     * @param spendenvorgangsID
     * @param anteil
     * @param spende
     * @param tafel
     */
    public CSpendenvorgang(int spendenvorgangsID, double anteil, CSpende spende, CTafel tafel) {
        this.spendenvorgangsID = spendenvorgangsID;
        this.anteil = anteil;
        this.spende = spende;
        this.tafel = tafel;
    }

    //Methoden
    public void setZuweisungszeitpunktNow() {
        Date now = new Date();
        now.setTime(System.currentTimeMillis());
        this.zuweisungszeitpunkt = now;
    }

    //Setter
    public void setSpendenvorgangsID(int SpendenvorgangsID) {
        this.spendenvorgangsID = SpendenvorgangsID;
    }

    public void setAnteil(double Anteil) {
        this.anteil = Anteil;
    }

    public void setStatus(StatusEnum Status) {
        this.status = Status;
    }

    public void setZuweisungszeitpunkt(Date Zuweisungszeitpunkt) {
        this.zuweisungszeitpunkt = Zuweisungszeitpunkt;
    }

    public void setSpende(CSpende Spende) {
        this.spende = Spende;
    }

    public void setTafel(CTafel Tafel) {
        this.tafel = Tafel;
    }

    //Getter
    public int getSpendenvorgangsID() {
        return spendenvorgangsID;
    }

    public double getAnteil() {
        return anteil;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Date getZuweisungszeitpunkt() {
        return zuweisungszeitpunkt;
    }

    public CSpende getSpende() {
        return spende;
    }

    public CTafel getTafel() {
        return tafel;
    }

    @Override
    public String toString() {
        return "CSpendenvorgang{" + "spendenvorgangsID=" + spendenvorgangsID + ", anteil=" + anteil + ", status=" + status + ", zuweisungszeitpunkt=" + zuweisungszeitpunkt + ", spende=" + spende + ", tafel=" + tafel + '}';
    }
}
