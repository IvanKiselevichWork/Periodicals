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
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static final String ADD_USER = "insert into user (" +
            "login, password, full_name, email, money, role_id, is_available) VALUES (" +
            "?, ?, ?, ?, ?, 2, 1";

    private static final String USER_DIDNT_ADD_MESSAGE = "User didnt add";

    @Override
    public void add(User user) throws RepositoryException {
        ResultSet generatedId = null;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_USER);
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
    public List<User> query(Specification<User, Repository<User>> specification) {
        return new ArrayList<>(); //todo
    }
}
