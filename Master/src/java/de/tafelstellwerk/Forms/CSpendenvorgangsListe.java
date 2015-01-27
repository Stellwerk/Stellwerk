package de.tafelstellwerk.Forms;

import de.tafelstellwerk.Model.CSpendenvorgang;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gpaschke
 */
public class CSpendenvorgangsListe {

    private List<CSpendenvorgang> spendenvorgang = new ArrayList<>();

    /**
     * Konstruktor
     */
    public CSpendenvorgangsListe() {
    }

    // Getter Setter
    /**
     * 
     * @return 
     */
    public List<CSpendenvorgang> getSpendenvorgang() {
        return spendenvorgang;
    }

    /**
     *
     * @param spendenvorgang
     */
    public void setSpendenvorgang(List<CSpendenvorgang> spendenvorgang) {
        this.spendenvorgang = spendenvorgang;
    }
}
