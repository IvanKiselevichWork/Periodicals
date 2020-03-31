package by.kiselevich.periodicals.exception;

public class EditionValidatorException extends Exception {

    public EditionValidatorException() {
    }

    public EditionValidatorException(String message) {
        super(message);
    }

    public EditionValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditionValidatorException(Throwable cause) {
        super(cause);
    }

    public EditionValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
