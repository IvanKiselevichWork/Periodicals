package by.kiselevich.periodicals.service.subscription;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface SubscriptionService {
    /**
     * Returns all subscriptions from data source
     * @return {@link List} with all {@link Subscription}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Subscription> getAllSubscriptions() throws ServiceException;

    /**
     * Returns all subscriptions by {@link User} {@code login} from data source
     * @param userLogin {@link User} {@code login}
     * @return {@link List} with {@link Subscription}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Subscription> getAllSubscriptionsByUserLogin(String userLogin) throws ServiceException;

    /**
     * Validate and add {@link Subscription} to datasource
     * @param subscription {@link Subscription} to be added
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void addSubscription(Subscription subscription) throws ServiceException;
}
