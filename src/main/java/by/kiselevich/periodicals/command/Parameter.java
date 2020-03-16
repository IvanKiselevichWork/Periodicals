package by.kiselevich.periodicals.command;

public enum Parameter {
    COMMAND("command"),
    LOGIN("login"),
    PASSWORD("password"),
    TARGET_LANGUAGE("targetLanguage");

    private String value;

    Parameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
