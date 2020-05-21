package by.kiselevich.periodicals.dao.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

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
    public void UserDaoTest1() throws DaoException {
        UserDao userDao = new UserDaoImpl();
        List<User> users = userDao.getAllUsers();
        Assert.assertEquals(1000, users.size());
    }

    @Test
    public void UserDaoTest2() throws DaoException {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByLogin("user1");
        Assert.assertEquals("user@periodicals.com", user.getEmail());
    }

    @Test
    public void UserDaoTest3() throws DaoException {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByLogin("user1");
        Assert.assertEquals(5, user.getPayments().size());
    }

    @Test
    public void UserDaoTest4() throws DaoException {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUserByLogin("user1");
        Assert.assertEquals(1004, user.getSubscriptions().size());
    }
}
