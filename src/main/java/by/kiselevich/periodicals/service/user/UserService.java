package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void signUp(User user) throws ServiceException;

    void signIn(User user) throws ServiceException;

    Optional<User> getUserByLogin(String login) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void blockUser(int id) throws ServiceException;

    void unblockUser(int id) throws ServiceException;

    void refillBalance(String login, BigDecimal amount) throws ServiceException;
}
