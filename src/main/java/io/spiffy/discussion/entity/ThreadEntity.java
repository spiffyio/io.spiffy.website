package io.spiffy.discussion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.dto.EntityType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "DISCUSSION_THREADS", uniqueConstraints = @UniqueConstraint(columnNames = { "entity_type", "entity_id",
        "archived_at" }) )
public class ThreadEntity extends HibernateEntity {

    public static final int MIN_ENTITY_TYPE_LENGTH = 1;
    public static final int MAX_ENTITY_TYPE_LENGTH = 64;

    public static final int MIN_ENTITY_ID_LENGTH = 1;
    public static final int MAX_ENTITY_ID_LENGTH = 256;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", length = MAX_ENTITY_TYPE_LENGTH, nullable = false)
    private EntityType entityType;

    @Column(name = "entity_id", length = MAX_ENTITY_ID_LENGTH, nullable = false)
    private String entityId;

    public ThreadEntity(final EntityType entityType, final String entityId) {
        this.entityType = entityType;
        this.entityId = entityId;
    }
}
