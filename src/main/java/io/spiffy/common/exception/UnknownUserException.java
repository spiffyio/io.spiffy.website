package io.spiffy.common.exception;

public class UnknownUserException extends RuntimeException {

    private static final long serialVersionUID = 1608645444248329765L;

    public UnknownUserException() {
    }

    public UnknownUserException(final String message) {
        super(message);
    }

    public UnknownUserException(final Throwable cause) {
        super(cause);
    }

    public UnknownUserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnknownUserException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
