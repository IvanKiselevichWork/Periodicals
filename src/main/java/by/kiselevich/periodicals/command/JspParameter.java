package by.kiselevich.periodicals.command;

public enum JspParameter {
    COMMAND("command"),
    LOGIN("login"),
    PASSWORD("password"),
    FULL_NAME("full_name"),
    EMAIL("email"),
    TARGET_LANGUAGE("target_language"),
    ID("id");

    private String value;

    JspParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
