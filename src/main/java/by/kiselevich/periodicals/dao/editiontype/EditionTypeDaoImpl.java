package by.kiselevich.periodicals.dao.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class EditionTypeDaoImpl implements EditionTypeDao {

    private Session session;

    public EditionTypeDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<EditionType> getAllEditionTypes() throws DaoException {
        try {
            return session.createQuery("FROM EditionType", EditionType.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public EditionType getEditionTypeById(int id) throws DaoException {
        try {
            Query<EditionType> query = session.createQuery("FROM EditionType WHERE id = :id", EditionType.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
