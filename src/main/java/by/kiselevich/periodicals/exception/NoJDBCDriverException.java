package by.kiselevich.periodicals.exception;

public class NoJDBCDriverException extends Exception {

    public NoJDBCDriverException() {
    }

    public NoJDBCDriverException(String message) {
        super(message);
    }

    public NoJDBCDriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoJDBCDriverException(Throwable cause) {
        super(cause);
    }

    public NoJDBCDriverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
