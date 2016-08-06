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
@Table(name = "USER_SESSIONS", uniqueConstraints = @UniqueConstraint(columnNames = { "session_id", "archived_at" }))
public class SessionEntity extends HibernateEntity {

    public static final int MIN_SESSION_ID_LENGTH = 3;
    public static final int MAX_SESSION_ID_LENGTH = 25;

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

    @Column(name = "user_agent", nullable = false)
    private String userAgent;

    @Column(name = "ip_address_id", nullable = false)
    private Long ipAddressId;

    public SessionEntity(final String sessionId, final long tokenId, final long accountId, final Date authenticatedAt,
            final String userAgent, final long ipAddressId) {
        this.sessionId = sessionId;
        this.tokenId = tokenId;
        this.accountId = accountId;
        this.authenticatedAt = authenticatedAt;
        this.userAgent = userAgent;
        this.ipAddressId = ipAddressId;
    }

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "invalidated_at", columnDefinition = "DATETIME")
    private Date invalidatedAt;

    @Setter
    @Transient
    private String token;

}
