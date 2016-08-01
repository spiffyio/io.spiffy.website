package io.spiffy.security.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.util.SecurityUtil;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SECURITY_HASHED_STRINGS")
public class HashedStringEntity extends HibernateEntity {

    public static final int MIN_HASH_LENGTH = 2048;
    public static final int MAX_HASH_LENGTH = 2048;

    public static final int MIN_SALT_LENGTH = 18;
    public static final int MAX_SALT_LENGTH = 22;

    @Column(name = "hash", length = MAX_HASH_LENGTH, nullable = false)
    private String hash;

    @Column(name = "salt", length = MAX_SALT_LENGTH, nullable = false)
    private String salt;

    public boolean matches(final String plainString) {
        final byte[] actual = SecurityUtil.getHash(plainString, SecurityUtil.fromHex(salt));
        final byte[] expected = SecurityUtil.fromHex(hash);

        int diff = expected.length ^ actual.length;
        for (int i = 0; i < expected.length && i < actual.length; i++) {
            diff |= expected[i] ^ actual[i];
        }
        return diff == 0;
    }
}
