package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.EditionValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditionValidator {

    private static final String NAME_REGEX = ".{1,200}";
    private static final String TYPE_REGEX = ".{1,200}";
    private static final String THEME_ID_REGEX = "\\d{1}";
    private static final String PERIODICITY_PER_YEAR_REGEX = "\\d{1,3}";
    private static final String MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS_REGEX = "\\d{1,3}";
    private static final String PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD_REGEX = "\\d{1,3}(\\.\\d+)?";

    public void checkEdition(Edition edition) throws EditionValidatorException {
        if (edition == null) {
            throw new EditionValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkName(edition.getName());
        //checkType(edition.getType());
        //checkThemeId
        //todo validator
    }

    private void checkName(String name) throws EditionValidatorException {
        if (name == null || !isStringMatchesRegex(name, NAME_REGEX)) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_NAME.getKey());
        }
    }

    private void checkTypeId(String typeId) throws EditionValidatorException {
        if (typeId == null || !isStringMatchesRegex(typeId, TYPE_REGEX)) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_TYPE_ID.getKey());
        }
    }

    private void checkThemeId(String themeId) throws EditionValidatorException {
        if (themeId == null || !isStringMatchesRegex(themeId, THEME_ID_REGEX)) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_THEME_ID.getKey());
        }
    }

    private void checkPeriodicityPerYear(String periodicityPerYear) throws EditionValidatorException {
        if (periodicityPerYear == null || !isStringMatchesRegex(periodicityPerYear, PERIODICITY_PER_YEAR_REGEX)) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_PERIODICITY_PER_YEAR.getKey());
        }
    }

    private void checkMinimumSubscriptionPeriodInMonths(String minimumSubscriptionPeriodInMonths) throws EditionValidatorException {
        if (minimumSubscriptionPeriodInMonths == null || !isStringMatchesRegex(minimumSubscriptionPeriodInMonths, MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS_REGEX)) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_MINIMUM_SUBSCRIPTION_PERIOD.getKey());
        }
    }

    private void checkPriceForMinimumSubscriptionPeriod(String priceForMinimumSubscriptionPeriod) throws EditionValidatorException {
        if (priceForMinimumSubscriptionPeriod == null || !isStringMatchesRegex(priceForMinimumSubscriptionPeriod, PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD_REGEX)) {
            throw new EditionValidatorException(ResourceBundleMessages.INVALID_PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD.getKey());
        }
    }

    private boolean isStringMatchesRegex(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
