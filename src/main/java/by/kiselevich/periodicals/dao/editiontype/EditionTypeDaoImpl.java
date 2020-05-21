package by.kiselevich.periodicals.dao.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EditionTypeDaoImpl implements EditionTypeDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<EditionType> getAllEditionTypes() throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM EditionType", EditionType.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public EditionType getEditionTypeById(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.load(EditionType.class, id);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
