package de.tafelstellwerk.Forms;

import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Model.CSpende;
import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Model.CTafel;
import javax.validation.Valid;

/**
 * Hilfsklasse für das Spendenformular
 *
 * @author gpaschke
 */
public class SpendenFormular {

    @Valid
    CSpende spende = new CSpende();
    @Valid
    CAdresse adresse = new CAdresse();
    @Valid
    CKontakt kontakt = new CKontakt();
    CSpender spender = new CSpender();
    CTafel tafel = new CTafel();
    //HCSpende HSpende = new HCSpende();
    boolean checkAdresse = false;
    boolean checkKontakt = false;

    /**
     * Erzeugt ein leeres Objekt der Klasse SpendenFormular
     */
    public SpendenFormular() {
    }

    //Getter
    public CSpende getSpende() {
        return spende;
    }

    public CAdresse getAdresse() {
        return adresse;
    }

    public CKontakt getKontakt() {
        return kontakt;
    }

    public boolean isCheckAdresse() {
        return checkAdresse;
    }

    public boolean isCheckKontakt() {
        return checkKontakt;
    }

//  public HCSpende getHSpende() {
//      return HSpende;
//  }

    //Setter
    public void setSpende(CSpende Spende) {
        this.spende = Spende;
    }

    public void setAdresse(CAdresse Adresse) {
        this.adresse = Adresse;
    }

    public void setKontakt(CKontakt Kontakt) {
        this.kontakt = Kontakt;
    }

//  public void setHSpende(HCSpende HSpende) {
//      this.HSpende = HSpende;
//  }

    public void setCheckAdresse(boolean CheckAdresse) {
        this.checkAdresse = CheckAdresse;
    }

    public CSpender getSpender() {
        return spender;
    }

    public void setSpender(CSpender spender) {
        this.spender = spender;
    }

    public CTafel getTafel() {
        return tafel;
    }

    public void setTafel(CTafel tafel) {
        this.tafel = tafel;
    }

    public void setCheckKontakt(boolean CheckKontakt) {
        this.checkKontakt = CheckKontakt;
    }
}
