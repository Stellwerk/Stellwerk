/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CAdresse;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 */
public class CAdresseDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param adresse 
     */
    public void createAdresse(CAdresse adresse){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {      
            session.getTransaction().begin();
            session.saveOrUpdate(adresse);
            session.getTransaction().commit(); 
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        
    }

    
    /**
     * Auslesen der Adresse anhand der ID
     * @param id
     * @return 
     */
     public CAdresse getAdresseById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CAdresse adresse = new CAdresse();
        try{
        session.beginTransaction();
        adresse = (CAdresse) session.get(CAdresse.class, id);
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return adresse;
    }
    
    /**
     * Löschen der Adress anhand des Adressobjektes
     * @param adresse 
     */
    public void deleteAdresse(CAdresse adresse){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(adresse);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
}
