package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
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
 * Implementation of {@link UserSpecification} for finding all {@link User} by {@code login} from database
 */
public class FindUserByLogin extends SpecificationUtil implements UserSpecification {

    private static final String FIND_USERS_BY_LOGIN = "select * from user inner join user_role on user.role_id = user_role.id where login = ?";

    private final String login;

    public FindUserByLogin(String login) {
        this.login = login;
    }

    @Override
    public List<User> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return users;
    }
}
