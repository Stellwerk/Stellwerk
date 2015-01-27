/*
 * Datenbanktransferobjekt für die Klasse Spende
 * hinzufügen
 * entfernen
 * aufruf nach ID
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CSpende;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 * @version 1.0
 */
public class CSpendeDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param spende 
     */
    public void createSpende(CSpende spende){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.saveOrUpdate(spende);
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
    public static CSpende getSpendeById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CSpende spende = new CSpende();
        try{
            session.getTransaction().begin();
            //DB Befehl
            spende = (CSpende) session.get(CSpende.class, id);		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spende;
    }
    
    /**
     * Löschen des Objekts
     * @param spende 
     */
    public static void deleteSpende(CSpende spende){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(spende);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    

    //Weitere Methoden
    
    /**
     * Liest die letzte Spende des Benutzers anhand seiner eMail adresse aus
     * @param eMail
     * @return CSpende
     */
    public CSpende getLastSpendeByEMail(String eMail){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CSpende spende = new CSpende();
        try{
            session.getTransaction().begin();
            //DB Befehl
            Query query = session.getNamedQuery("getLastSpendeByEMail").setString("eMail", eMail);
            query.setMaxResults(1);     //Begrenzt die Anzahl der ausgewählten Einträge auf 1
            List <CSpende> SpendenListe= query.list();
            if (SpendenListe.size() > 0){
                spende = (CSpende) SpendenListe.get(0);     
            }
            System.out.println(spende.toString());
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spende;
    }   
    
    /**
     * Auslesen aller Spenden mit dem Status <> Verteilt und mit der angegebenen EMail Adresse im Benutzer der Spende
     * @param eMail
     * @return 
     */
    public List<CSpende> getMeineSpendenByEMail(String eMail){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CSpende> liste;
        try{
            session.getTransaction().begin();
            //DB Befehl
            liste = session.getNamedQuery("getMeineSpendenByEMail").setString("eMail", eMail).setParameter("status", CSpende.StatusEnum.Verteilt).list();
            if (liste.size() <= 0){
                System.out.println("Liste mit Meine Spenden war leer!");   
            }
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return liste;
    }
    
    /**
     * Auslesen aller Spenden mit dem angegebenen Status
     * @param status
     * @return 
     */
    public static List<CSpende> getSpendenByStatus(CSpende.StatusEnum status){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<CSpende> liste;
        try{
            session.getTransaction().begin();
            //DB Befehl
            liste = session.getNamedQuery("getSpendenByStatus").setParameter("status", status).list();
            if (liste.size() <= 0){
                System.out.println("Liste mit Spenden war leer! Erfragter Status:" + status);   
            }
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return liste;
    }
    
    
    
    
    
    
    
}
