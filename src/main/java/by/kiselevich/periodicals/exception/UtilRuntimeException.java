package by.kiselevich.periodicals.exception;

public class UtilRuntimeException extends RuntimeException {

    public UtilRuntimeException() {
    }

    public UtilRuntimeException(String message) {
        super(message);
    }

    public UtilRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilRuntimeException(Throwable cause) {
        super(cause);
    }

    public UtilRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
