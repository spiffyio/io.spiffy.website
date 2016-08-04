package io.spiffy.stream.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "STREAM_POSTS", uniqueConstraints = @UniqueConstraint(columnNames = { "idempotent_id", "archived_at" }))
public class PostEntity extends HibernateEntity {

    public static final int MIN_IDEMPOTENT_ID_LENGTH = 1;
    public static final int MAX_IDEMPOTENT_ID_LENGTH = 256;

    public static final int MIN_TITLE_LENGTH = 1;
    public static final int MAX_TITLE_LENGTH = 80;

    @Column(name = "idempotent_id", length = MAX_IDEMPOTENT_ID_LENGTH, nullable = false)
    private String idempotentId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "media_id", nullable = false)
    private Long mediaId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "posted_at", columnDefinition = "DATETIME")
    private Date postedAt;

    @Setter
    @Column(name = "title", length = MAX_TITLE_LENGTH, nullable = false)
    private String title;

    @Setter
    @Column(name = "description")
    private String description;

    public PostEntity(final String idempotentId, final long accountId, final long mediaId, final Date postedAt) {
        this.idempotentId = idempotentId;
        this.accountId = accountId;
        this.mediaId = mediaId;
        this.postedAt = postedAt;
    }
}
