package io.spiffy.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_SESSIONS", uniqueConstraints = @UniqueConstraint(columnNames = { "session_id", "archived_at" }) )
public class SessionEntity extends HibernateEntity {

    public static final int MIN_SESSION_ID_LENGTH = 3;
    public static final int MAX_SESSION_ID_LENGTH = 64;

    @Setter
    @Column(name = "session_id", length = MAX_SESSION_ID_LENGTH, nullable = false)
    private String sessionId;

    @Column(name = "token_id", nullable = false)
    private Long tokenId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "authenticated_at", columnDefinition = "DATETIME", nullable = false)
    private Date authenticatedAt;

    @Column(name = "authenticated_ip_address_id", nullable = false)
    private Long authenticatedIPAddressId;

    @Column(name = "authenticated_fingerprint")
    private String authenticatedFingerprint;

    @Column(name = "authenticated_user_agent")
    private String authenticatedUserAgent;

    public SessionEntity(final String sessionId, final long tokenId, final long accountId, final Date authenticatedAt,
            final String fingerprint, final String userAgent, final long ipAddressId) {
        this.sessionId = sessionId;
        this.tokenId = tokenId;
        this.accountId = accountId;
        this.authenticatedAt = authenticatedAt;
        authenticatedFingerprint = fingerprint;
        authenticatedUserAgent = userAgent;
        authenticatedIPAddressId = ipAddressId;
    }

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_accessed_at", columnDefinition = "DATETIME")
    private Date lastAccessedAt;

    @Setter
    @Column(name = "last_ip_address_id", nullable = false)
    private Long lastIPAddressId;

    @Setter
    @Column(name = "last_fingerprint")
    private String lastFingerprint;

    @Setter
    @Column(name = "last_user_agent")
    private String lastUserAgent;

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "invalidated_at", columnDefinition = "DATETIME")
    private Date invalidatedAt;

    @Setter
    @Transient
    private String token;

    @Setter
    @Transient
    private String authenticatedIPAddress;

    @Setter
    @Transient
    private String lastIPAddress;

}
