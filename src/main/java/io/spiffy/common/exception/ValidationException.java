package io.spiffy.common.exception;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -2925149433623815718L;

    public ValidationException() {
    }

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final Throwable cause) {
        super(cause);
    }

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidationException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
