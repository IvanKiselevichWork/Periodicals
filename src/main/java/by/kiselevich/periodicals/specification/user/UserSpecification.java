package by.kiselevich.periodicals.specification.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.specification.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserSpecification extends Specification<User> {

    String ID = "id";
    String ROLE_ID = "role_id";
    String LOGIN = "login";
    String PASSWORD = "password";
    String FULL_NAME = "full_name";
    String EMAIL = "email";
    String MONEY = "money";
    String IS_AVAILABLE = "is_available";
    
    default List<User> getUsersFromResultSet(ResultSet resultSet) throws SQLException {
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
