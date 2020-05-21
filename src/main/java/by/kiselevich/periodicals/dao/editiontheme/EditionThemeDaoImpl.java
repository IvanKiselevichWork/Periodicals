package by.kiselevich.periodicals.dao.editiontheme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EditionThemeDaoImpl implements EditionThemeDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<EditionTheme> getAllEditionThemes() throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("from EditionTheme", EditionTheme.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public EditionTheme getEditionThemeById(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.load(EditionTheme.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
