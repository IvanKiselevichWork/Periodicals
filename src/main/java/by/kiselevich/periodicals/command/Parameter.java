package by.kiselevich.periodicals.command;

public enum Parameter {
    COMMAND("command"),
    LOGIN("login"),
    PASSWORD("password"),
    FULL_NAME("full_name"),
    EMAIL("email"),
    TARGET_LANGUAGE("target_language");

    private String value;

    Parameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
