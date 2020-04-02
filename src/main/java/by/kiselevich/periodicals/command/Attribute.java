package by.kiselevich.periodicals.command;

public enum Attribute {
    MESSAGE("message"),
    USER_ROLE("userRole"),
    LOGIN("login"),
    LANGUAGE("language"),
    ADMIN_PAGE_OPTION("adminPageOption"),
    USERS("users"),
    USERS_COUNT("usersCount"),
    EDITION_MAP("editionMap"),
    EDITIONS_COUNT("editionsCount"),
    PAYMENTS("payments"),
    PAYMENTS_COUNT("paymentsCount"),
    SUBSCRIPTIONS("subscriptions"),
    SUBSCRIPTIONS_COUNT("subscriptionsCount"),
    EDITIONS_THEMES("editionsThemes"),
    EDITIONS_TYPES("editionsTypes");

    private String value;

    Attribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
