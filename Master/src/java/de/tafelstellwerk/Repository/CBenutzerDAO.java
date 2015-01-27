/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CBenutzer;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 */
public class CBenutzerDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param benutzer 
     */
    public void createBenutzer(CBenutzer benutzer){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            //DB Befehl
            session.saveOrUpdate(benutzer);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    /**
     * Gibt den Benutzer anhand der eMail Adresse zurück, andernfalls ein leeres Objekt
     * @param eMail
     * @return 
     */
    public CBenutzer getBenutzerByEMail(String eMail) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CBenutzer benutzer = new CBenutzer();
        try{
        session.beginTransaction();
        benutzer = (CBenutzer) session.createQuery("from CBenutzer where EMail ='"+ eMail +"'").uniqueResult();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return benutzer;
    
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        List <CBenutzer> Liste;
//        try{
//        session.beginTransaction();
//        Liste = session.createQuery("from CBenutzer where eMail ='"+ eMail +"'").list();
//        } catch (Exception e){
//            throw e;
//        } finally{ 
//            try{ if (session != null) session.close();} catch (Exception ex) {}
//        }
//        return Liste == null || Liste.size() <= 0 ? null: (CBenutzer) Liste.get(0);
    }
    
    /**
     * beinhaltet nur vorname, nachname, Email, Rolle
     * @return 
     */
    public List<CBenutzer> getBenutzerListeIVNER(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CBenutzer> liste = new ArrayList<>();
        //from CBenutzer benutzer # alte abfrage die alle Parameter beinhaltet
        
        try{
        session.beginTransaction();
        liste =  session.createQuery("select new CBenutzer(benutzerID, vorname, nachname, eMail, rolle) from CBenutzer ORDER BY rolle").list(); // benötigt neuen Konstruktor im CBenutzer, nur mit den Variablen
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        
        return liste;
     }
    
    /**
     * Liste aller Benutzer 
     * @return List<CBenutzer>
     */
    public List<CBenutzer> getBenutzerListe(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CBenutzer> liste = new ArrayList<>();
        //from CBenutzer benutzer # alte abfrage die alle Parameter beinhaltet
        
        try{
        session.beginTransaction();
        liste =  session.createQuery("from CBenutzer ORDER BY rolle").list();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        
        return liste;
     }
    
    
    
    /**
     * Auslesen des Objektes anhand seiner ID
     * @param id
     * @return 
     */
    public CBenutzer getBenutzerById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CBenutzer benutzer = new CBenutzer();
        try{
            session.getTransaction().begin();
            //DB Befehl
            benutzer = (CBenutzer) session.get(CBenutzer.class, id);		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return benutzer;
    }
    
    /**
     * Liste aller Benutzer mit der angegebenen Rolle
     * @return List<CBenutzer> mit Koordinator
     */
    public static List<CBenutzer> getBenutzerListeByRolle(CBenutzer.RolleEnum rolle) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CBenutzer> liste;
        try{
        session.beginTransaction();
        liste = session.createQuery("from CBenutzer where rolle = :rolle").setParameter("rolle", rolle).list();
        } catch (Exception e){
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return liste;
    
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        List <CBenutzer> Liste;
//        try{
//        session.beginTransaction();
//        Liste = session.createQuery("from CBenutzer where eMail ='"+ eMail +"'").list();
//        } catch (Exception e){
//            throw e;
//        } finally{ 
//            try{ if (session != null) session.close();} catch (Exception ex) {}
//        }
//        return Liste == null || Liste.size() <= 0 ? null: (CBenutzer) Liste.get(0);
    }

    
    /**
     * Löschen des Objektes anhand des Objektes
     * @param benutzer 
     */
    public void deleteBenutzer(CBenutzer benutzer){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(benutzer);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
           session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
}
