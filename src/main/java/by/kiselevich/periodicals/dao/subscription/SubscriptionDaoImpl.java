package by.kiselevich.periodicals.dao.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Subscription subscription) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(subscription);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Subscription subscription) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(subscription);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions() throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("from Subscription", Subscription.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Subscription getSubscriptionById(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.load(Subscription.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
