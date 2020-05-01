package by.kiselevich.periodicals.specification.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.specification.SpecificationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link SubscriptionSpecification} for finding all {@link Subscription} by user's {@code login} from database
 */
public class FindAllSubscriptionsByUserLogin extends SpecificationUtil implements SubscriptionSpecification {

    private static final String FIND_ALL_SUBSCRIPTION_BY_USER_LOGIN = "select * from subscription inner join edition on subscription.edition_id = edition.id inner  join edition_type on edition.type_id = edition_type.id inner join edition_theme on edition.theme_id = edition_theme.id inner join user on subscription.user_id = user.id inner join user_role on user.role_id = user_role.id where user.login = ?";

    private final String userLogin;

    public FindAllSubscriptionsByUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public List<Subscription> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<Subscription> subscriptions = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_SUBSCRIPTION_BY_USER_LOGIN);
            statement.setString(1, userLogin);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subscriptions.add(getSubscriptionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return subscriptions;
    }
}
