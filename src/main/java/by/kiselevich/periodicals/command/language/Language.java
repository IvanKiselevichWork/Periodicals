package by.kiselevich.periodicals.command.language;

public enum Language {
    ENGLISH("en"),
    RUSSIAN("ru");

    private String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
