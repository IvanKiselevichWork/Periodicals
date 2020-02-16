package by.kiselevich.periodicals.dao;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DAOException;
import by.kiselevich.periodicals.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "select id, login, username, role_id, password, money, is_available from user where login = ? and password = ?";
    private static final String USER_ID_COLUMN = "id";
    private static final String USER_LOGIN_COLUMN = "login";
    private static final String USER_NAME_COLUMN = "username";
    private static final String USER_ROLE_ID_COLUMN = "role_id";
    private static final String USER_PASSWORD_COLUMN = "password";
    private static final String USER_MONEY_COLUMN = "money";
    private static final String USER_IS_AVAILABLE_COLUMN = "is_available";

    private static final String CREATE_USER = "INSERT INTO user(login, username, role_id, password, money, is_available) \n" +
            "VALUES (?, ?, 2, ?, 0, true)";

    /**
     *
     * @param login user login
     * @param password user password
     * @return User, if exist, null - if not
     * @throws DAOException on close connection catch SQLException
     */
    @Override
    public User signIn(String login, char[] password) throws DAOException {
        User user = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, new String(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt(USER_ID_COLUMN),
                        resultSet.getString(USER_LOGIN_COLUMN),
                        resultSet.getString(USER_NAME_COLUMN),
                        resultSet.getInt(USER_ROLE_ID_COLUMN),
                        resultSet.getString(USER_PASSWORD_COLUMN).toCharArray(),
                        resultSet.getBigDecimal(USER_MONEY_COLUMN),
                        resultSet.getBoolean(USER_IS_AVAILABLE_COLUMN)
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    /**
     *
     * @param username user name
     * @param login user login
     * @param password user password
     * @return User, if created, null - if not
     * @throws DAOException on close connection catch SQLException
     */
    @Override
    public User signUp(String username, String login, char[] password) throws DAOException {
        User user = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, new String(password));
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                user = signIn(login, password);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }
}
