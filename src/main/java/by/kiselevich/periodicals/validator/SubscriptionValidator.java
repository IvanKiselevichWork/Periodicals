package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.user.UserService;
import by.kiselevich.periodicals.util.DateUtil;

import java.sql.Timestamp;

public class SubscriptionValidator {

    private static final int MAXIMUM_SUBSCRIPTION_PERIOD = 12;

    private SubscriptionValidator() {}

    private static class SubscriptionValidatorHolder {
        private static final SubscriptionValidator INSTANCE = new SubscriptionValidator();
    }

    public static SubscriptionValidator getInstance() {
        return SubscriptionValidator.SubscriptionValidatorHolder.INSTANCE;
    }

    /**
     * Check {@link Subscription} fields: <p>
     * {@code name}, {@code edition}, subscription period by calculation from {@code subscriptionStartDate} and {@code subscriptionEndDate}, {@code user}
     * @param subscription {@link Subscription} entity to validate
     * @throws ValidatorException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    public void checkSubscription(Subscription subscription) throws ValidatorException {
        if (subscription == null) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkEdition(subscription.getEdition());
        checkPeriodForEditionWithId(subscription.getSubscriptionStartDate(), subscription.getSubscriptionEndDate(), subscription.getEdition().getId());
        checkUser(subscription.getUser());
    }

    private void checkEdition(Edition edition) throws ValidatorException {
        try {
            EditionService editionService = ServiceFactory.getInstance().getEditionService();
            Edition edition1 = editionService.getEditionById(edition.getId(), false);
            if (edition1 == null || !edition1.equals(edition)) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_EDITION.getKey());
            }
        } catch (ServiceException e) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private void checkPeriodForEditionWithId(Timestamp start, Timestamp end, int editionId) throws ValidatorException {
        try {
            EditionService editionService = ServiceFactory.getInstance().getEditionService();
            Edition edition1 = editionService.getEditionById(editionId, false);
            if (edition1 == null) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_EDITION.getKey());
            } else {
                int periodInMonths = DateUtil.getIntegerSubscriptionPeriodInMonths(start, end);
                if (periodInMonths < edition1.getMinimumSubscriptionPeriodInMonths() || periodInMonths > MAXIMUM_SUBSCRIPTION_PERIOD) {
                    throw new ValidatorException(ResourceBundleMessages.INVALID_PERIOD.getKey());
                }
            }
        } catch (ServiceException e) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private void checkUser(User user) throws ValidatorException {
        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            User user1 = userService.getUserByLogin(user.getLogin());
            if (user1 == null || !user1.equals(user)) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_USER.getKey());
            }
        } catch (ServiceException e) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
