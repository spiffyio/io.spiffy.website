package io.spiffy.stream.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "STREAM_POSTS", uniqueConstraints = { @UniqueConstraint(columnNames = { "idempotent_id", "archived_at" }),
        @UniqueConstraint(columnNames = { "name", "archived_at" }) })
public class PostEntity extends HibernateEntity {

    public enum Status {
        APPROVED, REPORTED
    }

    public static final int MIN_IDEMPOTENT_ID_LENGTH = 1;
    public static final int MAX_IDEMPOTENT_ID_LENGTH = 256;

    public static final int MIN_STATUS_LENGTH = 1;
    public static final int MAX_STATUS_LENGTH = 16;

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
    @Column(name = "description")
    private String description;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Type(type = "yes_no")
    @Column(name = "processed")
    private Boolean processed;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = MAX_STATUS_LENGTH)
    private Status status;

    public PostEntity(final String idempotentId, final long accountId, final long mediaId, final Date postedAt) {
        this.idempotentId = idempotentId;
        this.accountId = accountId;
        this.mediaId = mediaId;
        this.postedAt = postedAt;
        processed = false;
    }
}
