package de.tafelstellwerk.Forms;

import de.tafelstellwerk.Model.CTafel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gpaschke
 */
public class CTafelListe {
    private List<CTafel> tafelliste = new ArrayList<>();

    /**
     * Konstruktor
     */
    public CTafelListe() {
    }

    
    //Getter Setter
    /**
     * 
     * @return 
     */
    public List<CTafel> getTafelliste() {
        return tafelliste;
    }

    /**
     * 
     * @param tafelliste 
     */
    public void setTafelliste(List<CTafel> tafelliste) {
        this.tafelliste = tafelliste;
    }
    
    
    
    
    
}
