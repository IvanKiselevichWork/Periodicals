package by.kiselevich.periodicals.repository.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface UserRepository extends Repository<User> {

    void block(int id) throws RepositoryException;

    void unblock(int id) throws RepositoryException;
}
