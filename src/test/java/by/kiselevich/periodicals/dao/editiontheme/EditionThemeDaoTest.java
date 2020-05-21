package by.kiselevich.periodicals.dao.editiontheme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class EditionThemeDaoTest {

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
    public void EditionThemeDaoTest1() throws DaoException {
        EditionThemeDao editionThemeDao = new EditionThemeDaoImpl();
        List<EditionTheme> editionThemes = editionThemeDao.getAllEditionThemes();
        Assert.assertEquals(24, editionThemes.size());
    }
}
