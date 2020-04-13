package by.kiselevich.periodicals.service.subscription;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Payment;
import by.kiselevich.periodicals.entity.PaymentType;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.RepositoryFactory;
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

public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOG = LogManager.getLogger(SubscriptionServiceImpl.class);

    private SubscriptionRepository subscriptionRepository;
    private SubscriptionValidator subscriptionValidator;

    public SubscriptionServiceImpl() {
        subscriptionRepository = RepositoryFactory.getInstance().getSubscriptionRepository();
        subscriptionValidator = new SubscriptionValidator();
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
    public Subscription addSubscription(Subscription subscription) throws ServiceException {
        TransactionUnitOfWork transactionUnitOfWork = new TransactionUnitOfWork();
        try {
            subscriptionValidator.checkSubscription(subscription);

            int subscriptionPeriodInMonths = DateUtil.getIntegerSubscriptionPeriodInMonths(subscription);
            BigDecimal subscriptionPrice =
                subscription.getEdition().getPriceForMinimumSubscriptionPeriod()
                .multiply(new BigDecimal(subscriptionPeriodInMonths))
                .divide(new BigDecimal(subscription.getEdition().getMinimumSubscriptionPeriodInMonths()), RoundingMode.UP);

            if (subscription.getUser().getMoney().compareTo(subscriptionPrice) < 0) {
                throw new ServiceException("NOT_ENOUGH_MONEY");
            }

            User user = subscription.getUser();
            user.setMoney(user.getMoney().subtract(subscriptionPrice));

            Payment payment = new Payment.PaymentBuilder()
                    .user(user)
                    .paymentType(new PaymentType.PaymentTypeBuilder()
                        .id(2)
                        .type("payment")
                        .build())
                    .date(new Timestamp(System.currentTimeMillis()))
                    .amount(subscriptionPrice)
                    .subscription(subscription)
                    .build();

            transactionUnitOfWork.getUserRepository().update(user);
            transactionUnitOfWork.getSubscriptionRepository().add(subscription);
            transactionUnitOfWork.getPaymentRepository().add(payment);
            transactionUnitOfWork.commit();
            return subscription;
        } catch (RepositoryException e) {
            LOG.warn(e);
            transactionUnitOfWork.rollback();
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }
}
