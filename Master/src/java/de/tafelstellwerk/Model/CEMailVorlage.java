package de.tafelstellwerk.Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EMail Vorlage zur Bearbeitung der Datenbankeinträge; Klasse wird ansonsten
 * nicht benutzt; zieht sich betreff und nachricht
 *
 * @author gpaschke
 * @see CEMail
 * @param vorlageID
 * @param betreff
 * @param nachricht
 */
@Entity
@Table(name = "EMAILVORLAGE")
public class CEMailVorlage implements Serializable {
    //Variablen

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int vorlageID;
//        addAkteur, addSpendeKo, addSpendeSp, changeSpendeKo, changeSpendeSp,
//        addSpendeTa, delSpendeSp, noreact24, noreact48, noreact48Ko, accSpendeKo, decSpendeKo
//        add = hinzufügen, change = ändern, comp = reklamieren, del = löschen, noreact = keine Reaktion, acc = annehmen, dec = ablehnen
//            // Enum-Feld
//            public enum AnlassEnum {addAkteur, addSpendeKo, addSpendeSp, changeSpendeKo, changeSpendeSp,
//            addSpendeTa, delSpendeSp, noreact24, noreact48, noreact48Ko, accSpendeSp,accSpendeTa};
//    @Enumerated(EnumType.STRING)
//    private AnlassEnum Anlass;
    private String betreff;
    @Column(length = 3000)
    private String nachricht;

    //Konstruktor
    public CEMailVorlage() {
    }

    /**
     * Konstruktor
     *
     * @param vorlageID
     * @param betreff
     * @param nachricht
     */
    public CEMailVorlage(int vorlageID, String betreff, String nachricht) {
        this.vorlageID = vorlageID;
        this.betreff = betreff;
        this.nachricht = nachricht;
    }

    //Getter
    public int getVorlageID() {
        return vorlageID;
    }

    public String getBetreff() {
        return betreff;
    }

    public String getNachricht() {
        return nachricht;
    }

    //Setter
    public void setVorlageID(int VorlageID) {
        this.vorlageID = VorlageID;
    }

    public void setBetreff(String Betreff) {
        this.betreff = Betreff;
    }

    public void setNachricht(String Nachricht) {
        this.nachricht = Nachricht;
    }
}
