package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.factory.ServiceFactory;
import by.kiselevich.periodicals.service.user.UserService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String LOGIN_REGEX = "[a-zA-z0-9_]{5,40}";
    private static final String PASSWORD_REGEX = ".{8,200}";
    private static final String FULL_NAME_REGEX = ".{1,200}";
    private static final String EMAIL_REGEX = "[a-zA-z0-9_.-]{1,40}@[a-zA-z0-9_-]{2,40}\\.[a-z]{2,10}";

    private UserValidator() {}

    private static class UserValidatorHolder {
        private static final UserValidator INSTANCE = new UserValidator();
    }

    public static UserValidator getInstance() {
        return UserValidator.UserValidatorHolder.INSTANCE;
    }

    /**
     * Check {@link User} fields: <p>
     * {@code login}, {@code password}, {@code fullName}, {@code email}
     * @param user {@link User} entity to validate
     * @throws ValidatorException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    public void checkUserCredentialsOnSignUp(User user) throws ValidatorException, ServiceException {
        if (user == null) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkLogin(user.getLogin());
        checkIsLoginInUse(user.getLogin());
        checkPassword(user.getPassword());
        checkFullName(user.getFullName());
        checkEmail(user.getEmail());
    }

    /**
     * Check {@link User} fields: <p>
     * {@code login}, {@code password}
     * @param user {@link User} entity to validate
     * @throws ValidatorException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    public void checkUserCredentialsOnSignIn(User user) throws ValidatorException {
        if (user == null) {
            throw new ValidatorException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }

        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
    }

    /**
     * Check {@code balance}
     * @param value value to check
     * @throws ValidatorException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    public void checkRefillAmount(BigDecimal value) throws ValidatorException {
        if (value == null || isValueHasMoreThanTwoDecimalDigits(value) || isValueHasMoreThanNineDigitsBeforeDot(value) || isValueLessOrEqualsThanZero(value)) {
            throw new ValidatorException(ResourceBundleMessages.INVALID_REFILL_AMOUNT.getKey());
        }
    }


    private boolean isValueHasMoreThanTwoDecimalDigits(BigDecimal value) {
        return !value.multiply(BigDecimal.valueOf(100)).remainder(BigDecimal.ONE).stripTrailingZeros().equals(BigDecimal.ZERO);
    }

    private boolean isValueHasMoreThanNineDigitsBeforeDot(BigDecimal value) {
        return value.toBigInteger().compareTo(BigInteger.valueOf(999999999)) > 0;
    }

    private boolean isValueLessOrEqualsThanZero(BigDecimal value) {
        return value.stripTrailingZeros().compareTo(BigDecimal.valueOf(0)) <= 0;
    }

    private void checkLogin(String login) throws ValidatorException {
        if (login == null || isStringNotMatchesRegex(login, LOGIN_REGEX)) {
            throw new ValidatorException(ResourceBundleMessages.INVALID_LOGIN.getKey());
        }
    }

    private void checkIsLoginInUse(String login) throws ValidatorException, ServiceException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        Optional<User> optionalUser = userService.getUserByLogin(login);
        if (optionalUser.isPresent()) {
            throw new ValidatorException(ResourceBundleMessages.LOGIN_IN_USE_KEY.getKey());
        }
    }

    private void checkPassword(String password) throws ValidatorException {
        if (password == null || isStringNotMatchesRegex(password, PASSWORD_REGEX)) {
            throw new ValidatorException(ResourceBundleMessages.INVALID_PASSWORD.getKey());
        }
    }

    private void checkFullName(String fullName) throws ValidatorException {
        if (fullName == null || isStringNotMatchesRegex(fullName, FULL_NAME_REGEX)) {
            throw new ValidatorException(ResourceBundleMessages.INVALID_FULL_NAME.getKey());
        }
    }

    private void checkEmail(String email) throws ValidatorException {
        if (email == null || isStringNotMatchesRegex(email, EMAIL_REGEX)) {
            throw new ValidatorException(ResourceBundleMessages.INVALID_EMAIL.getKey());
        }
    }

    private boolean isStringNotMatchesRegex(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return !matcher.matches();
    }
}
