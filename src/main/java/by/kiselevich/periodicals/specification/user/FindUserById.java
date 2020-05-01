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
 * Implementation of {@link UserSpecification} for finding all {@link User} by {@code id} from database
 */
public class FindUserById extends SpecificationUtil implements UserSpecification {

    private static final String FIND_USER_BY_ID = "select * from user inner join user_role on user.role_id = user_role.id where user.id = ?";

    private final int id;

    public FindUserById(int id) {
        this.id = id;
    }

    @Override
    public List<User> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setInt(1, id);
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
