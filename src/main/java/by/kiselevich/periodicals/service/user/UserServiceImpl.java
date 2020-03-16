package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.UserServiceException;
import by.kiselevich.periodicals.factory.UserRepositoryFactory;
import by.kiselevich.periodicals.repository.user.UserRepository;
import by.kiselevich.periodicals.specification.user.FindUserByLogin;
import by.kiselevich.periodicals.specification.user.FindUserByLoginAndPassword;
import by.kiselevich.periodicals.validator.UserValidator;

public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_KEY = "user_not_found";
    private static final String LOGIN_IN_USE_KEY = "login_in_use";
    private static final String INVALID_LOGIN_KEY = "invalid_login";
    private static final String INVALID_PASSWORD_KEY = "invalid_password";

    private UserRepository userRepository;
    private UserValidator userValidator;

    public UserServiceImpl() {
        userRepository = UserRepositoryFactory.getInstance().getUserRepository();
        userValidator = new UserValidator();
    }

    @Override
    public void singUp(String login, String password) throws UserServiceException {
        checkUserCredentials(login, password);

        if (!userRepository.query(new FindUserByLogin(login)).isEmpty()) {
            throw new UserServiceException(LOGIN_IN_USE_KEY);
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password.toCharArray());
        userRepository.add(user);
    }

    @Override
    public void singIn(String login, String password) throws UserServiceException {
        checkUserCredentials(login, password);

        if (userRepository.query(new FindUserByLoginAndPassword(login, password.toCharArray())).isEmpty()) {
            throw new UserServiceException(USER_NOT_FOUND_KEY);
        }
    }

    private void checkUserCredentials(String login, String password) throws UserServiceException {
        if (!userValidator.isStringValid(login)) {
            throw new UserServiceException(INVALID_LOGIN_KEY);
        }
        if (!userValidator.isStringValid(password)) {
            throw new UserServiceException(INVALID_PASSWORD_KEY);
        }
    }
}
