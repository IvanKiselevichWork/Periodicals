package by.kiselevich.periodicals.command;

public enum ResourceBundleMessages {

    INVALID_LOGIN("invalid_login"),
    INVALID_PASSWORD("invalid_password"),
    INVALID_FULL_NAME("invalid_full_name"),
    INVALID_EMAIL("invalid_email"),
    USER_NOT_FOUND_KEY("user_not_found"),
    USER_BLOCKED("user_blocked"),
    LOGIN_IN_USE_KEY("login_in_use"),

    INVALID_NAME("invalid_name"),
    INVALID_TYPE("invalid_type"),
    INVALID_THEME("invalid_theme"),
    INVALID_PERIODICITY_PER_YEAR("invalid_periodicity_per_year"),
    INVALID_MINIMUM_SUBSCRIPTION_PERIOD("invalid_minimum_subscription_period"),
    INVALID_PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD("invalid_price_for_minimum_subscription_period"),

    INVALID_DATA_FORMAT("invalid_data_format"),

    COPYRIGHT("copyright"),

    INTERNAL_ERROR("internal_error");

    private String key;

    ResourceBundleMessages(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
