package by.kiselevich.periodicals.service.subscription;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.dao.payment.PaymentDao;
import by.kiselevich.periodicals.dao.subscription.SubscriptionDao;
import by.kiselevich.periodicals.dao.user.UserDao;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.DaoFactory;
import by.kiselevich.periodicals.factory.PaymentTypeFactory;
import by.kiselevich.periodicals.util.DateUtil;
import by.kiselevich.periodicals.validator.SubscriptionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of {@link SubscriptionService}
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOG = LogManager.getLogger(SubscriptionServiceImpl.class);

    private SubscriptionDao subscriptionDao;
    private UserDao userDao;
    private PaymentDao paymentDao;
    private final SubscriptionValidator subscriptionValidator;

    public SubscriptionServiceImpl() {
        subscriptionValidator = SubscriptionValidator.getInstance();
    }

    public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    @Transactional
    public List<Subscription> getAllSubscriptions() throws ServiceException {
        try {
            return subscriptionDao.getAllSubscriptions();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    @Transactional
    public Subscription getSubscriptionById(int id) throws ServiceException {
        try {
            return subscriptionDao.getSubscriptionById(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    @Transactional
    public void addSubscription(Subscription subscription) throws ServiceException {
        Session session = DaoFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            subscriptionValidator.checkSubscription(subscription);
            BigDecimal subscriptionPrice = calculateSubscriptionPrice(subscription);
            User user = decreaseUserBalance(subscription.getUser(), subscriptionPrice);
            Payment payment = buildNewPayment(subscription, subscriptionPrice, user);
            userDao.update(user);
            subscriptionDao.add(subscription);
            paymentDao.add(payment);
            transaction.commit();
        } catch (DaoException e) {
            LOG.warn(e);
            transaction.rollback();
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void stopSubscription(Subscription subscription, User user) throws ServiceException {
        Session session = DaoFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            checkIfUserOwnSubscription(subscription, user);
            checkIfSubscriptionNotExpired(subscription);

            BigDecimal subscriptionPriceOld = calculateSubscriptionPrice(subscription);
            subscription.setSubscriptionEndDate(new Timestamp(System.currentTimeMillis()));
            BigDecimal subscriptionPriceNew = calculateSubscriptionPrice(subscription);

            BigDecimal userRefundAmount = subscriptionPriceOld.subtract(subscriptionPriceNew);
            user = increaseUserBalance(subscription.getUser(), userRefundAmount);
            Payment payment = buildNewRefund(subscription, userRefundAmount, user);

            userDao.update(user);
            subscriptionDao.update(subscription);
            paymentDao.add(payment);
            transaction.commit();
        } catch (DaoException e) {
            LOG.warn(e);
            transaction.rollback();
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private User decreaseUserBalance(User user, BigDecimal amount) throws ServiceException {
        if (user.getMoney().compareTo(amount) < 0) {
            throw new ServiceException(ResourceBundleMessages.INSUFFICIENT_FUNDS.getKey());
        }
        user.setMoney(user.getMoney().subtract(amount));
        return user;
    }

    private User increaseUserBalance(User user, BigDecimal amount) throws ServiceException {
        if (user.getMoney().compareTo(amount) < 0) {
            throw new ServiceException(ResourceBundleMessages.INSUFFICIENT_FUNDS.getKey());
        }
        user.setMoney(user.getMoney().add(amount));
        return user;
    }

    private BigDecimal calculateSubscriptionPrice(Subscription subscription) {
        int subscriptionPeriodInMonths = DateUtil.getIntegerSubscriptionPeriodInMonths(subscription);
        return subscription.getEdition().getPriceForMinimumSubscriptionPeriod()
                .multiply(new BigDecimal(subscriptionPeriodInMonths))
                .divide(new BigDecimal(subscription.getEdition().getMinimumSubscriptionPeriodInMonths()), RoundingMode.UP);
    }

    private Payment buildNewPayment(Subscription subscription, BigDecimal subscriptionPrice, User user) {
        return new Payment.PaymentBuilder()
                .user(user)
                .paymentType(PaymentTypeFactory.getPayment())
                .date(new Timestamp(System.currentTimeMillis()))
                .amount(subscriptionPrice)
                .subscription(subscription)
                .build();
    }

    private Payment buildNewRefund(Subscription subscription, BigDecimal subscriptionPrice, User user) {
        return new Payment.PaymentBuilder()
                .user(user)
                .paymentType(PaymentTypeFactory.getRefund())
                .date(new Timestamp(System.currentTimeMillis()))
                .amount(subscriptionPrice)
                .subscription(subscription)
                .build();
    }

    private void checkIfUserOwnSubscription(Subscription subscription, User user) throws ServiceException {
        if (!subscription.getUser().equals(user)) {
            LOG.warn("User trying to stop other subscription");
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private void checkIfSubscriptionNotExpired(Subscription subscription) throws ServiceException {
        if (!subscription.getSubscriptionEndDate().after(new Timestamp(System.currentTimeMillis()))) {
            LOG.warn("User trying to stop expired subscription");
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
