package de.tafelstellwerk.Model;


import de.tafelstellwerk.Repository.CBenutzerDAO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.util.DigestUtils;

/**
 * Benutzer zur Benutzerverwaltung, Login, kann Rollen annehmen
 *
 * @author Lau, Simon v.1.0
 * @author gpaschke v.1.1 - Datenbank+ Enum
 * @author Patrick Niemann
 *
 * @param benutzerID
 * @param vorname
 * @param nachname
 * @param eMail
 * @param passwort
 * @param rolle
 * @param aktiviert
 * @param spender
 * @param tafel
 */
@Entity
@Table(name = "BENUTZER")
public class CBenutzer implements Serializable {

// Enum Felder - neuer TYP muss public!
    public enum RolleEnum {

        Administrator, Koordinator, Spender, Tafel
    };
//Variablendeklaration
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int benutzerID;
    @Size(min = 1, max = 30)
    @Column(length = 30)
    private String vorname;
    @Size(min = 1, max = 30)
    @Column(length = 30)
    private String nachname;
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}")
    @Column(unique = true)
    private String eMail;
    @Pattern(regexp = "^[A-Za-z0-9-!@$&?]+$")
    @Size(min = 3, max = 35) // Später 8 Zeichen min!
    @Column(length = 32)
    private String passwort;
    private RolleEnum rolle;
    private Boolean aktiviert;
    @ManyToOne
    private CSpender spender;
    @ManyToOne
    private CTafel tafel;

// Konstruktor zum Erstellen der Klasse
    /**
     * Erstellt leeres Benutzer Objekt
     */
    public CBenutzer() {
    }

    //Für die DB Abfrage im DAO zur Auflistung der Benutzer
    public CBenutzer(int benutzerID, String vorname, String nachname, String eMail, RolleEnum rolle) {
        this.benutzerID = benutzerID;
        this.vorname = vorname;
        this.nachname = nachname;
        this.eMail = eMail;
        this.rolle = rolle;
    }

    /**
     * Erstellt befülltes Benutzer Objekt
     *
     * @param benutzerID
     * @param vorname
     * @param nachname
     * @param eMail
     * @param passwort
     * @param rolle
     * @param aktiviert
     * @param spender
     * @param tafel
     */
    public CBenutzer(int benutzerID, String vorname, String nachname, String eMail, String passwort, RolleEnum rolle, Boolean aktiviert, CSpender spender, CTafel tafel) {
        this.benutzerID = benutzerID;
        this.vorname = vorname;
        this.nachname = nachname;
        this.eMail = eMail;
        this.passwort = passwort;
        this.rolle = rolle;
        this.aktiviert = aktiviert;
        this.spender = spender;
        this.tafel = tafel;
    }

    //Methoden
    /**
     * Methode Greift auf die DB zu und gibt den Benutzer zurück, der hinter der
     * eMail adresse liegt - für Login
     *
     * @param eMail
     */
    public void getBenutzer(String EMail) {
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
        CBenutzer temp = (CBenutzer) benutzerDAO.getBenutzerByEMail(EMail);
        this.benutzerID = temp.benutzerID;
        this.vorname = temp.vorname;
        this.nachname = temp.nachname;
        this.eMail = temp.eMail;
        this.passwort = temp.passwort;
        this.aktiviert = temp.aktiviert;
        this.rolle = temp.rolle;
        this.spender = temp.spender;
        this.tafel = temp.tafel;
    }

    public void getBenutzer(int ID) {
        CBenutzerDAO benutzerDAO = new CBenutzerDAO();
        CBenutzer temp = (CBenutzer) benutzerDAO.getBenutzerById(ID);
        this.benutzerID = temp.benutzerID;
        this.vorname = temp.vorname;
        this.nachname = temp.nachname;
        this.eMail = temp.eMail;
        this.passwort = temp.passwort;
        this.aktiviert = temp.aktiviert;
        this.rolle = temp.rolle;
        this.spender = temp.spender;
        this.tafel = temp.tafel;
    }

    /**
     * Methode erstellt aus einem String dessen MD5 Wert für das Speichern des
     * Passworts in der DB
     *
     * @param password
     * @return
     */
    public static String hashPasswort(String password) {
        byte[] hash = null;
        if (password == null) {
            System.out.println("## Fehler! Methode HashPasswort. Eingegebener String-Passwort = null. MD5 Wert kann nicht ermittelt werden.");
            return null;
        }

        try {
            hash = password.getBytes("UTF-8");
        } catch (Exception e) {
        }

        return DigestUtils.md5DigestAsHex(hash);

//        if (password != null) {
//            String hashword = null;
//            try {
//                MessageDigest md5 = MessageDigest.getInstance("MD5");
//                md5.update(password.getBytes());
//                BigInteger hash = new BigInteger(1, md5.digest());
//                hashword = hash.toString(16);
//            } catch (NoSuchAlgorithmException nsae) {
//                // ignore
//            }
//            return hashword;
//        } else {
//            System.out.println("## Fehler! Methode HashPasswort. Eingegebener String-Passwort = null. MD5 Wert kann nicht ermittelt werden.");
//            return null;
//        }
    }

    public void hashPasswort() {
        this.passwort = hashPasswort(passwort);
    }

//Getter
    public int getBenutzerID() {
        return benutzerID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getEMail() {
        return eMail;
    }

    public String getPasswort() {
        return passwort;
    }

    public RolleEnum getRolle() {
        return rolle;
    }

    public CSpender getSpender() {
        return spender;
    }

    public CTafel getTafel() {
        return tafel;
    }

    public Boolean getAktiviert() {
        return aktiviert;
    }

//Setter
    public void setBenutzerID(int BenutzerID) {
        this.benutzerID = BenutzerID;
    }

    public void setVorname(String Vorname) {
        this.vorname = Vorname;
    }

    public void setNachname(String Nachname) {
        this.nachname = Nachname;
    }

    public void setEMail(String EMail) {
        this.eMail = EMail;
    }

    public void setPasswort(String Passwort) {
        this.passwort = Passwort;
    }

    public void setHashPasswort(String Passwort) {
        this.passwort = hashPasswort(Passwort);
    }

    public void setRolle(RolleEnum Rolle) {
        this.rolle = Rolle;
    }

    public void setSpender(CSpender Spender) {
        this.spender = Spender;
    }

    public void setTafel(CTafel Tafel) {
        this.tafel = Tafel;
    }

    public void setAktiviert(Boolean Aktiviert) {
        this.aktiviert = Aktiviert;
    }

    //TO String
    @Override
    public String toString() {
        return "CBenutzer{" + "benutzerID=" + benutzerID + ", vorname=" + vorname + ", nachname=" + nachname + ", eMail=" + eMail + ", passwort=" + passwort + ", rolle=" + rolle + ", aktiviert=" + aktiviert + ", spender=" + spender + ", tafel=" + tafel + '}';
    }
}
