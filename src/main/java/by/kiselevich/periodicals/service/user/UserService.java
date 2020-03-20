package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.UserServiceException;

public interface UserService {
    void singUp(User user) throws UserServiceException;

    void singIn(User user) throws UserServiceException;
}
