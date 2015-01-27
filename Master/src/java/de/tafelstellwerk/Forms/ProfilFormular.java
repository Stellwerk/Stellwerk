/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Forms;

import de.tafelstellwerk.Model.CAdresse;
import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CKontakt;
import de.tafelstellwerk.Model.CSpender;
import de.tafelstellwerk.Model.CTafel;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Klasse mit den Elementen des Profil Formulars
 *
 * @author Simon Lau
 */
public class ProfilFormular {

    @Valid
    CKontakt kontakt = new CKontakt();
    @Valid
    CAdresse adresse = new CAdresse();
    boolean checkAdresse = false;
    boolean checkKontakt = false;
    @Valid
    CBenutzer benutzer = new CBenutzer();
    CSpender spender = new CSpender();
    CTafel tafel = new CTafel();
    String rolle;
    @Pattern(regexp = "^$|^[A-Za-z0-9-!@$&?]{8,35}+$")
    @Size(min = 0, max = 35)
    String neuesPasswort;
    String bestNeuesPasswort;
    String passwortkontrolle;
    String nachricht;

    /**
     *
     * @return
     */
    public String getPasswortkontrolle() {
        return passwortkontrolle;
    }

    /**
     *
     * @param passwortkontrolle
     */
    public void setPasswortkontrolle(String passwortkontrolle) {
        this.passwortkontrolle = passwortkontrolle;
    }

    /**
     *
     * @return
     */
    public CKontakt getKontakt() {
        return kontakt;
    }

    /**
     *
     * @param kontakt
     */
    public void setKontakt(CKontakt kontakt) {
        this.kontakt = kontakt;
    }

    /**
     *
     * @return
     */
    public CAdresse getAdresse() {
        return adresse;
    }

    /**
     *
     * @param adresse
     */
    public void setAdresse(CAdresse adresse) {
        this.adresse = adresse;
    }

    /**
     *
     * @return
     */
    public boolean isCheckAdresse() {
        return checkAdresse;
    }

    /**
     *
     * @param checkAdresse
     */
    public void setCheckAdresse(boolean checkAdresse) {
        this.checkAdresse = checkAdresse;
    }

    /**
     *
     * @return
     */
    public boolean isCheckKontakt() {
        return checkKontakt;
    }

    /**
     *
     * @param checkKontakt
     */
    public void setCheckKontakt(boolean checkKontakt) {
        this.checkKontakt = checkKontakt;
    }

    /**
     *
     * @return
     */
    public CBenutzer getBenutzer() {
        return benutzer;
    }

    /**
     *
     * @param benutzer
     */
    public void setBenutzer(CBenutzer benutzer) {
        this.benutzer = benutzer;
    }

    /**
     *
     * @return
     */
    public CSpender getSpender() {
        return spender;
    }

    /**
     *
     * @param spender
     */
    public void setSpender(CSpender spender) {
        this.spender = spender;
    }

    /**
     *
     * @return
     */
    public CTafel getTafel() {
        return tafel;
    }

    /**
     *
     * @param tafel
     */
    public void setTafel(CTafel tafel) {
        this.tafel = tafel;
    }

    /**
     *
     * @return
     */
    public String getNeuesPasswort() {
        return neuesPasswort;
    }

    /**
     *
     * @param neuesPasswort
     */
    public void setNeuesPasswort(String neuesPasswort) {
        this.neuesPasswort = neuesPasswort;
    }

    /**
     *
     * @return
     */
    public String getBestNeuesPasswort() {
        return bestNeuesPasswort;
    }

    /**
     *
     * @param bestNeuesPasswort
     */
    public void setBestNeuesPasswort(String bestNeuesPasswort) {
        this.bestNeuesPasswort = bestNeuesPasswort;
    }

    /**
     *
     * @return
     */
    public String getRolle() {
        return rolle;
    }

    /**
     *
     * @param rolle
     */
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }

    /**
     *
     * @return
     */
    public String getNachricht() {
        return nachricht;
    }

    /**
     *
     * @param nachricht
     */
    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }
}
