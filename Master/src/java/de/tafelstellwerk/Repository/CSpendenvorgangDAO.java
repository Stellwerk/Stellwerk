/*
 * Datenbanktransferobjekt für die Klasse Spendenvorgang
 * hinzufügen
 * entfernen
 * aufruf nach ID
 */
package de.tafelstellwerk.Repository;

import de.tafelstellwerk.Model.CSpendenvorgang;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author gpaschke
 * @version 1.0
 */
public class CSpendenvorgangDAO {
    
    /**
     * Neues Objekt in Datenbank speichern
     * @param spendenvorgang 
     */
    public void createSpendenvorgang(CSpendenvorgang spendenvorgang){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.saveOrUpdate(spendenvorgang);
            session.getTransaction().commit();
        } catch (Exception e) {
           if (session.getTransaction() != null) session.getTransaction().rollback();
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    //Vorhandenes Objekt aus Datenbank lesen - nach ID
    public CSpendenvorgang getSpendenvorgangById(int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CSpendenvorgang spendenvorgang = new CSpendenvorgang();
        try{
            session.getTransaction().begin();
            //DB Befehl
            spendenvorgang = (CSpendenvorgang) session.get(CSpendenvorgang.class, id);		
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spendenvorgang;
    }
    
    //Vorhandenes Objekt aus Datenbank löschen - nach Benutzer
    public static void deleteSpendenvorgang(CSpendenvorgang spendenvorgang){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            session.getTransaction().begin();
            session.delete(spendenvorgang);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
    }
    
    public static List<CSpendenvorgang> getMeineSpenden(String eMail){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List <CSpendenvorgang> spendenvorgangsliste;
        try{
            session.getTransaction().begin();
            //DB Befehl
            spendenvorgangsliste = session.getNamedQuery("getMeineSpenden").setString("eMail", eMail).setParameter("status", CSpendenvorgang.StatusEnum.ignoriert ).list();
            if (spendenvorgangsliste.size() <= 0){
                System.out.println("Spendenvorgangsliste leer!");
            }
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spendenvorgangsliste;
    }
    
    public static List<CSpendenvorgang> getSpendenvorgangListe(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List <CSpendenvorgang> spendenvorgangsliste;
        try{
            session.getTransaction().begin();
            //DB Befehl
            spendenvorgangsliste = session.createQuery("from CSpendenvorgang").list();
            if (spendenvorgangsliste.size() <= 0){
                System.out.println("Spendenvorgangsliste leer!");
            }
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spendenvorgangsliste;
    }
    
    public static List<CSpendenvorgang> getSpendenvorgangListeBySpende(int spendeID){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List <CSpendenvorgang> spendenvorgangsliste;
        try{
            session.getTransaction().begin();
            //DB Befehl
            spendenvorgangsliste = session.createQuery("from CSpendenvorgang sv where sv.spende.spendeID = :spende ").setParameter("spende", spendeID).list();
            if (spendenvorgangsliste.size() <= 0){
                System.out.println("Spendenvorgangsliste leer!");
            }
        } catch (Exception e) {
           throw e;
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spendenvorgangsliste;
    }
    
    public static CSpendenvorgang getSpendenvorgangBySpendeAndTafel(int spendeID, int tafelID){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CSpendenvorgang spendenvorgang = new CSpendenvorgang();
        try{
            session.getTransaction().begin();
            //DB Befehl
            //select new CBenutzer(benutzerID, vorname, nachname, eMail, rolle) from CBenutzer ORDER BY rolle
            spendenvorgang = (CSpendenvorgang) session.createQuery("select new CSpendenvorgang(spendenvorgangsID, anteil, tafel) from CSpendenvorgang sv where sv.spende.spendeID = :spende and sv.tafel.id = :tafel ").setParameter("spende", spendeID).setParameter("tafel", tafelID).uniqueResult();
        } catch (Exception e) {
            System.out.println("Spendenvorgang nicht gefunden - anhand Spende und Tafel");
        } finally{ 
            try{ if (session != null) session.close();} catch (Exception ex) {}
        }
        return spendenvorgang;
    }
    
}
