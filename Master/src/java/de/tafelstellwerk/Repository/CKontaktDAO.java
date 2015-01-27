/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CKontakt;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 */
public class CKontaktDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param kontakt 
     */
    public void createKontakt(CKontakt kontakt){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.saveOrUpdate(kontakt);
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
    public CKontakt getKontaktById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CKontakt kontakt = new CKontakt();
        try{
            session.getTransaction().begin();
            //DB Befehl
            kontakt = (CKontakt) session.get(CKontakt.class, id);
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return kontakt;
    }
    
    /**
     * Löschen des Objektes
     * @param kontakt 
     */
    public void deleteKontakt(CKontakt kontakt){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(kontakt);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
}
