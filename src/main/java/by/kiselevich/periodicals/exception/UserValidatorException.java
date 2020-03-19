package by.kiselevich.periodicals.exception;

public class UserValidatorException extends Exception {

    public UserValidatorException() {
    }

    public UserValidatorException(String message) {
        super(message);
    }

    public UserValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserValidatorException(Throwable cause) {
        super(cause);
    }

    public UserValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
