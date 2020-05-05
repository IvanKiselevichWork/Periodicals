package by.kiselevich.periodicals.service.subscription;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.PaymentTypeFactory;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.Repository;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepository;
import by.kiselevich.periodicals.specification.subscription.FindAllSubscriptions;
import by.kiselevich.periodicals.specification.subscription.FindAllSubscriptionsByUserLogin;
import by.kiselevich.periodicals.unitofwork.TransactionUnitOfWork;
import by.kiselevich.periodicals.util.DateUtil;
import by.kiselevich.periodicals.validator.SubscriptionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of {@link SubscriptionService}
 */
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOG = LogManager.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionValidator subscriptionValidator;

    public SubscriptionServiceImpl() {
        subscriptionRepository = RepositoryFactory.getInstance().getSubscriptionRepository();
        subscriptionValidator = SubscriptionValidator.getInstance();
    }

    @Override
    public List<Subscription> getAllSubscriptions() throws ServiceException {
        try {
            return subscriptionRepository.query(new FindAllSubscriptions());
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Subscription> getAllSubscriptionsByUserLogin(String userLogin) throws ServiceException {
        try {
            return subscriptionRepository.query(new FindAllSubscriptionsByUserLogin(userLogin));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void addSubscription(Subscription subscription) throws ServiceException {
        TransactionUnitOfWork transactionUnitOfWork = new TransactionUnitOfWork();
        try {
            subscriptionValidator.checkSubscription(subscription);

            BigDecimal subscriptionPrice = calculateSubscriptionPrice(subscription);
            User user = updateUserBalance(subscription.getUser(), subscriptionPrice);
            Payment payment = buildNewPayment(subscription, subscriptionPrice, user);

            transactionUnitOfWork.getUserRepository().update(user);
            transactionUnitOfWork.getSubscriptionRepository().add(subscription);
            transactionUnitOfWork.getPaymentRepository().add(payment);
            transactionUnitOfWork.commit();
        } catch (RepositoryException e) {
            LOG.warn(e);
            transactionUnitOfWork.rollback();
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        } finally {
            transactionUnitOfWork.close();
        }
    }

    @Override
    public int getAllSubscriptionCount() throws ServiceException {
        try {
            return subscriptionRepository.size();
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private User updateUserBalance(User user, BigDecimal subscriptionPrice) throws ServiceException {
        if (user.getMoney().compareTo(subscriptionPrice) < 0) {
            throw new ServiceException(ResourceBundleMessages.INSUFFICIENT_FUNDS.getKey());
        }

        user.setMoney(user.getMoney().subtract(subscriptionPrice));
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
}
