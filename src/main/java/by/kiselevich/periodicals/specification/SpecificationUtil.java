package by.kiselevich.periodicals.specification;

import by.kiselevich.periodicals.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecificationUtil {

    private static final String EDITION_ID = "edition.id";
    private static final String EDITION_NAME = "edition.name";
    private static final String EDITION_PERIODICITY_PER_YEAR = "edition.periodicity_per_year";
    private static final String EDITION_MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS = "edition.minimum_subscription_period_in_months";
    private static final String EDITION_PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD = "edition.price_for_minimum_subscription_period";

    private static final String EDITION_TYPE_ID = "edition_type.id";
    private static final String EDITION_TYPE_TYPE = "edition_type.type";

    private static final String EDITION_THEME_ID = "edition_theme.id";
    private static final String EDITION_THEME_TITLE = "edition_theme.title";

    private static final String PAYMENT_ID = "payment.id";
    private static final String PAYMENT_DATE = "payment.date";
    private static final String PAYMENT_AMOUNT = "payment.amount";

    private static final String PAYMENT_TYPE_ID = "payment_type.id";
    private static final String PAYMENT_TYPE_TYPE = "payment_type.type";

    private static final String SUBSCRIPTION_ID = "subscription.id";
    private static final String SUBSCRIPTION_START_DATE = "subscription.subscription_start_date";
    private static final String SUBSCRIPTION_END_DATE = "subscription.subscription_end_date";

    private static final String USER_ID = "user.id";
    private static final String USER_LOGIN = "user.login";
    private static final String USER_PASSWORD = "user.password";
    private static final String USER_FULL_NAME = "user.full_name";
    private static final String USER_EMAIL = "user.email";
    private static final String USER_MONEY = "user.money";
    private static final String USER_IS_AVAILABLE = "user.is_available";

    private static final String USER_ROLE_ID = "user_role.id";
    private static final String USER_ROLE_ROLE = "user_role.role";

    protected Edition getEditionFromResultSet(ResultSet resultSet) throws SQLException {
        return new Edition.EditionBuilder()
                .id(resultSet.getInt(EDITION_ID))
                .name(resultSet.getString(EDITION_NAME))
                .editionType(getEditionTypeFromResultSet(resultSet))
                .editionTheme(getEditionThemeFromResultSet(resultSet))
                .periodicityPerYear(resultSet.getInt(EDITION_PERIODICITY_PER_YEAR))
                .minimumSubscriptionPeriodInMonths(resultSet.getInt(EDITION_MINIMUM_SUBSCRIPTION_PERIOD_IN_MONTHS))
                .priceForMinimumSubscriptionPeriod(resultSet.getBigDecimal(EDITION_PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD))
                .build();
    }

    protected EditionType getEditionTypeFromResultSet(ResultSet resultSet) throws SQLException {
        return new EditionType.EditionTypeBuilder()
                .id(resultSet.getInt(EDITION_TYPE_ID))
                .type(resultSet.getString(EDITION_TYPE_TYPE))
                .build();
    }

    protected EditionTheme getEditionThemeFromResultSet(ResultSet resultSet) throws SQLException {
        return new EditionTheme.EditionThemeBuilder()
                .id(resultSet.getInt(EDITION_THEME_ID))
                .title(resultSet.getString(EDITION_THEME_TITLE))
                .build();
    }

    protected Payment getPaymentFromResultSet(ResultSet resultSet) throws SQLException {
        return new Payment.PaymentBuilder()
                .id(resultSet.getInt(PAYMENT_ID))
                .user(getUserFromResultSet(resultSet))
                .paymentType(getPaymentTypeFromResultSet(resultSet))
                .date(resultSet.getTimestamp(PAYMENT_DATE))
                .amount(resultSet.getBigDecimal(PAYMENT_AMOUNT))
                .subscription(getSubscriptionFromResultSet(resultSet))
                .build();
    }
    protected PaymentType getPaymentTypeFromResultSet(ResultSet resultSet) throws SQLException {
        return new PaymentType.PaymentTypeBuilder()
                .id(resultSet.getInt(PAYMENT_TYPE_ID))
                .type(resultSet.getString(PAYMENT_TYPE_TYPE))
                .build();
    }

    protected Subscription getSubscriptionFromResultSet(ResultSet resultSet) throws SQLException {
        return new Subscription.SubscriptionBuilder()
                .id(resultSet.getInt(SUBSCRIPTION_ID))
                .edition(getEditionFromResultSet(resultSet))
                .subscriptionStartDate(resultSet.getTimestamp(SUBSCRIPTION_START_DATE))
                .subscriptionEndDate(resultSet.getTimestamp(SUBSCRIPTION_END_DATE))
                .user(getUserFromResultSet(resultSet))
                .build();
    }

    protected User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder()
                .id(resultSet.getInt(USER_ID))
                .userRole(getUserRoleFromResultSet(resultSet))
                .login(resultSet.getString(USER_LOGIN))
                .password(resultSet.getString(USER_PASSWORD))
                .fullName(resultSet.getString(USER_FULL_NAME))
                .email(resultSet.getString(USER_EMAIL))
                .money(resultSet.getBigDecimal(USER_MONEY))
                .available(resultSet.getBoolean(USER_IS_AVAILABLE))
                .build();
    }

    protected UserRole getUserRoleFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserRole.UserRoleBuilder()
                .id(resultSet.getInt(USER_ROLE_ID))
                .role(resultSet.getString(USER_ROLE_ROLE))
                .build();
    }
}
