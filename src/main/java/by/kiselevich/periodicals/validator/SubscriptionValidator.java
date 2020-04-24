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
import java.util.List;
import java.util.Optional;

public class SubscriptionValidator {

    private static final int MAXIMUM_SUBSCRIPTION_PERIOD = 12;

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
            List<Edition> editionList = editionService.getEditionsById(edition.getId(), false);
            if (editionList.isEmpty() || !editionList.get(0).equals(edition)) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_EDITION.getKey());
            }
        } catch (ServiceException e) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private void checkPeriodForEditionWithId(Timestamp start, Timestamp end, int editionId) throws ValidatorException {
        try {
            EditionService editionService = ServiceFactory.getInstance().getEditionService();
            List<Edition> editionList = editionService.getEditionsById(editionId, false);
            if (editionList.isEmpty()) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_EDITION.getKey());
            } else {
                int periodInMonths = DateUtil.getIntegerSubscriptionPeriodInMonths(start, end);
                if (periodInMonths < editionList.get(0).getMinimumSubscriptionPeriodInMonths() || periodInMonths > MAXIMUM_SUBSCRIPTION_PERIOD) {
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
            Optional<User> optionalUser = userService.getUserByLogin(user.getLogin());
            if (!optionalUser.isPresent() || !optionalUser.get().equals(user)) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_USER.getKey());
            }
        } catch (ServiceException e) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
