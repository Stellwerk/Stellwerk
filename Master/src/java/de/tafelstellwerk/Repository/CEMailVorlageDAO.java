/*
 * 
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CBenutzer;
import de.tafelstellwerk.Model.CEMailVorlage;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 * @since 19.6.13
 * @version 1.1
 */
public class CEMailVorlageDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param eMailVorlage 
     */
    public void createEMailVorlage(CEMailVorlage eMailVorlage) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.saveOrUpdate(eMailVorlage);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }       
    }

    /**
     * Auslesen des Objektes anhand seiner ID
     * @param id
     * @return 
     */
    public CEMailVorlage getEMailVorlageById(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CEMailVorlage eMailVorlage = new CEMailVorlage();
        try{
            session.getTransaction().begin();
            //DB Befehl
            eMailVorlage = (CEMailVorlage) session.get(CEMailVorlage.class, id);		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
       return eMailVorlage;
    }
    
    /**
     * Auslesen des Objektes anhand des Betreffs
     * @param betreff
     * @return 
     */
    public CEMailVorlage getEMailVorlageByBetreff(String betreff) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CEMailVorlage eMailVorlage = new CEMailVorlage();
        try{
        session.beginTransaction();
        eMailVorlage = (CEMailVorlage) session.createQuery("from CEMailVorlage where Betreff ='"+ betreff +"'").uniqueResult();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return eMailVorlage;
    }

    /**
     * Löschen des Objekte
     * @param eMailVorlage 
     */
    public void deleteEMailVorlage(CEMailVorlage eMailVorlage) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(eMailVorlage);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    /**
     * Auslesen aller Objekte
     * @return 
     */
    public static List<CEMailVorlage> getEMailVorlagenListe(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CEMailVorlage> liste;
        try{
        session.beginTransaction();
        liste = session.createQuery("from CEMailVorlage").list();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return liste;
    }
}
