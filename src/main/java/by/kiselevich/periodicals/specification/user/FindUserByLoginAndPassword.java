package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.specification.SpecificationUtil;
import by.kiselevich.periodicals.util.HashUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserByLoginAndPassword extends SpecificationUtil implements UserSpecification {

    private static final String FIND_USERS_BY_LOGIN_AND_PASSWORD = "select * from user inner join user_role on user.role_id = user_role.id where login = ? and password = ?";

    private String login;
    private String password;

    public FindUserByLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = HashUtil.getHash(password.toCharArray(), login);
    }

    @Override
    public List<User> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_LOGIN_AND_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
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
