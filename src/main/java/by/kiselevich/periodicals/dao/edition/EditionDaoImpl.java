package by.kiselevich.periodicals.dao.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EditionDaoImpl implements EditionDao {

    private static final String EDITION_NAME_FORMAT = "%%%s%%";
    private static final String EXCLAMATION_MARK = "!";
    private static final String EXCLAMATION_MARK_REPLACEMENT = "!!";
    private static final String PERCENT_SIGN = "%";
    private static final String PERCENT_SIGN_REPLACEMENT = "!%";
    private static final String UNDERSCORE_SYMBOL = "_";
    private static final String UNDERSCORE_SYMBOL_REPLACEMENT = "!_";

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Edition edition) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(edition);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Edition edition) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(edition);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void block(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Edition edition = session.load(Edition.class, id);
            edition.setBlocked(true);
            session.update(edition);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void unblock(int id) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Edition edition = session.load(Edition.class, id);
            edition.setBlocked(false);
            session.update(edition);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Edition> getAllEditions() throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("from Edition ", Edition.class).list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Edition getEditionById(int id, boolean findNotBlockedEditionsOnly) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Edition> editionCriteriaQuery = criteriaBuilder.createQuery(Edition.class);
            Root<Edition> editionRoot = editionCriteriaQuery.from(Edition.class);
            List<Predicate> editionPredicates = new ArrayList<>();
            editionPredicates.add(criteriaBuilder.equal(editionRoot.get("id"), id));
            if (findNotBlockedEditionsOnly) {
                editionPredicates.add(criteriaBuilder.equal(editionRoot.get("blocked"), false));
            }
            editionCriteriaQuery.select(editionRoot).where(editionPredicates.toArray(new Predicate[]{}));
            Query<Edition> query = session.createQuery(editionCriteriaQuery);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Edition> getEditionsByNameAndTypeAndThemeAndBlockage(String name, Integer typeId, Integer themeId, Boolean getBlocked) throws DaoException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Edition> editionCriteriaQuery = criteriaBuilder.createQuery(Edition.class);
            Root<Edition> editionRoot = editionCriteriaQuery.from(Edition.class);
            List<Predicate> editionPredicates = new ArrayList<>();

            if (!StringUtils.isEmpty(name)) {
                name = name.replace(EXCLAMATION_MARK, EXCLAMATION_MARK_REPLACEMENT)
                        .replace(PERCENT_SIGN, PERCENT_SIGN_REPLACEMENT)
                        .replace(UNDERSCORE_SYMBOL, UNDERSCORE_SYMBOL_REPLACEMENT);
                name = String.format(EDITION_NAME_FORMAT, name);
                editionPredicates.add(criteriaBuilder.like(editionRoot.get("name"), name));
            }
            if (typeId != null) {
                editionPredicates.add(criteriaBuilder.equal(editionRoot.get("editionType"), typeId));
            }
            if (themeId != null) {
                editionPredicates.add(criteriaBuilder.equal(editionRoot.get("editionTheme"), themeId));
            }
            if (getBlocked != null && !getBlocked) {
                editionPredicates.add(criteriaBuilder.equal(editionRoot.get("blocked"), false));
            }

            editionCriteriaQuery.select(editionRoot).where(editionPredicates.toArray(new Predicate[]{}));
            Query<Edition> query = session.createQuery(editionCriteriaQuery);

            return query.list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
