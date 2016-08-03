package io.spiffy.email.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.api.email.dto.EmailProperties;
import io.spiffy.common.api.email.dto.EmailType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "EMAIL_EMAILS", uniqueConstraints = @UniqueConstraint(columnNames = { "idempotent_id", "archived_at" }))
public class EmailEntity extends HibernateEntity {

    public static final int MIN_IDEMPOTENT_ID_LENGTH = 1;
    public static final int MAX_IDEMPOTENT_ID_LENGTH = 256;

    @Column(name = "idempotent_id", length = MAX_IDEMPOTENT_ID_LENGTH, nullable = false)
    private String idempotentId;

    @ManyToOne
    @JoinColumn(name = "email_address", nullable = false)
    private EmailAddressEntity emailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_type", nullable = false)
    private EmailType type;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "properties_json", nullable = false)
    private String propertiesJson;

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_at", columnDefinition = "DATETIME")
    private Date sentAt;

    public EmailEntity(final String idempotentId, final EmailAddressEntity emailAddress, final EmailType type,
            final long accountId, final String propertiesJson) {
        this.idempotentId = idempotentId;
        this.emailAddress = emailAddress;
        this.type = type;
        this.accountId = accountId;
        this.propertiesJson = propertiesJson;
    }

    @Setter
    @Transient
    private EmailProperties properties;
}