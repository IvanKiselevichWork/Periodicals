package by.kiselevich.periodicals.dao.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.DaoException;

import java.util.List;

public interface SubscriptionDao {

    /**
     * Adds {@link Subscription} to data source
     * @param subscription {@link Subscription} to add
     * @throws DaoException if error occurs
     */
    void add(Subscription subscription) throws DaoException;

    /**
     * Updates {@link Subscription} in data source
     * @param subscription {@link Subscription} to update
     * @throws DaoException if error occurs
     */
    void update(Subscription subscription) throws DaoException;

    /**
     * Get all {@link Subscription} from data source
     * @return {@code List} of {@link Subscription}
     * @throws DaoException if error occurs
     */
    List<Subscription> getAllSubscriptions() throws DaoException;

    /**
     * Get {@link Subscription} by {@code id} from data source
     * @param id {@link Subscription} {@code id}
     * @return {@link Subscription} with given {@code id}
     * @throws DaoException if error occurs
     */
    Subscription getSubscriptionById(int id) throws DaoException;
}
