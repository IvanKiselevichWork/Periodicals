package by.kiselevich.periodicals.dao.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DaoException;

import java.util.List;

public interface UserDao {

    /**
     * Adds {@code User} to data source
     * @param user {@link User} to add
     * @throws DaoException if error occurs
     */
    void add(User user) throws DaoException;

    /**
     * Updates {@code User} in data source
     * @param user {@link User} to update
     * @throws DaoException if error occurs
     */
    void update(User user) throws DaoException;

    /**
     * Blocks {@code User}
     * @param id {@link User} {@code id}
     * @throws DaoException if error occurs
     */
    void block(int id) throws DaoException;

    /**
     * Unblocks {@code User}
     * @param id {@link User} {@code id}
     * @throws DaoException if error occurs
     */
    void unblock(int id) throws DaoException;

    /**
     * Get all {@link User} from data source
     * @return {@code List} of {@link User}
     * @throws DaoException if error occurs
     */
    List<User> getAllUsers() throws DaoException;

    /**
     * Get {@link User} by {@code id} from data source
     * @param id {@link User} {@code id}
     * @return {@link User} with given {@code id} or {@code null}
     * @throws DaoException if error occurs
     */
    User getUserById(int id) throws DaoException;

    /**
     * Get {@link User} by {@code login} from data source
     * @param login {@link User} {@code login}
     * @return {@link User} with given {@code login} or {@code null}
     * @throws DaoException if error occurs
     */
    User getUserByLogin(String login) throws DaoException;

    /**
     * Get {@link User} by {@code login} and {@code password} from data source
     * @param login {@link User} {@code login}
     * @param password {@link User} {@code password}
     * @return {@link User} with given {@code login} and {@code password} or {@code null}
     * @throws DaoException if error occurs
     */
    User getUserByLoginAndPassword(String login, String password) throws DaoException;
}
