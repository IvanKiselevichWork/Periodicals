package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.UserServiceException;

import java.util.List;

public interface UserService {
    void signUp(User user) throws UserServiceException;

    void signIn(User user) throws UserServiceException;

    List<User> getAllUsers() throws UserServiceException;

    void blockUser(int id) throws UserServiceException;

    void unblockUser(int id) throws UserServiceException;
}
