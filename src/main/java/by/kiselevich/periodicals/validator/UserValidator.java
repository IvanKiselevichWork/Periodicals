package by.kiselevich.periodicals.validator;

public class UserValidator {

    public boolean isStringValid(String string) {
        return string != null && !string.isEmpty();
    }
}
