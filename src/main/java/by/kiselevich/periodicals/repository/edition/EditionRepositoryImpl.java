package by.kiselevich.periodicals.repository.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EditionRepositoryImpl implements EditionRepository {

    private static final Logger LOG = LogManager.getLogger(EditionRepositoryImpl.class);

    private static final String ADD_EDITION = "insert into edition (name, type_id, theme_id, periodicity_per_year, minimum_subscription_period_in_months, price_for_minimum_subscription_period) values (?, ?, ?, ?, ?, ?)";

    private static final String EDITION_NOT_ADDED_MESSAGE = "Edition has not been added";

    @Override
    public void add(Edition edition) throws RepositoryException {
        ResultSet generatedId = null;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_EDITION, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, edition.getName());
            statement.setInt(2, edition.getTypeId());
            statement.setInt(3, edition.getThemeId());
            statement.setInt(4, edition.getPeriodicityPerYear());
            statement.setInt(5, edition.getMinimumSubscriptionPeriodInMonths());
            statement.setBigDecimal(6, edition.getPriceForMinimumSubscriptionPeriod());
            int updatedRowCount = statement.executeUpdate();
            boolean isEditionAdded = false;
            if (updatedRowCount == 1) {
                generatedId = statement.getGeneratedKeys();
                if (generatedId.next()) {
                    edition.setId(generatedId.getInt(1));
                    isEditionAdded = true;
                }
            }
            if (!isEditionAdded) {
                throw new RepositoryException(EDITION_NOT_ADDED_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(generatedId);
        }
    }

    @Override
    public void remove(int id) throws RepositoryException {
        //todo
    }

    @Override
    public void update(Edition edition) throws RepositoryException {
        //todo
    }

    @Override
    public List<Edition> query(Specification<Edition> specification) throws RepositoryException {
        return specification.query();
    }
}
