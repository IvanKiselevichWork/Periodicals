package by.kiselevich.periodicals.repository.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.repository.RepositoryUtil;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.*;
import java.util.List;

public class EditionRepositoryImpl extends RepositoryUtil implements EditionRepository {

    private static final String ADD_EDITION = "insert into edition (name, type_id, theme_id, periodicity_per_year, minimum_subscription_period_in_months, price_for_minimum_subscription_period, is_blocked) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EDITION = "update edition set name = ?, type_id = ?, theme_id = ?, periodicity_per_year = ?, minimum_subscription_period_in_months = ?, price_for_minimum_subscription_period = ? where id = ?";

    private static final String BLOCK_EDITION = "update edition set is_blocked = 1 where id = ?";
    private static final String UNBLOCK_EDITION = "update edition set is_blocked = 0 where id = ?";

    private static final String EDITION_NOT_ADDED_MESSAGE = "Edition has not been added";
    private static final String EDITION_NOT_UPDATED_MESSAGE = "Edition has not been updated";
    private static final String EDITION_NOT_BLOCKED_MESSAGE = "Edition was not blocked";
    private static final String EDITION_NOT_UNBLOCKED_MESSAGE = "Edition was not unblocked";

    private ConnectionPool connectionPool;

    public EditionRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Edition edition) throws RepositoryException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_EDITION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, edition.getName());
            statement.setInt(2, edition.getEditionType().getId());
            statement.setInt(3, edition.getEditionTheme().getId());
            statement.setInt(4, edition.getPeriodicityPerYear());
            statement.setInt(5, edition.getMinimumSubscriptionPeriodInMonths());
            statement.setBigDecimal(6, edition.getPriceForMinimumSubscriptionPeriod());
            statement.setBoolean(7, edition.isBlocked());
            int generatedId = executeUpdateOneRowAndGetGeneratedId(statement, EDITION_NOT_ADDED_MESSAGE);
            edition.setId(generatedId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void block(int id) throws RepositoryException {
        updateEditionById(id, BLOCK_EDITION, EDITION_NOT_BLOCKED_MESSAGE);
    }

    @Override
    public void unblock(int id) throws RepositoryException {
        updateEditionById(id, UNBLOCK_EDITION, EDITION_NOT_UNBLOCKED_MESSAGE);
    }

    @Override
    public void update(Edition edition) throws RepositoryException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_EDITION)) {
            statement.setString(1, edition.getName());
            statement.setInt(2, edition.getEditionType().getId());
            statement.setInt(3, edition.getEditionTheme().getId());
            statement.setInt(4, edition.getPeriodicityPerYear());
            statement.setInt(5, edition.getMinimumSubscriptionPeriodInMonths());
            statement.setBigDecimal(6, edition.getPriceForMinimumSubscriptionPeriod());
            statement.setInt(7, edition.getId());
            int updatedRowCount = statement.executeUpdate();
            if (updatedRowCount != 1) {
                throw new RepositoryException(EDITION_NOT_UPDATED_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Edition> query(Specification<Edition> specification) throws RepositoryException {
        return specification.query();
    }

    private void updateEditionById(int id, String updateSqlQuery, String exceptionMessage) throws RepositoryException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSqlQuery)) {
            statement.setInt(1, id);
            int updatedRowCount = statement.executeUpdate();
            if (updatedRowCount != 1) {
                throw new RepositoryException(exceptionMessage);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
