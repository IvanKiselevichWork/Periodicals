package by.kiselevich.periodicals.service.subscription;

import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface SubscriptionService {

    List<Subscription> getAllSubscriptions() throws ServiceException;
}
