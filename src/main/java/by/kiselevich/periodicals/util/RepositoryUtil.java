package by.kiselevich.periodicals.util;

import by.kiselevich.periodicals.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryUtil {

    private static final Logger LOG = LogManager.getLogger(RepositoryUtil.class);

    public static void closeResultSet(ResultSet resultSet) {
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

    public static List<User> getUsersFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new User(
                    resultSet.getInt("id"),
                    resultSet.getInt("role_id"),
                    resultSet.getString("login"),
                    resultSet.getString("password").toCharArray(),
                    resultSet.getString("full_name"),
                    resultSet.getString("email"),
                    resultSet.getBigDecimal("money"),
                    resultSet.getBoolean("is_available")
            ));
        }
        return users;
    }
}
