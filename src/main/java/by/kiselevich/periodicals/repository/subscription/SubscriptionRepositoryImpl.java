package by.kiselevich.periodicals.repository.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.repository.RepositoryUtil;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.*;
import java.util.List;

public class SubscriptionRepositoryImpl extends RepositoryUtil implements SubscriptionRepository {

    private static final String ADD_SUBSCRIPTION = "insert into subscription(edition_id, subscription_start_date, subscription_end_date, user_id) values (?, ?, ?, ?)";

    private static final String SUBSCRIPTION_NOT_ADDED_MESSAGE = "Subscription has not been added";

    private ConnectionPool connectionPool;

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
}
