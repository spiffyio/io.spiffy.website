package io.spiffy.common.util;

import org.apache.commons.lang3.StringUtils;

public class CsrfUtil {

    private static final int ITERATIONS = 100;
    private static final int HASH_LENGTH = 256;

    public static String generateToken(final String sessionId, final String name) {
        final byte[] hash = SecurityUtil.getHash(sessionId, name.getBytes(), ITERATIONS, HASH_LENGTH);
        return SecurityUtil.toHex(hash);
    }

    public static boolean validateToken(final String sessionId, final String name, final String token) {
        return StringUtils.equals(generateToken(sessionId, name), token);
    }
}
