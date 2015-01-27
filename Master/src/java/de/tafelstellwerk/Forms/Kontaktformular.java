package de.tafelstellwerk.Forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Klasse mit dem Elementen des Kontaktformulars
 *
 * @author Patrick
 */
public class Kontaktformular {

    @Size(min = 1, max = 30)
    String name;
    String strasse;
    @Pattern(regexp = "^$|[0-9]{5}")
    String plz;
    @Pattern(regexp = "^$|^[\\p{L}A-Za-z- ]+$")
    String ort;
    @Pattern(regexp = "^$|^[0-9+-/() ]+$")
    String tel;
    @Pattern(regexp = "^$|^[0-9+-/() ]+$")
    String fax;
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}")
    String email;
    @Size(min = 1)
    String text;

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     *
     * @param strasse
     */
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    /**
     *
     * @return
     */
    public String getPlz() {
        return plz;
    }

    /**
     *
     * @param plz
     */
    public void setPlz(String plz) {
        this.plz = plz;
    }

    /**
     *
     * @return
     */
    public String getOrt() {
        return ort;
    }

    /**
     *
     * @param ort
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    /**
     *
     * @return
     */
    public String getTel() {
        return tel;
    }

    /**
     *
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     *
     * @return
     */
    public String getFax() {
        return fax;
    }

    /**
     *
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }
}
