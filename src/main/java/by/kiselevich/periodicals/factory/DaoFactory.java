package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.dao.edition.EditionDao;
import by.kiselevich.periodicals.dao.edition.EditionDaoImpl;
import by.kiselevich.periodicals.dao.editiontheme.EditionThemeDao;
import by.kiselevich.periodicals.dao.editiontheme.EditionThemeDaoImpl;
import by.kiselevich.periodicals.dao.editiontype.EditionTypeDao;
import by.kiselevich.periodicals.dao.editiontype.EditionTypeDaoImpl;
import by.kiselevich.periodicals.dao.payment.PaymentDao;
import by.kiselevich.periodicals.dao.payment.PaymentDaoImpl;
import by.kiselevich.periodicals.dao.subscription.SubscriptionDao;
import by.kiselevich.periodicals.dao.subscription.SubscriptionDaoImpl;
import by.kiselevich.periodicals.dao.user.UserDao;
import by.kiselevich.periodicals.dao.user.UserDaoImpl;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class DaoFactory {

    private static final Session session = new Configuration().configure().buildSessionFactory().openSession();
    private final UserDao userDao;
    private final EditionDao editionDao;
    private final PaymentDao paymentDao;
    private final SubscriptionDao subscriptionDao;
    private final EditionThemeDao editionThemeDao;
    private final EditionTypeDao editionTypeDao;

    private DaoFactory() {
        userDao = new UserDaoImpl(session);
        editionDao = new EditionDaoImpl(session);
        paymentDao = new PaymentDaoImpl(session);
        subscriptionDao = new SubscriptionDaoImpl(session);
        editionThemeDao = new EditionThemeDaoImpl(session);
        editionTypeDao = new EditionTypeDaoImpl(session);
    }

    private static class DaoFactoryHolder {
        private static final DaoFactory INSTANCE = new DaoFactory();
    }

    public static DaoFactory getInstance() {
        return DaoFactoryHolder.INSTANCE;
    }

    public static Session getSession() {
        return session;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public EditionDao getEditionDao() {
        return editionDao;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public SubscriptionDao getSubscriptionDao() {
        return subscriptionDao;
    }

    public EditionThemeDao getEditionThemeDao() {
        return editionThemeDao;
    }

    public EditionTypeDao getEditionTypeDao() {
        return editionTypeDao;
    }

    public static void closeSession() {
        if (session != null) {
            session.close();
        }
    }

}
