package io.spiffy.common.exception;

public class UnknownPostException extends RuntimeException {

    private static final long serialVersionUID = 6764762597776128143L;

    public UnknownPostException() {
    }

    public UnknownPostException(final String message) {
        super(message);
    }

    public UnknownPostException(final Throwable cause) {
        super(cause);
    }

    public UnknownPostException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnknownPostException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
