package io.spiffy.security.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.util.SecurityUtil;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SECURITY_ENCRYPTED_STRINGS", uniqueConstraints = @UniqueConstraint(columnNames = { "encrypted",
        "archived_at" }) )
public class EncryptedStringEntity extends HibernateEntity {

    public static final int MIN_ENCRYPTED_LENGTH = 1;
    public static final int MAX_ENCRYPTED_LENGTH = 512;

    @Column(name = "encrypted", length = MAX_ENCRYPTED_LENGTH, nullable = false)
    private String encrypted;

    public boolean matches(final String plainString) {
        return StringUtils.equals(getPlainString(), plainString);
    }

    @Transient
    public String getPlainString() {
        return SecurityUtil.decrypt(encrypted);
    }
}
