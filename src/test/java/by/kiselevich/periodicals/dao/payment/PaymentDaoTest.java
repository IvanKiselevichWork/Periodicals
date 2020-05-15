package by.kiselevich.periodicals.dao.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class PaymentDaoTest {

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
    public void PaymentDaoTest1() throws DaoException {
        PaymentDao paymentDao = new PaymentDaoImpl(session);
        List<Payment> payments = paymentDao.getAllPayments();
        Assert.assertEquals(1018, payments.size());
    }
}
