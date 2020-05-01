package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.command.UserType;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.PaymentTypeFactory;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.user.UserRepository;
import by.kiselevich.periodicals.specification.user.FindAllUsers;
import by.kiselevich.periodicals.specification.user.FindUserById;
import by.kiselevich.periodicals.specification.user.FindUserByLogin;
import by.kiselevich.periodicals.specification.user.FindUserByLoginAndPassword;
import by.kiselevich.periodicals.unitofwork.TransactionUnitOfWork;
import by.kiselevich.periodicals.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public UserServiceImpl() {
        userRepository = RepositoryFactory.getInstance().getUserRepository();
        userValidator = UserValidator.getInstance();
    }

    @Override
    public User signUp(User user) throws ServiceException {
        try {
            userValidator.checkUserCredentialsOnSignUp(user);
            user = userRepository.add(user);
            return user;
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User signIn(User user) throws ServiceException {
        try {
            userValidator.checkUserCredentialsOnSignIn(user);

            List<User> userList = userRepository.query(new FindUserByLoginAndPassword(user.getLogin(), user.getPassword()));
            if (userList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }

            if (!userList.get(0).isAvailable()) {
                throw new ServiceException(ResourceBundleMessages.USER_BLOCKED.getKey());
            }

            return userList.get(0);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }  catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws ServiceException {
        try {
            List<User> users = userRepository.query(new FindUserByLogin(login));
            if (!users.isEmpty()) {
                return Optional.of(users.get(0));
            }
            return Optional.empty();
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
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
            List<User> userList = userRepository.query(new FindUserById(id));
            if (userList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }
            if (UserType.getUserTypeByUser(userList.get(0)) == UserType.ADMIN) {
                throw new ServiceException(ResourceBundleMessages.CANT_BLOCK_ADMIN.getKey());
            }
            userRepository.block(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void unblockUser(int id) throws ServiceException {
        try {
            List<User> userList = userRepository.query(new FindUserById(id));
            if (userList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }
            if (UserType.getUserTypeByUser(userList.get(0)) == UserType.ADMIN) {
                throw new ServiceException(ResourceBundleMessages.CANT_UNBLOCK_ADMIN.getKey());
            }
            userRepository.unblock(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void refillBalance(String login, BigDecimal amount) throws ServiceException {
        TransactionUnitOfWork transactionUnitOfWork = new TransactionUnitOfWork();
        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ServiceException(ResourceBundleMessages.INVALID_REFILL_AMOUNT.getKey());
            }
            List<User> userList = userRepository.query(new FindUserByLogin(login));
            if (userList.isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }
            User user = userList.get(0);
            user.setMoney(user.getMoney().add(amount));
            Payment payment = new Payment.PaymentBuilder()
                    .user(user)
                    .paymentType(PaymentTypeFactory.getRefill())
                    .date(new Timestamp(System.currentTimeMillis()))
                    .amount(amount)
                    .build();
            transactionUnitOfWork.getUserRepository().update(user);
            transactionUnitOfWork.getPaymentRepository().add(payment);
            transactionUnitOfWork.commit();
        } catch (RepositoryException e) {
            transactionUnitOfWork.rollback();
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } finally {
            transactionUnitOfWork.close();
        }
    }
}
