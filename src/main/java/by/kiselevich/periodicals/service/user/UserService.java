package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.exception.UserServiceException;

public interface UserService {
    void singUp(String login, char[] password, String fullName, String email) throws UserServiceException;

    void singIn(String login, String password) throws UserServiceException;
}
