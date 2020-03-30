package by.kiselevich.periodicals.command;

public enum ResourceBundleMessages {

    INVALID_LOGIN("invalid_login"),
    INVALID_PASSWORD("invalid_password"),
    INVALID_FULL_NAME("invalid_full_name"),
    INVALID_EMAIL("invalid_email"),
    USER_NOT_FOUND_KEY("user_not_found"),
    LOGIN_IN_USE_KEY("login_in_use"),

    INVALID_TITLE("invalid_title"),
    INVALID_TYPE_ID("invalid_type_id"),
    INVALID_THEME_ID("invalid_theme_id"),
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
