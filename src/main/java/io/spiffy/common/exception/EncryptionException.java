package io.spiffy.common.exception;

public class EncryptionException extends RuntimeException {

    private static final long serialVersionUID = -4352077786650940565L;

    public EncryptionException() {
    }

    public EncryptionException(final String message) {
        super(message);
    }

    public EncryptionException(final Throwable cause) {
        super(cause);
    }

    public EncryptionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EncryptionException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
