package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.repository.Repository;
import by.kiselevich.periodicals.util.RepositoryUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FindUserByLogin implements UserSpecification {

    private static final String FIND_USERS_BY_LOGIN = "select id, login, password, full_name, email, money, role_id, is_available from user where login = ?";

    private String login;

    public FindUserByLogin(String login) {
        this.login = login;
    }

    @Override
    public List<User> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<User> users;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            users = RepositoryUtil.getUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            RepositoryUtil.closeResultSet(resultSet);
        }
        return users;
    }
}
