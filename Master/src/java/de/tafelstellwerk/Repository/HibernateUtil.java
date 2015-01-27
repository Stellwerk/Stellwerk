/*
 * Diese Datei stellt die Sessions zum Datenbankzugriff zur 
 * verfügung und greift auf die Konfiguration xml im default package zurück
 */
package de.tafelstellwerk.Repository;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Gerard
 */
public class HibernateUtil {

//    private static SessionFactory sessionFactory;
//private static ServiceRegistry serviceRegistry;
//
//private static SessionFactory configureSessionFactory() throws HibernateException {
//    Configuration configuration = new Configuration();
//    configuration.configure();
//    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
//    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//    return sessionFactory;
//}

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;
    private static ServiceRegistryBuilder serviceRegistryBuilder;
    
    private static SessionFactory configureSessionFactory() throws HibernateException {
        try{
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
        serviceRegistry = serviceRegistryBuilder.buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex){
            System.out.println("-------------------------------------");
            System.out.println("Hibernate Fehler!...");
            System.err.println("Initial SessionFactory creation failed." + ex);
        }
    return sessionFactory;
    }
    
    
    
//    static {
//        try {
//            // Create the SessionFactory from standard (hibernate.cfg.xml) 
//            // config file.
//            Configuration configuration = new Configuration();
//            configuration.configure();
//            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
//            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//        } catch (Throwable ex) {
//            // Log the exception. 
//            System.err.println("Initial SessionFactory creation failed." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
    
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){sessionFactory = configureSessionFactory();}
        return sessionFactory;
    }
}
