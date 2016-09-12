package io.spiffy.notification.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.dto.EntityType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "NOTIFICATION_ALERTS", uniqueConstraints = @UniqueConstraint(columnNames = { "account_id", "idempotent_id",
        "archived_at" }))
public class AlertEntity extends HibernateEntity {
    public static final int MIN_IDEMPOTENT_ID_LENGTH = 1;
    public static final int MAX_IDEMPOTENT_ID_LENGTH = 256;

    public static final int MIN_ENTITY_TYPE_LENGTH = 1;
    public static final int MAX_ENTITY_TYPE_LENGTH = 64;

    public static final int MIN_ENTITY_ID_LENGTH = 1;
    public static final int MAX_ENTITY_ID_LENGTH = 256;

    @Column(name = "account_id", nullable = false)
    private Long account;

    @Column(name = "idempotent_id", length = MAX_IDEMPOTENT_ID_LENGTH)
    private String idempotentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", length = MAX_ENTITY_TYPE_LENGTH, nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id", length = MAX_ENTITY_ID_LENGTH, nullable = false)
    private String entityId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_at", columnDefinition = "DATETIME", nullable = false)
    private Date sentAt;

    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "read_at", columnDefinition = "DATETIME")
    private Date readAt;

    public AlertEntity(final long account, final String idempotentId, final EntityType entityType, final String entityId,
            final Date sentAt) {
        this.account = account;
        this.idempotentId = idempotentId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.sentAt = sentAt;
    }
}
