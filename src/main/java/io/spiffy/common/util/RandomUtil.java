package io.spiffy.common.util;

import java.util.Random;

public class RandomUtil {

    public static Random random = new Random();

    public static String randomAlphaNumericString(final int length) {
        ValidationUtil.validateLength("randomString.length", length, 0, 256);

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(randomAlphaNumericChar());
        }
        return builder.toString();
    }

    public static char randomAlphaNumericChar() {
        int offset = random.nextInt(36);

        if (offset < 10) {
            return (char) ('0' + offset);
        }

        offset -= 10;

        if (random.nextBoolean()) {
            return (char) ('a' + offset);
        } else {
            return (char) ('A' + offset);
        }
    }

    public static int randomInt(final int min, final int max) {
        return random.nextInt(max - min) + min;
    }

    public static long randomLong() {
        return random.nextLong();
    }
}
