package by.kiselevich.periodicals.repository.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.repository.Repository;
import by.kiselevich.periodicals.specification.Specification;
import by.kiselevich.periodicals.util.HashUtil;
import by.kiselevich.periodicals.util.RepositoryUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static final String USER_ROLE_ID = "2";
    private static final String USER_IS_AVAILABLE = "1";

    private static final String ADD_USER = "insert into user (" +
            "login, password, full_name, email, money, role_id, is_available) VALUES (" +
            "?, ?, ?, ?, ?, " + USER_ROLE_ID + ", " + USER_IS_AVAILABLE + ")";

    private static final String USER_DIDNT_ADD_MESSAGE = "User didnt add";

    @Override
    public void add(User user) throws RepositoryException {
        ResultSet generatedId = null;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, HashUtil.getHash(user.getPassword(), user.getLogin()));
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getEmail());
            statement.setBigDecimal(5, user.getMoney());
            int updatedRowCount = statement.executeUpdate();
            boolean isUserAdded = false;
            if (updatedRowCount == 1) {
                generatedId = statement.getGeneratedKeys();
                if (generatedId.next()) {
                    user.setId(generatedId.getInt(1));
                    isUserAdded = true;
                }
            }
            if (!isUserAdded) {
                throw new RepositoryException(USER_DIDNT_ADD_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            RepositoryUtil.closeResultSet(generatedId);
        }
    }

    @Override
    public void remove(int id) {
        //todo
    }

    @Override
    public void update(User user) {
        //todo
    }

    @Override
    public List<User> query(Specification<User> specification) throws RepositoryException {
        return specification.query();
    }
}
