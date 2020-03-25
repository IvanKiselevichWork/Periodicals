package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.util.HashUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class FindUserByLoginAndPassword implements UserSpecification {

    private static final String FIND_USERS_BY_LOGIN_AND_PASSWORD = "select id, login, password, full_name, email, money, role_id, is_available from user where login = ? and password = ?";

    private String login;
    private String password;

    public FindUserByLoginAndPassword(String login, char[] password) {
        this.login = login;
        this.password = HashUtil.getHash(password, login);
        Arrays.fill(password, '0');
    }

    @Override
    public List<User> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<User> users;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            users = getUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return users;
    }
}
