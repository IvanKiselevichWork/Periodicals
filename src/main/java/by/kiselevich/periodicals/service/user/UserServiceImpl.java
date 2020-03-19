package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.UserServiceException;
import by.kiselevich.periodicals.factory.UserRepositoryFactory;
import by.kiselevich.periodicals.repository.user.UserRepository;
import by.kiselevich.periodicals.specification.user.FindUserByLogin;
import by.kiselevich.periodicals.specification.user.FindUserByLoginAndPassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    public UserServiceImpl() {
        userRepository = UserRepositoryFactory.getInstance().getUserRepository();
    }

    @Override
    public void singUp(String login, char[] password, String fullName, String email) throws UserServiceException {
        try {
            if (!userRepository.query(new FindUserByLogin(login)).isEmpty()) {
                throw new UserServiceException(ResourceBundleMessages.LOGIN_IN_USE_KEY.getKey());
            }

            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setMoney(BigDecimal.valueOf(0));
            userRepository.add(user);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new UserServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void singIn(String login, String password) throws UserServiceException {
        try {
            if (userRepository.query(new FindUserByLoginAndPassword(login, password.toCharArray())).isEmpty()) {
                throw new UserServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new UserServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
