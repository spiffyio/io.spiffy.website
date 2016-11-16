package io.spiffy.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.api.user.dto.Provider;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_CREDENTIALS", uniqueConstraints = { @UniqueConstraint(columnNames = { "account_id", "archived_at" }),
        @UniqueConstraint(columnNames = { "provider", "provider_id", "archived_at" }) })
public class CredentialEntity extends HibernateEntity {

    public static final int MAX_PROVIDER_LENGTH = 16;

    public static final int MIN_PROVIDER_ID_LENGTH = 1;
    public static final int MAX_PROVIDER_ID_LENGTH = 64;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = MAX_PROVIDER_LENGTH, nullable = false)
    private Provider provider;

    @Column(name = "provider_id", length = MAX_PROVIDER_ID_LENGTH, nullable = false)
    private String providerId;

    @Setter
    @Column(name = "password_id")
    private Long passwordId;

    public CredentialEntity(final AccountEntity account, final Provider provider, final String providerId) {
        this.account = account;
        this.provider = provider;
        this.providerId = providerId;
    }
}
