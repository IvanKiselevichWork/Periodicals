package by.kiselevich.periodicals.service.user;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.command.UserType;
import by.kiselevich.periodicals.dao.payment.PaymentDao;
import by.kiselevich.periodicals.dao.user.UserDao;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.DaoFactory;
import by.kiselevich.periodicals.factory.PaymentTypeFactory;
import by.kiselevich.periodicals.util.HashUtil;
import by.kiselevich.periodicals.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of {@link UserService}
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final PaymentDao paymentDao;
    private final UserValidator userValidator;

    public UserServiceImpl() {
        userDao = DaoFactory.getInstance().getUserDao();
        paymentDao = DaoFactory.getInstance().getPaymentDao();
        userValidator = UserValidator.getInstance();
    }

    @Override
    public User signUp(User user) throws ServiceException {
        try {
            userValidator.checkUserCredentialsOnSignUp(user);
            String hash = HashUtil.getHash(user.getPassword().toCharArray(), user.getLogin());
            user.setPassword(hash);
            userDao.add(user);
            return user;
        } catch (DaoException e) {
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
            String hash = HashUtil.getHash(user.getPassword().toCharArray(), user.getLogin());
            user.setPassword(hash);
            User user1 = userDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
            if (user1 == null) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }

            if (!user1.isAvailable()) {
                throw new ServiceException(ResourceBundleMessages.USER_BLOCKED.getKey());
            }

            return user1;
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        try {
            return userDao.getUserByLogin(login);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDao.getAllUsers();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void blockUser(int id) throws ServiceException {
        try {
            User user = userDao.getUserById(id);
            if (user == null) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }
            if (UserType.getUserTypeByUser(user) == UserType.ADMIN) {
                throw new ServiceException(ResourceBundleMessages.CANT_BLOCK_ADMIN.getKey());
            }
            userDao.block(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void unblockUser(int id) throws ServiceException {
        try {
            User user = userDao.getUserById(id);
            if (user == null) {
                throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
            }
            if (UserType.getUserTypeByUser(user) == UserType.ADMIN) {
                throw new ServiceException(ResourceBundleMessages.CANT_BLOCK_ADMIN.getKey());
            }
            userDao.unblock(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void refillBalance(String login, BigDecimal amount) throws ServiceException {
        Session session = DaoFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ServiceException(ResourceBundleMessages.INVALID_REFILL_AMOUNT.getKey());
            }
            User user = refillUserBalance(login, amount);
            Payment payment = buildNewPayment(amount, user);
            userDao.update(user);
            paymentDao.add(payment);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private User refillUserBalance(String login, BigDecimal amount) throws DaoException, ServiceException {
        User user = userDao.getUserByLogin(login);
        if (user == null) {
            throw new ServiceException(ResourceBundleMessages.USER_NOT_FOUND_KEY.getKey());
        }
        user.setMoney(user.getMoney().add(amount));
        return user;
    }

    private Payment buildNewPayment(BigDecimal amount, User user) {
        return new Payment.PaymentBuilder()
                .user(user)
                .paymentType(PaymentTypeFactory.getRefill())
                .date(new Timestamp(System.currentTimeMillis()))
                .amount(amount)
                .build();
    }
}
