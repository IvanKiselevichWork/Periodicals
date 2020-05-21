package by.kiselevich.periodicals.dao.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void block(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.load(User.class, id);
            user.setAvailable(false);
            session.update(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void unblock(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.load(User.class, id);
            user.setAvailable(true);
            session.update(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User getUserById(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.load(User.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery("select u from User u where login = :login", User.class);
            query.setParameter("login", login);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery("select u from User u where login = :login and password = :password", User.class);
            query.setParameter("login", login);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
