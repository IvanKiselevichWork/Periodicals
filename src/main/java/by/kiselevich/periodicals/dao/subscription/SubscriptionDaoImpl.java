package by.kiselevich.periodicals.dao.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SubscriptionDaoImpl implements SubscriptionDao {

    private Session session;

    public SubscriptionDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void add(Subscription subscription) throws DaoException {
        try {
            session.save(subscription);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Subscription subscription) throws DaoException {
        try {
            session.update(subscription);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions() throws DaoException {
        try {
            return session.createQuery("select s from Subscription s", Subscription.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Subscription getSubscriptionById(int id) throws DaoException {
        try {
            Query<Subscription> query = session.createQuery("select s from Subscription s where id = :id", Subscription.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
