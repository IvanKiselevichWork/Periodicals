package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.exception.SessionFactoryInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static final Logger LOG = LogManager.getLogger(HibernateSessionFactory.class);
    private static boolean isSessionFactoryInitialized;
    private static final SessionFactory SESSION_FACTORY;
    static {
        try {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
            isSessionFactoryInitialized = true;
        } catch (Exception e) {
            LOG.error(e);
            throw new SessionFactoryInitializationException("Session factory not initialized");
        }
    }

    private HibernateSessionFactory() {

    }

    public static boolean isSessionFactoryInitialized() {
        return isSessionFactoryInitialized;
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
