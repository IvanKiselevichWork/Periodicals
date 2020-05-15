package by.kiselevich.periodicals.dao.editiontheme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EditionThemeDaoImpl implements EditionThemeDao {

    private Session session;

    public EditionThemeDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<EditionTheme> getAllEditionThemes() throws DaoException {
        try {
            return session.createQuery("select e from EditionTheme e", EditionTheme.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public EditionTheme getEditionThemeById(int id) throws DaoException {
        try {
            Query<EditionTheme> query = session.createQuery("select e from EditionTheme e where id = :id", EditionTheme.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
