package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service to work with {@link User}
 */
public interface UserService {
    /**
     * Sign up user by validating and adding it to data source
     * @param user {@code User} for signing up
     * @return {@link User} if valid and added
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    User signUp(User user) throws ServiceException;

    /**
     * Sign in user by validating and setting role from data source
     * @param user {@code User} for signing in
     * @return {@link User} if valid and exist
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    User signIn(User user) throws ServiceException;

    /**
     * Returns {@code Optional<User>} by login from data source
     * @param login user login
     * @return {@code Optional<User>} by login
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    Optional<User> getUserByLogin(String login) throws ServiceException;

    /**
     * Returns all users as {@code List} from data source
     * @return {@code List} with all users
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Block user
     * @param id user's id
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void blockUser(int id) throws ServiceException;

    /**
     * Unblock user
     * @param id user's id
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void unblockUser(int id) throws ServiceException;

    /**
     * Refill user's balance by {@code amount}
     * @param login user's login
     * @param amount balance refills by
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void refillBalance(String login, BigDecimal amount) throws ServiceException;
}
