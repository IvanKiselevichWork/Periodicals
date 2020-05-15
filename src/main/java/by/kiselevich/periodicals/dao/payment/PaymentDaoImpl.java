package by.kiselevich.periodicals.dao.payment;

import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;

import java.util.List;

public class PaymentDaoImpl implements PaymentDao {

    private Session session;

    public PaymentDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void add(Payment payment) throws DaoException {
        try {
            session.save(payment);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Payment> getAllPayments() throws DaoException {
        try {
            return session.createQuery("select p from Payment p", Payment.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
