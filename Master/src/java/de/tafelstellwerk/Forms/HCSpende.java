package de.tafelstellwerk.Forms;

/**
 * Dies ist die Hilfsklasse für CSpende, um Date Felder aus Spring in Strings
 * aufzunehmen und später in Date format umzuwandeln
 *
 * @author gpaschke
 * @version 0.1
 */
public class HCSpende {

    private String HRueckmeldefrist;
    private String HMHD;
    private String HAbholfrist;

    /**
     * Erzeugt ein leeres Objekt der Hilfsklasse HCSpende
     */
    public HCSpende() {
    }

    /**
     * Erzeugt ein gefülltes Objekt der Hilfsklasse HCSpende
     *
     * @param HRueckmeldefrist
     * @param HMHD
     * @param HAbholfrist
     */
    public HCSpende(String HRueckmeldefrist, String HMHD, String HAbholfrist) {
        this.HRueckmeldefrist = HRueckmeldefrist;
        this.HMHD = HMHD;
        this.HAbholfrist = HAbholfrist;
    }

    //Getter
    /**
     * 
     * @return 
     */
    public String getHRueckmeldefrist() {
        return HRueckmeldefrist;
    }

    /**
     * 
     * @return 
     */
    public String getHMHD() {
        return HMHD;
    }

    /**
     * 
     * @return 
     */
    public String getHAbholfrist() {
        return HAbholfrist;
    }

    //Setter
    /**
     * 
     * @param HRueckmeldefrist 
     */
    public void setHRueckmeldefrist(String HRueckmeldefrist) {
        this.HRueckmeldefrist = HRueckmeldefrist;
    }

    /**
     * 
     * @param HMHD 
     */
    public void setHMHD(String HMHD) {
        this.HMHD = HMHD;
    }

    /**
     * 
     * @param HAbholfrist 
     */
    public void setHAbholfrist(String HAbholfrist) {
        this.HAbholfrist = HAbholfrist;
    }
}
