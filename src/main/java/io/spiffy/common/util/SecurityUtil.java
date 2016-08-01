package io.spiffy.common.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import io.spiffy.common.config.AppConfig;
import io.spiffy.common.exception.EncryptionException;

public class SecurityUtil {

    private static final int ITERATIONS = 1000;
    private static final int HASH_LENGTH = 8192;

    private static final SecretKeyFactory SKF = SecurityUtil.getSecretKeyFactory();
    private static final SecureRandom SR = SecurityUtil.getSecureRandom();

    private static final Cipher ENCRYPT_CIPHER;
    private static final Cipher DECRYPT_CIPHER;

    static {
        try {
            final IvParameterSpec ivps = new IvParameterSpec(AppConfig.getEncryptionIV().getBytes("UTF-8"));
            final SecretKeySpec sks = new SecretKeySpec(AppConfig.getEncryptionKey().getBytes("UTF-8"), "AES");

            ENCRYPT_CIPHER = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            ENCRYPT_CIPHER.init(Cipher.ENCRYPT_MODE, sks, ivps);

            DECRYPT_CIPHER = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            DECRYPT_CIPHER.init(Cipher.DECRYPT_MODE, sks, ivps);
        } catch (final Exception e) {
            throw new EncryptionException("unable to encrypt value", e);
        }
    }

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

    public static String encrypt(final String plainString) {
        try {
            final byte[] encrypted = ENCRYPT_CIPHER.doFinal(plainString.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (final Exception e) {
            throw new EncryptionException("unable to encrypt plainString", e);
        }
    }

    public static String decrypt(final String encrypted) {
        try {
            final byte[] original = DECRYPT_CIPHER.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (final Exception e) {
            throw new EncryptionException("unable to decrypt encrypted", e);
        }
    }
}
