package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.exception.UserValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String LOGIN_REGEX = "[a-zA-z0-9_]{5,40}";
    private static final String PASSWORD_REGEX = ".{8,200}";
    private static final String FULL_NAME_REGEX = ".{1,200}";
    private static final String EMAIL_REGEX = "[a-zA-z0-9_-]{1,40}@[a-zA-z0-9_-]{2,40}\\.[a-z]{2,10}";

    private Pattern pattern;
    private Matcher matcher;

    public void checkUserCredentials(String login, String password, String fullName, String email) throws UserValidatorException {
        if (!isLoginValid(login)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_LOGIN.getKey());
        }
        if (!isPasswordValid(password)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_PASSWORD.getKey());
        }
        if (!isFullNameValid(fullName)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_FULL_NAME.getKey());
        }
        if (!isEmailValid(email)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_EMAIL.getKey());
        }
    }

    public boolean isLoginValid(String login) {
        pattern = Pattern.compile(LOGIN_REGEX);
        matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public boolean isPasswordValid(String password) {
        pattern = Pattern.compile(PASSWORD_REGEX);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isFullNameValid(String fullName) {
        pattern = Pattern.compile(FULL_NAME_REGEX);
        matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    public boolean isEmailValid(String email) {
        pattern = Pattern.compile(EMAIL_REGEX);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
