package io.spiffy.common.util;

public class ObfuscateUtil {

    public static String obfuscate(final long key) {
        return BaseUtil.toBase39(obfuscate(key, RandomUtil.randomInt(1, 9), RandomUtil.randomInt(1, 9)));
    }

    public static long unobfuscate(final String key) {
        if (key.matches("[0-9]+")) {
            return Long.parseLong(key);
        }

        return recover(BaseUtil.fromBase39(key));
    }

    private static long reverse(final long original) {
        return obfuscate(original, 0, 0) / 10;
    }

    private static long recover(final long original) {
        return reverse(original / 10) / 10;
    }

    private static long obfuscate(long original, final long first, final long last) {
        long reverse = first;
        do {
            reverse *= 10;
            reverse += original % 10;
            original /= 10;
        } while (original > 0);

        reverse *= 10;
        reverse += last;

        return reverse;
    }
}