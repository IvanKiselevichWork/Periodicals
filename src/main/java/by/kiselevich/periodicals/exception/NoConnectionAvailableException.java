package by.kiselevich.periodicals.exception;

public class NoConnectionAvailableException extends Exception {

    public NoConnectionAvailableException() {
    }

    public NoConnectionAvailableException(String message) {
        super(message);
    }

    public NoConnectionAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoConnectionAvailableException(Throwable cause) {
        super(cause);
    }

    public NoConnectionAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
