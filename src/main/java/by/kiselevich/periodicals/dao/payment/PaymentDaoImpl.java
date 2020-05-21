package by.kiselevich.periodicals.dao.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDaoImpl implements PaymentDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Payment payment) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(payment);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Payment> getAllPayments() throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("from Payment", Payment.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
