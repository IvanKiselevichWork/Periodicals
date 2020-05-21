package by.kiselevich.periodicals.dao.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class EditionDaoTest {

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
    public void EditionDaoTest1() throws DaoException {
        EditionDao editionDao = new EditionDaoImpl();
        Assert.assertEquals(740, editionDao.getAllEditions().size());
    }

    @Test
    public void EditionDaoTest2() throws DaoException {
        EditionDao editionDao = new EditionDaoImpl();
        List<Edition> editionList = editionDao.getEditionsByNameAndTypeAndThemeAndBlockage(null, null, 1, null);
        Assert.assertEquals(3, editionList.size());
    }

    @Test
    public void EditionDaoTest3() throws DaoException {
        EditionDao editionDao = new EditionDaoImpl();
        List<Edition> editionList = editionDao.getEditionsByNameAndTypeAndThemeAndBlockage(null, 1, null, null);
        Assert.assertEquals(278, editionList.size());
    }

    @Test
    public void EditionDaoTest4() throws DaoException {
        EditionDao editionDao = new EditionDaoImpl();
        List<Edition> editionList = editionDao.getEditionsByNameAndTypeAndThemeAndBlockage("1", 1, 1, null);
        Assert.assertEquals(0, editionList.size());
    }
}
