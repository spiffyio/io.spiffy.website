package io.spiffy.common.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityUtil {

    private static final int ITERATIONS = 1000;
    private static final int HASH_LENGTH = 8192;

    private static final SecretKeyFactory SKF = SecurityUtil.getSecretKeyFactory();
    private static final SecureRandom SR = SecurityUtil.getSecureRandom();

    public static SecretKeyFactory getSecretKeyFactory() {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(final byte[] array) {
        final BigInteger bi = new BigInteger(1, array);
        final String hex = bi.toString(16);
        final int pad = array.length * 2 - hex.length();
        return pad > 0 ? String.format("%0" + pad + "d", 0) + hex : hex;
    }

    public static byte[] fromHex(final String hex) {
        final byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static byte[] getSalt() {
        final byte[] salt = new byte[1024];
        SR.nextBytes(salt);

        return salt.toString().getBytes();
    }

    public static byte[] getHash(final String plainString, final byte[] salt) {
        return getHash(plainString, salt, ITERATIONS, HASH_LENGTH);
    }

    public static byte[] getHash(final String plainString, final byte[] salt, final int interations, final int hashLength) {
        final PBEKeySpec spec = new PBEKeySpec(plainString.toCharArray(), salt, interations, hashLength);
        try {
            return SKF.generateSecret(spec).getEncoded();
        } catch (final InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean matches(final byte[] hash, final byte[] salt, final String plainString) {
        final byte[] actual = getHash(plainString, salt);
        final byte[] expected = hash;

        int diff = expected.length ^ actual.length;
        for (int i = 0; i < expected.length && i < actual.length; i++) {
            diff |= expected[i] ^ actual[i];
        }
        return diff == 0;
    }
}
