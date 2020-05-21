package by.kiselevich.periodicals.dao.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class EditionTypeDaoTest {

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
    public void EditionTypeDaoTest1() throws DaoException {
        EditionTypeDao editionTypeDao = new EditionTypeDaoImpl();
        List<EditionType> editionTypes = editionTypeDao.getAllEditionTypes();
        Assert.assertEquals(2, editionTypes.size());
    }
}
