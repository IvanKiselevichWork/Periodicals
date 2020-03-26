package by.kiselevich.periodicals.command;

public enum Attribute {
    MESSAGE("message"),
    USER_ROLE("userRole"),
    LOGIN("login"),
    LANGUAGE("language"),
    ADMIN_PAGE_OPTION("adminPageOption"),
    USERS("users"),
    USERS_COUNT("usersCount"),
    EDITIONS("editions"),
    EDITIONS_COUNT("editionsCount"),
    PAYMENTS("payments"),
    PAYMENTS_COUNT("paymentsCount");

    private String value;

    Attribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
