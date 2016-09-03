package io.spiffy.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.exception.ValidationException;

public class ValidationUtil {

    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMERIC = "0123456789";
    public static final String ALPHA_NUMERIC = ALPHA + ALPHA.toUpperCase() + NUMERIC;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z][a-z0-9]*$", Pattern.CASE_INSENSITIVE);

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(AppConfig.getEmailPattern(), Pattern.CASE_INSENSITIVE);

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(AppConfig.getPasswordPattern());

    private static final Set<String> INVALID_USERNAMES;

    static {
        final Set<String> invalidUsernames = new HashSet<>();
        invalidUsernames.add("api");
        invalidUsernames.add("react");
        invalidUsernames.add("llc");
        invalidUsernames.add("terms");
        invalidUsernames.add("privacy");
        invalidUsernames.add("stream");
        invalidUsernames.add("sessions");
        invalidUsernames.add("error");
        invalidUsernames.add("index");

        INVALID_USERNAMES = Collections.unmodifiableSet(invalidUsernames);
    }

    public static final Set<Character> ALPHA_NUMERIC_SET;

    static {
        final Set<Character> alphaNumericTempSet = new HashSet<>();
        for (final char c : ALPHA_NUMERIC.toCharArray()) {
            alphaNumericTempSet.add(c);
        }

        ALPHA_NUMERIC_SET = Collections.unmodifiableSet(alphaNumericTempSet);
    }

    public static void validateNotNull(final String message, final Object o) {
        if (o == null) {
            throw new ValidationException(message);
        }
    }

    public static void validateMinInt(final String message, final int value, final int min) {
        if (value < min) {
            throw new ValidationException(String.format("%s %d < %d", message, value, min));
        }
    }

    public static void validateMaxInt(final String message, final int value, final int max) {
        if (value > max) {
            throw new ValidationException(String.format("%s %d > %d", message, value, max));
        }
    }

    public static void validateInt(final String message, final int value, final int min, final int max) {
        validateMinInt(message, value, min);
        validateMaxInt(message, value, max);
    }

    public static void validateLength(final String message, final int length, final int min, final int max) {
        validateInt(message, length, min, max);
    }

    public static void validateLength(final String message, final String text, final int min, final int max) {
        validateNotNull(message, text);
        validateInt(message, text.length(), min, max);
    }

    public static void validateSize(final String message, final Collection<?> collection, final int min, final int max) {
        validateNotNull(message, collection);
        validateInt(message, collection.size(), min, max);
    }

    public static void validateAlphaNumeric(final String message, final String text) {
        validateNotNull(message, text);
        for (final char c : text.toCharArray()) {
            validateAlphaNumeric(message, c);
        }
    }

    public static void validateAlphaNumeric(final String message, final char c) {
        if (!ALPHA_NUMERIC_SET.contains(c)) {
            throw new ValidationException(message);
        }
    }

    public static void validateUsername(final String message, final String username) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new ValidationException(message);
        }

        if (INVALID_USERNAMES.contains(username.toLowerCase())) {
            throw new ValidationException(message);
        }
    }

    public static void validateEmailAddress(final String message, final String emailAddress) {
        if (!EMAIL_ADDRESS_PATTERN.matcher(emailAddress).matches()) {
            throw new ValidationException(message);
        }
    }

    public static void validatePassword(final String message, final String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ValidationException(message);
        }

    }

    public static void validateSameOrNull(final String message, final HibernateEntity a, final HibernateEntity b) {
        if (a == null) {
            return;
        }

        if (b == null) {
            return;
        }

        if (a.getId() != b.getId()) {
            throw new ValidationException(message);
        }
    }
}