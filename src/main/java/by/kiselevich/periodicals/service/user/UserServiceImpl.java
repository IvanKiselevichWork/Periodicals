package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.UserValidatorException;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.user.UserRepository;
import by.kiselevich.periodicals.specification.user.FindAllUsers;
import by.kiselevich.periodicals.specification.user.FindUserByLogin;
import by.kiselevich.periodicals.specification.user.FindUserByLoginAndPassword;
import by.kiselevich.periodicals.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private UserValidator userValidator;

    public UserServiceImpl() {
        userRepository = RepositoryFactory.getInstance().getUserRepository();
        userValidator = new UserValidator();
    }

    @Override
    public void signUp(User user) throws ServiceException {
        try {
            userValidator.checkUserCredentialsOnSignUp(user);

            if (!userRepository.query(new FindUserByLogin(user.getLogin())).isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.LOGIN_IN_USE_KEY.getKey());
            }

            userRepository.add(user);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (UserValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void signIn(User user) throws ServiceException {
        try {
            userValidator.checkUserCredentialsOnSignIn(user);

            List<User> userList = userRepository.query(new FindUserByLoginAndPassword(user.getLogin(), user.getPassword()));
            if (userList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }
            //todo builder
            user.setRoleId(userList.get(0).getRoleId());
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }  catch (UserValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userRepository.query(new FindAllUsers());
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void blockUser(int id) throws ServiceException {
        try {
            userRepository.block(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void unblockUser(int id) throws ServiceException {
        try {
            userRepository.unblock(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
