package by.kiselevich.periodicals.command;

public enum ResourceBundleMessages {

    INVALID_LOGIN("invalid_login"),
    INVALID_PASSWORD("invalid_password"),
    INVALID_FULL_NAME("invalid_full_name"),
    INVALID_EMAIL("invalid_email"),
    USER_NOT_FOUND_KEY("user_not_found"),
    LOGIN_IN_USE_KEY("login_in_use"),
    INTERNAL_ERROR("internal_error");

    private String key;

    ResourceBundleMessages(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
