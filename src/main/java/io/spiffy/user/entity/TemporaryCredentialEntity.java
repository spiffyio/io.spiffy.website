package io.spiffy.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_TEMP_CREDENTIALS", uniqueConstraints = @UniqueConstraint(columnNames = { "account_id", "archived_at" }) )
public class TemporaryCredentialEntity extends HibernateEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "password_id", nullable = false)
    private Long passwordId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires_at", columnDefinition = "DATETIME")
    private Date expiresAt;

    public TemporaryCredentialEntity(final long accountId, final long passwordId, final Date expiresAt) {
        this.accountId = accountId;
        this.passwordId = passwordId;
        this.expiresAt = expiresAt;
    }
}
