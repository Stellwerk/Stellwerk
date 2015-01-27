/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CAllgemeines;
import de.tafelstellwerk.Model.CBenutzer;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 */
public class CAllgemeinesDAO {
    /**
     * Neues Objekt in Datenbank speichern
     * @param allgemeines 
     */
    public static void createAllgemeines(CAllgemeines allgemeines){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {      
            session.getTransaction().begin();
            session.saveOrUpdate(allgemeines);
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
     public static CAllgemeines getAllgemeinesById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CAllgemeines allgemeines = new CAllgemeines();
        try{
        session.beginTransaction();
        allgemeines = (CAllgemeines) session.get(CAllgemeines.class, id);
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return allgemeines;
    }
    
    /**
     * Löschen des Objektes anhand seiner ID
     * @param allgemeines 
     */
    public void deleteAllgemeines(CAllgemeines allgemeines){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(allgemeines);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    /**
     * Auslesen aller Objekte und Rückgabe als Liste
     * @return 
     */
    public static List<CAllgemeines> getAllgemeinesListe(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CAllgemeines> liste = new ArrayList<>();
        //from CBenutzer benutzer # alte abfrage die alle Parameter beinhaltet
        
        try{
        session.beginTransaction();
        liste =  session.createQuery("from CAllgemeines").list();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        
        return liste;
     }
    
}
