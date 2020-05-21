package by.kiselevich.periodicals.dao.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class SubscriptionDaoTest {

    private static Session session;

    @BeforeClass
    public static void init() {
        session = new Configuration().configure().buildSessionFactory().openSession();
    }

    @AfterClass
    public static void deInit() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    @Test
    public void SubscriptionDaoTest1() throws DaoException {
        SubscriptionDao subscriptionDao = new SubscriptionDaoImpl();
        List<Subscription> subscriptions = subscriptionDao.getAllSubscriptions();
        Assert.assertEquals(1008, subscriptions.size());
    }
}
