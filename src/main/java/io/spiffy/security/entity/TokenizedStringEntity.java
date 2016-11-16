package io.spiffy.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SECURITY_TOKENIZED_STRINGS", uniqueConstraints = @UniqueConstraint(columnNames = { "token", "archived_at" }) )
public class TokenizedStringEntity extends HibernateEntity {

    public static final int MIN_TOKEN_LENGTH = 1;
    public static final int MAX_TOKEN_LENGTH = 128;

    @Column(name = "token", length = MAX_TOKEN_LENGTH, nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(name = "encrypted_id", nullable = false)
    private EncryptedStringEntity encrypted;
}
