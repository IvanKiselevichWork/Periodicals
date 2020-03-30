package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.UserValidatorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String LOGIN_REGEX = "[a-zA-z0-9_]{5,40}";
    private static final String PASSWORD_REGEX = ".{8,200}";
    private static final String FULL_NAME_REGEX = ".{1,200}";
    private static final String EMAIL_REGEX = "[a-zA-z0-9_-]{1,40}@[a-zA-z0-9_-]{2,40}\\.[a-z]{2,10}";

    public void checkUserCredentialsOnSignUp(User user) throws UserValidatorException {
        if (user == null) {
            throw new UserValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkFullName(user.getFullName());
        checkEmail(user.getEmail());
    }

    public void checkUserCredentialsOnSignIn(User user) throws UserValidatorException {
        if (user == null) {
            throw new UserValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
    }

    private void checkLogin(String login) throws UserValidatorException {
        if (login == null || !isStringMatchesRegex(login, LOGIN_REGEX)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_LOGIN.getKey());
        }
    }

    private void checkPassword(String password) throws UserValidatorException {
        if (password == null || !isStringMatchesRegex(password, PASSWORD_REGEX)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_PASSWORD.getKey());
        }
    }

    private void checkFullName(String fullName) throws UserValidatorException {
        if (fullName == null || !isStringMatchesRegex(fullName, FULL_NAME_REGEX)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_FULL_NAME.getKey());
        }
    }

    private void checkEmail(String email) throws UserValidatorException {
        if (email == null || !isStringMatchesRegex(email, EMAIL_REGEX)) {
            throw new UserValidatorException(ResourceBundleMessages.INVALID_EMAIL.getKey());
        }
    }

    private boolean isStringMatchesRegex(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
