package by.kiselevich.periodicals.dao.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private Session session;

    public UserDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void add(User user) throws DaoException {
        try {
            session.save(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try {
            session.update(user);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void block(int id) throws DaoException {
        try {
            Query<User> query = session.createQuery("update User set available = 0 where id = :id", User.class);
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void unblock(int id) throws DaoException {
        try {
            Query<User> query = session.createQuery("update User set available = 1 where id = :id", User.class);
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        try {
            return session.createQuery("select u from User u", User.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User getUserById(int id) throws DaoException {
        try {
            Query<User> query = session.createQuery("select u from User u where id = :id", User.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        try {
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
            Query<User> query = session.createQuery("select u from User u where login = :login and password = :password", User.class);
            query.setParameter("login", login);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
