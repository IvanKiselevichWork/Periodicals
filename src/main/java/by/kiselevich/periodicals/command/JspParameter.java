package by.kiselevich.periodicals.command;

public enum JspParameter {
    COMMAND("command"),
    LOGIN("login"),
    PASSWORD("password"),
    FULL_NAME("full_name"),
    EMAIL("email"),
    TARGET_LANGUAGE("target_language"),
    ID("id"),
    TITLE("title"),
    TYPE_ID("type_id"),
    THEME_ID("theme_id"),
    PERIODICITY_PER_YEAR("periodicity_per_year"),
    MINIMUM_SUBSCRIPTION_PERIOD("minimum_subscription_period"),
    PRICE_FOR_MINIMUM_SUBSCRIPTION_PERIOD("price_for_minimum_subscription_period");

    private String value;

    JspParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
