package by.kiselevich.periodicals.repository.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.repository.RepositoryUtil;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.*;
import java.util.List;

/**
 * Implementation of {@link SubscriptionRepository}
 */
public class SubscriptionRepositoryImpl extends RepositoryUtil implements SubscriptionRepository {

    private static final String ADD_SUBSCRIPTION = "insert into subscription(edition_id, subscription_start_date, subscription_end_date, user_id) values (?, ?, ?, ?)";
    private static final String ROW_COUNT = "rowcount";
    private static final String COUNT_SUBSCRIPTION = "select count(*) as " + ROW_COUNT + " from subscription";


    private static final String SUBSCRIPTION_NOT_ADDED_MESSAGE = "Subscription has not been added";

    private final ConnectionPool connectionPool;

    public SubscriptionRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Subscription subscription) throws RepositoryException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, subscription.getEdition().getId());
            statement.setTimestamp(2, subscription.getSubscriptionStartDate());
            statement.setTimestamp(3, subscription.getSubscriptionEndDate());
            statement.setInt(4, subscription.getUser().getId());
            int generatedId = executeUpdateOneRowAndGetGeneratedId(statement, SUBSCRIPTION_NOT_ADDED_MESSAGE);
            subscription.setId(generatedId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Subscription> query(Specification<Subscription> specification) throws RepositoryException {
        return specification.query();
    }

    @Override
    public int size() throws RepositoryException {
        ResultSet resultSet = null;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_SUBSCRIPTION)) {
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(ROW_COUNT);
            }
            return 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
    }
}
