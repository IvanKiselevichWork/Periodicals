package by.kiselevich.periodicals.exception;

public class ConnectionPoolRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 7972560797503105153L;

    public ConnectionPoolRuntimeException() {
    }

    public ConnectionPoolRuntimeException(String message) {
        super(message);
    }

    public ConnectionPoolRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolRuntimeException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
