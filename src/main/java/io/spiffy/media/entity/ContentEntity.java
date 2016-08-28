package io.spiffy.media.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.api.media.dto.ContentType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEDIA_CONTENT", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "archived_at" }),
        @UniqueConstraint(columnNames = { "account_id", "idempotent_id", "archived_at" }) })
public class ContentEntity extends HibernateEntity {
    public static final int MIN_IDEMPOTENT_ID_LENGTH = 1;
    public static final int MAX_IDEMPOTENT_ID_LENGTH = 256;

    public static final int MIN_TYPE_LENGTH = 1;
    public static final int MAX_TYPE_LENGTH = 16;

    public static final int MIN_NAME_LENGTH = 5;
    public static final int MAX_NAME_LENGTH = 64;

    @Column(name = "account_id", length = MAX_TYPE_LENGTH, nullable = false)
    private Long account;

    @Column(name = "idempotent_id", length = MAX_NAME_LENGTH)
    private String idempotentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = MAX_TYPE_LENGTH, nullable = false)
    private ContentType type;

    @Setter
    @Column(name = "name", length = MAX_NAME_LENGTH)
    private String name;

    @Setter
    @Type(type = "yes_no")
    @Column(name = "processed")
    private Boolean processed;

    public ContentEntity(final long account, final String idempotentId, final ContentType type) {
        this.account = account;
        this.idempotentId = idempotentId;
        this.type = type;
    }
}
