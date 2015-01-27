/*
 * Datenbanktransferobjekt für die Klasse Spender
 * hinzufügen
 * entfernen
 * aufruf nach ID
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CSpender;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author gpaschke
 */
public class CSpenderDAO {
    /**
     * Neues Objekt in Datenbank speichern
     * @param spender 
     */
    public void createSpender(CSpender spender){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.saveOrUpdate(spender);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    //Vorhandenes Objekt aus Datenbank lesen - nach ID
    public static CSpender getSpenderById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CSpender spender = new CSpender();
        try{
            session.getTransaction().begin();
            //DB Befehl
            spender = (CSpender) session.get(CSpender.class, id);		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spender;
    }
    
    //Vorhandenes Objekt aus Datenbank löschen - nach Benutzer
    public void deleteSpender(CSpender spender){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(spender);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    public static List<CSpender> getSpenderListe(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CSpender> liste;
        try{
            session.getTransaction().begin();
            //DB Befehl
            liste = session.createQuery("from CSpender").list();		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return liste;
    }
}
