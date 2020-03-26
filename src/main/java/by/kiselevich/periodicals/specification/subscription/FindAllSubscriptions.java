package by.kiselevich.periodicals.specification.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FindAllSubscriptions implements SubscriptionSpecification {

    private static final String FIND_ALL_SUBSCRIPTION = "select id, edition_id, subscription_start_date, subscription_end_date, user_id, is_paid from subscription";

    @Override
    public List<Subscription> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<Subscription> subscriptions;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_SUBSCRIPTION);
            resultSet = statement.executeQuery();
            subscriptions = getSubscriptionFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return subscriptions;
    }
}
