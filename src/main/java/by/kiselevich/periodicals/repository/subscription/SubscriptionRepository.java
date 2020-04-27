package by.kiselevich.periodicals.repository.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface SubscriptionRepository extends Repository<Subscription> {

    /**
     * Adds {@code Subscription} to data source
     * @param subscription {@link Subscription} to add
     * @throws RepositoryException if error occurs
     */
    void add(Subscription subscription) throws RepositoryException;
}
