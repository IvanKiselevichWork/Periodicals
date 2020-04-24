package by.kiselevich.periodicals.repository.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.repository.Repository;

public interface SubscriptionRepository extends Repository<Subscription> {

    void add(Subscription subscription) throws RepositoryException;
}
