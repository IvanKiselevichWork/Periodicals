package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUserSpecification implements UserSpecification {

    private static final Logger LOG = LogManager.getLogger(AbstractUserSpecification.class);

    private static final String ID = "id";
    private static final String ROLE_ID = "role_id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FULL_NAME = "full_name";
    private static final String EMAIL = "email";
    private static final String MONEY = "money";
    private static final String IS_AVAILABLE = "is_available";

    protected void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                LOG.warn(e);
            }
        }
    }

    protected List<User> getUsersFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getInt(ID),
                    resultSet.getInt(ROLE_ID),
                    resultSet.getString(LOGIN),
                    resultSet.getString(PASSWORD).toCharArray(),
                    resultSet.getString(FULL_NAME),
                    resultSet.getString(EMAIL),
                    resultSet.getBigDecimal(MONEY),
                    resultSet.getBoolean(IS_AVAILABLE)
            ));
        }
        return users;
    }
}
