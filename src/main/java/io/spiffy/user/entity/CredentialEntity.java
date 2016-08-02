package io.spiffy.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_CREDENTIALS", uniqueConstraints = @UniqueConstraint(columnNames = { "account_id", "archived_at" }))
public class CredentialEntity extends HibernateEntity {

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Setter
    @Column(name = "password_id", nullable = false)
    private Long passwordId;

    public CredentialEntity(final Long accountId) {
        this.accountId = accountId;
    }
}
