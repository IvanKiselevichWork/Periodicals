package by.kiselevich.periodicals.command;

public enum Attribute {
    MESSAGE("message"),
    USER_TYPE("userType"),
    LOGIN("login"),
    LANGUAGE("language"),
    ADMIN_PAGE_OPTION("adminPageOption"),
    USER_PAGE_OPTION("userPageOption"),
    USER_BALANCE("userBalance"),
    USERS("users"),
    USERS_COUNT("usersCount"),
    EDITIONS("editions"),
    EDITIONS_COUNT("editionsCount"),
    PAYMENTS("payments"),
    PAYMENTS_COUNT("paymentsCount"),
    SUBSCRIPTIONS("subscriptions"),
    SUBSCRIPTIONS_COUNT("subscriptionsCount"),
    EDITIONS_THEMES("editionsThemes"),
    EDITIONS_TYPES("editionsTypes"),
    EDITION_NAME_VALUE("editionNameValue"),
    EDITION_TYPE_ID_VALUE("editionTypeIdValue"),
    EDITION_THEME_ID_VALUE("editionThemeIdValue");

    private final String value;

    Attribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
