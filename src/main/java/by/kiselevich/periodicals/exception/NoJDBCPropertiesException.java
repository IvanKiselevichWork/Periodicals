package by.kiselevich.periodicals.exception;

public class NoJDBCPropertiesException extends Exception {

    public NoJDBCPropertiesException() {
    }

    public NoJDBCPropertiesException(String message) {
        super(message);
    }

    public NoJDBCPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoJDBCPropertiesException(Throwable cause) {
        super(cause);
    }

    public NoJDBCPropertiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
