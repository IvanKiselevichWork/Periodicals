package by.kiselevich.periodicals.exception;

public class SessionFactoryInitializationException extends RuntimeException {

    public SessionFactoryInitializationException() {
    }

    public SessionFactoryInitializationException(String message) {
        super(message);
    }

    public SessionFactoryInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionFactoryInitializationException(Throwable cause) {
        super(cause);
    }

    public SessionFactoryInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
