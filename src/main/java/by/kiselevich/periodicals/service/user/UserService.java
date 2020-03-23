package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.UserServiceException;

public interface UserService {
    void signUp(User user) throws UserServiceException;

    void signIn(User user) throws UserServiceException;
}
