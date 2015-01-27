/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CSpendenarchiv;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 */
public class CSpendenarchivDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param spendenarchiv 
     */
    public static void createSpendenarchiv(CSpendenarchiv spendenarchiv){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {      
            session.getTransaction().begin();
            session.saveOrUpdate(spendenarchiv);
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
     public static CSpendenarchiv getSpendenarchivById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CSpendenarchiv spendenarchiv = new CSpendenarchiv();
        try{
        session.beginTransaction();
        spendenarchiv = (CSpendenarchiv) session.get(CSpendenarchiv.class, id);
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spendenarchiv;
    }
    
    /**
     * Löschen des Objekts
     * @param spendenarchiv 
     */
    public void deleteSpendenarchiv(CSpendenarchiv spendenarchiv){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(spendenarchiv);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    /**
     * Auslesen des Archivs - alle Werte
     * @return 
     */
    public static List<CSpendenarchiv> getSpendenarchivListe(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CSpendenarchiv> liste = new ArrayList<>();
        //from CBenutzer benutzer # alte abfrage die alle Parameter beinhaltet
        
        try{
        session.beginTransaction();
        liste =  session.createQuery("from CSpendenarchiv").list();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        
        return liste;
     }
    
    /**
     * Auslesen des Archivs - alle Werte nach Spende
     * @return 
     */
    public static List<CSpendenarchiv> getArchivListeBySpende(int spendeID){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CSpendenarchiv> liste = new ArrayList<>();
        //from CBenutzer benutzer # alte abfrage die alle Parameter beinhaltet
        
        try{
        session.beginTransaction();
        liste =  session.createQuery("from CSpendenarchiv a where a.spende.spendeID = :spendeID").setParameter("spendeID", spendeID).list();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        
        return liste;
     }
    
    /**
     * Auslesen des Archivs - alle Werte nach Tafel
     * @return 
     */
    public static List<CSpendenarchiv> getArchivListeByTafel(int tafelID){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CSpendenarchiv> liste = new ArrayList<>();
        //from CBenutzer benutzer # alte abfrage die alle Parameter beinhaltet
        
        try{
        session.beginTransaction();
        liste =  session.createQuery("select a from CSpendenarchiv a where a.tafel.id = :tafelID").setParameter("tafelID", tafelID).list();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        
        return liste;
     }
    
}
