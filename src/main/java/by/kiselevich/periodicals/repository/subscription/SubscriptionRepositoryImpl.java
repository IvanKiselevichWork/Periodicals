package by.kiselevich.periodicals.repository.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private static final String ADD_SUBSCRIPTION = "insert into subscription(edition_id, subscription_start_date, subscription_end_date, user_id, is_paid) values (?, ?, ?, ?, ?)";

    private static final String SUBSCRIPTION_NOT_ADDED_MESSAGE = "Subscription has not been added";

    @Override
    public void add(Subscription subscription) throws RepositoryException {
        ResultSet generatedId = null;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, subscription.getEdition().getId());
            statement.setTimestamp(2, subscription.getSubscriptionStartDate());
            statement.setTimestamp(3, subscription.getSubscriptionEndDate());
            statement.setInt(4, subscription.getUser().getId());
            statement.setBoolean(5, subscription.isPaid());
            int updatedRowCount = statement.executeUpdate();
            boolean isSubscriptionAdded = false;
            if (updatedRowCount == 1) {
                generatedId = statement.getGeneratedKeys();
                if (generatedId.next()) {
                    subscription.setId(generatedId.getInt(1));
                    isSubscriptionAdded = true;
                }
            }
            if (!isSubscriptionAdded) {
                throw new RepositoryException(SUBSCRIPTION_NOT_ADDED_MESSAGE);
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
    public void update(Subscription subscription) throws RepositoryException {
        //todo
    }

    @Override
    public List<Subscription> query(Specification<Subscription> specification) throws RepositoryException {
        return specification.query();
    }
}
