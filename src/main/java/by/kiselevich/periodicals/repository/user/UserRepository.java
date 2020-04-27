package by.kiselevich.periodicals.repository.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface UserRepository extends Repository<User> {

    /**
     * Adds {@code User} to data source
     * @param user {@link User} to add
     * @return {@link User} if added
     * @throws RepositoryException if error occurs
     */
    User add(User user) throws RepositoryException;

    /**
     * Updates {@code User} in data source
     * @param user {@link User} to update
     * @throws RepositoryException if error occurs
     */
    void update(User user) throws RepositoryException;

    /**
     * Blocks {@code User}
     * @param id {@link User} {@code id}
     * @throws RepositoryException if error occurs
     */
    void block(int id) throws RepositoryException;

    /**
     * Unblocks {@code User}
     * @param id {@link User} {@code id}
     * @throws RepositoryException if error occurs
     */
    void unblock(int id) throws RepositoryException;
}
