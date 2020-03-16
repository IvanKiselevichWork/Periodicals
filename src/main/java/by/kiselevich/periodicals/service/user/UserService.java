package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.exception.UserServiceException;

public interface UserService {
    void singUp(String login, String password) throws UserServiceException;

    void singIn(String login, String password) throws UserServiceException;
}
