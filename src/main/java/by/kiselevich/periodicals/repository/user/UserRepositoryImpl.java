package by.kiselevich.periodicals.repository.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.repository.RepositoryUtil;
import by.kiselevich.periodicals.specification.Specification;
import by.kiselevich.periodicals.util.HashUtil;

import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends RepositoryUtil implements UserRepository {

    private static final String USER_ROLE_ID = "2";
    private static final String USER_IS_AVAILABLE = "1";

    private static final String ADD_USER = "insert into user (" +
            "login, password, full_name, email, money, role_id, is_available) VALUES (" +
            "?, ?, ?, ?, ?, " + USER_ROLE_ID + ", " + USER_IS_AVAILABLE + ")";

    private static final String UPDATE_USER = "UPDATE user SET role_id = ?, login = ?, password = ?, full_name = ?, email = ?, money = ?, is_available = ? WHERE id = ?;";

    private static final String BLOCK_USER = "update user set is_available = 0 where id = ?";
    private static final String UNBLOCK_USER = "update user set is_available = 1 where id = ?";

    private static final String USER_NOT_ADDED_MESSAGE = "User has not been added";
    private static final String USER_NOT_UPDATED_MESSAGE = "User has not been updated";
    private static final String USER_NOT_BLOCKED_MESSAGE = "User was not blocked";
    private static final String USER_NOT_UNBLOCKED_MESSAGE = "User was not unblocked";

    private final ConnectionPool connectionPool;

    public UserRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public User add(User user) throws RepositoryException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            String hash = HashUtil.getHash(user.getPassword().toCharArray(), user.getLogin());
            statement.setString(2, hash);
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getEmail());
            statement.setBigDecimal(5, user.getMoney());
            int generatedId = executeUpdateOneRowAndGetGeneratedId(statement, USER_NOT_ADDED_MESSAGE);
            user.setId(generatedId);
            user.setPassword(hash);
            return user;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void block(int id) throws RepositoryException {
        updateEntityById(id, BLOCK_USER, USER_NOT_BLOCKED_MESSAGE, connectionPool);
    }

    @Override
    public void unblock(int id) throws RepositoryException {
        updateEntityById(id, UNBLOCK_USER, USER_NOT_UNBLOCKED_MESSAGE, connectionPool);
    }

    @Override
    public void update(User user) throws RepositoryException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setInt(1, user.getRole().getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFullName());
            statement.setString(5, user.getEmail());
            statement.setBigDecimal(6, user.getMoney());
            statement.setBoolean(7, user.isAvailable());
            statement.setInt(8, user.getId());
            int updatedRowCount = statement.executeUpdate();
            boolean isUserUpdated = false;
            if (updatedRowCount == 1) {
                isUserUpdated = true;
            }
            if (!isUserUpdated) {
                throw new RepositoryException(USER_NOT_UPDATED_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<User> query(Specification<User> specification) throws RepositoryException {
        return specification.query();
    }
}
