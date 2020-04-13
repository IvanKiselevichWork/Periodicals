package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.Subscription;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.edition.EditionService;

import java.sql.Timestamp;
import java.time.Period;
import java.util.List;

public class SubscriptionValidator {

    private static final int MONTH_PER_YEAR = 12;
    private static final double MONTH_PER_DAY = 0.033;
    private static final int MAXIMUM_SUBSCRIPTION_PERIOD = 12;

    public void checkSubscription(Subscription subscription) throws ValidatorException {
        if (subscription == null) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkEditionId(subscription.getEdition().getId());
        checkPeriodForEditionWithId(subscription.getSubscriptionStartDate(), subscription.getSubscriptionEndDate(), subscription.getEdition().getId());
    }

    private void checkEditionId(int editionId) throws ValidatorException {
        try {
            EditionService editionService = ServiceFactory.getInstance().getEditionService();
            if (editionService.getEditionsById(editionId).isEmpty()) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_ID.getKey());
            }
        } catch (ServiceException e) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private void checkPeriodForEditionWithId(Timestamp start, Timestamp end, int editionId) throws ValidatorException {
        try {
            EditionService editionService = ServiceFactory.getInstance().getEditionService();
            List<Edition> editionList = editionService.getEditionsById(editionId);
            if (editionList.isEmpty()) {
                throw new ValidatorException(ResourceBundleMessages.INVALID_ID.getKey());
            } else {
                Period period = Period.between(start.toLocalDateTime().toLocalDate(), end.toLocalDateTime().toLocalDate());
                double periodInMonths = period.getYears() * MONTH_PER_YEAR + period.getMonths() + period.getDays() * MONTH_PER_DAY;
                if (periodInMonths < editionList.get(0).getMinimumSubscriptionPeriodInMonths() || periodInMonths > MAXIMUM_SUBSCRIPTION_PERIOD) {
                    throw new ValidatorException(ResourceBundleMessages.INVALID_PERIOD.getKey());
                }
            }
        } catch (ServiceException e) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
