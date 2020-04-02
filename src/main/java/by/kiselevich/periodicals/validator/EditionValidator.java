package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.EditionValidatorException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.editiontype.EditionTypeService;
import by.kiselevich.periodicals.service.theme.ThemeService;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditionValidator {

    private static final String NAME_REGEX = ".{1,200}";

    public void checkEdition(Edition edition) throws EditionValidatorException {
        if (edition == null) {
            throw new EditionValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkName(edition.getName());
        checkTypeId(edition.getTypeId());
        checkThemeId(edition.getThemeId());
        checkPeriodicityPerYear(edition.getPeriodicityPerYear());
        checkMinimumSubscriptionPeriodInMonths(edition.getMinimumSubscriptionPeriodInMonths());
        checkPriceForMinimumSubscriptionPeriod(edition.getPriceForMinimumSubscriptionPeriod());

    }

    private void checkName(String name) throws EditionValidatorException {
        if (name == null || !isStringMatchesRegex(name)) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_NAME.getKey());
        }
    }

    private void checkTypeId(int typeId) throws EditionValidatorException {
        try {
            EditionTypeService editionTypeService = ServiceFactory.getInstance().getEditionTypeService();
            if (editionTypeService.getEditionTypeById(typeId).isEmpty()) {
                throw new EditionValidatorException(ResourceBundleMessages.INVALID_TYPE.getKey());
            }
        } catch (ServiceException e) {
            throw new EditionValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private void checkThemeId(int themeId) throws EditionValidatorException {
        try {
            ThemeService themeService = ServiceFactory.getInstance().getThemeService();
            if (themeService.getThemeById(themeId).isEmpty()) {
                throw new EditionValidatorException(ResourceBundleMessages.INVALID_THEME.getKey());
            }
        } catch (ServiceException e) {
            throw new EditionValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    private void checkPeriodicityPerYear(int periodicityPerYear) throws EditionValidatorException {
        if (periodicityPerYear < 1) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_PERIODICITY_PER_YEAR.getKey());
        }
    }

    private void checkMinimumSubscriptionPeriodInMonths(int minimumSubscriptionPeriodInMonths) throws EditionValidatorException {
        if (minimumSubscriptionPeriodInMonths < 1) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_MINIMUM_SUBSCRIPTION_PERIOD.getKey());
        }
    }

    private void checkPriceForMinimumSubscriptionPeriod(BigDecimal priceForMinimumSubscriptionPeriod) throws EditionValidatorException {
        if (priceForMinimumSubscriptionPeriod == null || priceForMinimumSubscriptionPeriod.compareTo(BigDecimal.valueOf(0)) < 1 ) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD.getKey());
        }
    }

    private boolean isStringMatchesRegex(String string) {
        Pattern pattern = Pattern.compile(EditionValidator.NAME_REGEX);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
