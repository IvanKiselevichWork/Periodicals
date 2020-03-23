package by.kiselevich.periodicals.exception;

public class NoJDBCPropertiesException extends Exception {

    private static final long serialVersionUID = 4277209190713084422L;

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
