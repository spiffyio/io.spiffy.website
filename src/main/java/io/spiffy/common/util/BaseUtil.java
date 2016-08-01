package io.spiffy.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseUtil {

    private static final char[] BASE2_ALPHABET = { '0', '1' };

    private static final char[] BASE39_ALPHABET = { 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
            'T', 'V', 'W', 'X', 'Z', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x',
            'z', };

    private static final char[] BASE64_ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '-', '_' };

    private static final Map<Character, Integer> BASE2_MAP = toMap(BASE2_ALPHABET);
    private static final Map<Character, Integer> BASE39_MAP = toMap(BASE39_ALPHABET);
    private static final Map<Character, Integer> BASE64_MAP = toMap(BASE64_ALPHABET);

    private static Map<Character, Integer> toMap(final char[] chars) {
        final Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            map.put(chars[i], i);
        }
        return Collections.unmodifiableMap(map);
    }

    public static long fromBase2(final String base2) {
        return fromBase(BASE2_MAP, base2);
    }

    public static long fromBase39(final String base39) {
        return fromBase(BASE39_MAP, base39);
    }

    public static long fromBase64(final String base64) {
        return fromBase(BASE64_MAP, base64);
    }

    private static long fromBase(final Map<Character, Integer> map, final String baseX) {
        final char[] chars = baseX.toCharArray();
        final int base = map.size();
        long base10 = 0;
        long digit = 1;
        for (int i = baseX.length() - 1; i >= 0; i--) {
            base10 += map.get(chars[i]) * digit;
            digit *= base;
        }
        return base10;
    }

    public static String toBase2(final long base10) {
        return toBase(BASE2_ALPHABET, base10);
    }

    public static String toBase39(final long base10) {
        return toBase(BASE39_ALPHABET, base10);
    }

    public static String toBase64(final long base10) {
        return toBase(BASE64_ALPHABET, base10);
    }

    private static String toBase(final char[] alphabet, final long base10) {
        final int base = alphabet.length;
        final StringBuilder builder = new StringBuilder();
        long remainder = base10;
        do {
            final long q = remainder / base;
            final int r = (int) (remainder % base);
            remainder = q;
            builder.insert(0, alphabet[r]);
        } while (remainder != 0);

        return builder.toString();
    }
}