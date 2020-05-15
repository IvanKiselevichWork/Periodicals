package by.kiselevich.periodicals.dao.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.DaoException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EditionDaoImpl implements EditionDao {

    private static final String EDITION_NAME_FORMAT = "%%%s%%";
    private static final String EXCLAMATION_MARK = "!";
    private static final String EXCLAMATION_MARK_REPLACEMENT = "!!";
    private static final String PERCENT_SIGN = "%";
    private static final String PERCENT_SIGN_REPLACEMENT = "!%";
    private static final String UNDERSCORE_SYMBOL = "_";
    private static final String UNDERSCORE_SYMBOL_REPLACEMENT = "!_";

    private Session session;

    public EditionDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void add(Edition edition) throws DaoException {
        try {
            session.save(edition);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Edition edition) throws DaoException {
        try {
            session.update(edition);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void block(int id) throws DaoException {
        try {
            Query<Edition> query = session.createQuery("update Edition set blocked = 1 where id = :id", Edition.class);
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void unblock(int id) throws DaoException {
        try {
            Query<Edition> query = session.createQuery("update Edition set blocked = 0 where id = :id", Edition.class);
            query.setParameter("id", id);
            query.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Edition> getAllEditions() throws DaoException {
        try {
            Query<Edition> query = session.createQuery("select e from Edition e", Edition.class);
            return query.list();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Edition getEditionById(int id, boolean findNotBlockedEditionsOnly) throws DaoException {
        try {
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
