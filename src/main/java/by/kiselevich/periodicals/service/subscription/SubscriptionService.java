package by.kiselevich.periodicals.service.subscription;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Service to work with {@link Subscription}
 */
public interface SubscriptionService {
    /**
     * Returns all subscriptions from data source
     * @return {@link List} with all {@link Subscription}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<Subscription> getAllSubscriptions() throws ServiceException;

    /**
     * Returns subscription from data source by id
     * @param id {@link Subscription} {@code id}
     * @return {@link Optional} with {@link Subscription}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    Optional<Subscription> getSubscriptions(int id) throws ServiceException;

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

    /**
     * Stop {@link Subscription} if not expired
     * @param subscription {@link Subscription} to stop
     * @param user {@link User} which owns {@link Subscription}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    void stopSubscription(Subscription subscription, User user) throws ServiceException;

    /**
     * Returns count of all subscription in datasource
     * @return count of subscription
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    int getAllSubscriptionCount() throws ServiceException;
}
