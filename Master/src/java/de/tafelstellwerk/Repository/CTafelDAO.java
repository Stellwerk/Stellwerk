/*
 * Datenbanktransferobjekt für die Klasse Tafel
 * hinzufügen
 * entfernen
 * aufruf nach ID
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CTafel;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 * @version 1.0
 */
public class CTafelDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param tafel 
     */
    public void createTafel(CTafel tafel){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.saveOrUpdate(tafel);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    //Vorhandenes Objekt aus Datenbank lesen - nach ID
    public static CTafel getTafelById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CTafel tafel = new CTafel();
        try{
            session.getTransaction().begin();
            //DB Befehl
            tafel = (CTafel) session.get(CTafel.class, id);		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return tafel;
    }
    
    //Vorhandenes Objekt aus Datenbank löschen - nach Benutzer
    public void deleteSpende(CTafel tafel){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(tafel);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    public static List<CTafel> getTafelListe(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CTafel> liste;
        try{
            session.getTransaction().begin();
            //DB Befehl
            liste = session.createQuery("from CTafel").list();		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return liste;
    }
     
    /**
     * selectiert alle Tafeln, die im Spendenvorgang der gefragten zugeordneten spende in verbindung stehen und dessen anteil < 0 ist
     * @param spendenID
     * @return List<CTafel>
     */
    public static List<CTafel> getTafelListeAusgewaehlt(int spendenID){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CTafel> liste;
        try{
            session.getTransaction().begin();
            //DB Befehl select v from CBenutzer b, CSpendenvorgang v where b.eMail = :eMail and v.tafel = b.tafel ORDER BY v.spendenvorgangsID DESC
            liste = session.createQuery("select sv.tafel from CTafel t, CSpendenvorgang sv where sv.spende.spendeID = :sid and sv.tafel.id = t.id and sv.anteil <> 0.0 order by t.tafelname ASC").setParameter("sid", spendenID).list();		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return liste;
    }
}
