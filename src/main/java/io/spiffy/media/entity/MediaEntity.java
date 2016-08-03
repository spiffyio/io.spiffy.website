package io.spiffy.media.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;
import io.spiffy.common.api.media.dto.MediaType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEDIA_MEDIA", uniqueConstraints = { @UniqueConstraint(columnNames = { "idempotent_id", "archived_at" }),
        @UniqueConstraint(columnNames = { "name", "archived_at" }) })
public class MediaEntity extends HibernateEntity {
    public static final int MIN_IDEMPOTENT_ID_LENGTH = 1;
    public static final int MAX_IDEMPOTENT_ID_LENGTH = 256;

    public static final int MIN_TYPE_LENGTH = 1;
    public static final int MAX_TYPE_LENGTH = 16;

    public static final int MIN_MD5_LENGTH = 22;
    public static final int MAX_MD5_LENGTH = 24;

    public static final int MIN_NAME_LENGTH = 5;
    public static final int MAX_NAME_LENGTH = 64;

    @Column(name = "idempotent_id", length = MAX_IDEMPOTENT_ID_LENGTH, nullable = false)
    private String idempotentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", length = MAX_TYPE_LENGTH, nullable = false)
    private MediaType type;

    @Column(name = "md5", length = MAX_MD5_LENGTH, nullable = false)
    private String md5;

    @Setter
    @Column(name = "name", length = MAX_NAME_LENGTH)
    private String name;

    public MediaEntity(final String idempotentId, final MediaType type, final String md5) {
        this.idempotentId = idempotentId;
        this.type = type;
        this.md5 = md5;
    }
}
