package de.tafelstellwerk.Forms;

import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Model.CTafel;
import java.util.ArrayList;
import java.util.List;

/**
 * Benutzer bearbeiten, Spender oder Tafel hinzufügen
 *
 * @author gpaschke
 */
public class CSpenderTafelListe {

    private List<CSpender> spender = new ArrayList<>();
    private List<CTafel> tafel = new ArrayList<>();
    private int selectedID;

    /**
     * Konstruktor
     */
    public CSpenderTafelListe() {
    }

    //Getter Setter
    /**
     *
     * @return
     */
    public List<CSpender> getSpender() {
        return spender;
    }

    /**
     *
     * @return
     */
    public List<CTafel> getTafel() {
        return tafel;
    }

    /**
     *
     * @return
     */
    public int getSelectedID() {
        return selectedID;
    }

    /**
     *
     * @param spender
     */
    public void setSpender(List<CSpender> spender) {
        this.spender = spender;
    }

    /**
     *
     * @param tafel
     */
    public void setTafel(List<CTafel> tafel) {
        this.tafel = tafel;
    }

    /**
     *
     * @param selectedID
     */
    public void setSelectedID(int selectedID) {
        this.selectedID = selectedID;
    }
}
