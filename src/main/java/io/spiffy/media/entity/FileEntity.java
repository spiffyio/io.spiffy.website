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
@Table(name = "MEDIA_FILES", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "type", "archived_at" }) )
public class FileEntity extends HibernateEntity {

    @Getter
    public enum Privacy {
        PRIVATE("content"), //
        PUBLIC("media"), //
        RAW("raw");

        private final String bucket;

        private Privacy(final String bucket) {
            this.bucket = bucket;
        }
    }

    public static final int MIN_NAME_LENGTH = 5;
    public static final int MAX_NAME_LENGTH = 128;

    public static final int MIN_TYPE_LENGTH = 1;
    public static final int MAX_TYPE_LENGTH = 16;

    public static final int MIN_MD5_LENGTH = 22;
    public static final int MAX_MD5_LENGTH = 24;

    public static final int MIN_PRIVACY_LENGTH = 1;
    public static final int MAX_PRIVACY_LENGTH = 16;

    @Column(name = "name", length = MAX_NAME_LENGTH)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = MAX_TYPE_LENGTH, nullable = false)
    private MediaType type;

    @Column(name = "md5", length = MAX_MD5_LENGTH, nullable = false)
    private String md5;

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy", length = MAX_PRIVACY_LENGTH)
    private Privacy privacy = Privacy.PUBLIC;

    public FileEntity(final String name, final MediaType type, final String md5, final Privacy privacy) {
        this.name = name;
        this.type = type;
        this.md5 = md5;
        this.privacy = privacy;
    }

    @Setter
    @Transient
    private byte[] value;
}
