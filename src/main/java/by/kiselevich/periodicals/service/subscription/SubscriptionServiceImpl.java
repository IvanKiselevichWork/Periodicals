package by.kiselevich.periodicals.service.subscription;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.subscription.SubscriptionRepository;
import by.kiselevich.periodicals.specification.subscription.FindAllSubscriptions;
import by.kiselevich.periodicals.specification.subscription.FindAllSubscriptionsByUserLogin;
import by.kiselevich.periodicals.validator.SubscriptionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        try {
            subscriptionValidator.checkSubscription(subscription);
            // todo check money
            subscriptionRepository.add(subscription);
            return subscription;
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }
}
