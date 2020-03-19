package by.kiselevich.periodicals.command.language;

public enum Language {
    ENGLISH("en"),
    RUSSIAN("ru");

    private String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
