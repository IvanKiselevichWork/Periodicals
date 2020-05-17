package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.entity.*;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import org.junit.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SubscriptionValidatorTest extends Assert {

    private static final SubscriptionValidator subscriptionValidator = SubscriptionValidator.getInstance();

    @Test
    public void testCheckSubscriptionPositive() throws ServiceException, ValidatorException {
        Subscription subscription = getSubscription();
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionNullSubscription() throws ValidatorException {
        subscriptionValidator.checkSubscription(null);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidEdition() throws ServiceException, ValidatorException {
        Subscription subscription = getSubscription();
        subscription.getEdition().setName("Invalid edition");
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidPeriod1() throws ServiceException, ValidatorException {
        Subscription subscription = getSubscription();
        subscription.setSubscriptionEndDate(Timestamp.valueOf(LocalDateTime.parse("2009-12-25T10:15:30")));
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidPeriod2() throws ServiceException, ValidatorException {
        Subscription subscription = getSubscription();
        subscription.setSubscriptionEndDate(Timestamp.valueOf(LocalDateTime.parse("2007-11-25T10:15:30")));
        subscriptionValidator.checkSubscription(subscription);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckSubscriptionInvalidUser() throws ServiceException, ValidatorException {
        Subscription subscription = getSubscription();
        subscription.getUser().setLogin("invalid user login");
        subscriptionValidator.checkSubscription(subscription);
    }

    private Subscription getSubscription() throws ServiceException {
        Edition edition = ServiceFactory.getInstance().getEditionService().getAllEditions().get(0);
        User user = ServiceFactory.getInstance().getUserService().getAllUsers().get(0);
        return new Subscription.SubscriptionBuilder()
                .id(0)
                .edition(edition)
                .subscriptionStartDate(Timestamp.valueOf(LocalDateTime.parse("2007-12-25T10:15:30")))
                .subscriptionEndDate(Timestamp.valueOf(LocalDateTime.parse("2008-12-25T10:15:30")))
                .user(user)
                .build();
    }
}
