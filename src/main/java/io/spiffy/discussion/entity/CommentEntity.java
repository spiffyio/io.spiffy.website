package io.spiffy.discussion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "DISCUSSION_COMMENTS", uniqueConstraints = @UniqueConstraint(columnNames = { "thread_id", "idempotent_id",
        "account_id", "archived_at" }) )
public class CommentEntity extends HibernateEntity {

    public static final int MIN_IDEMPOTENT_ID_LENGTH = 1;
    public static final int MAX_IDEMPOTENT_ID_LENGTH = 256;

    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    private ThreadEntity thread;

    @Column(name = "idempotent_id", length = MAX_IDEMPOTENT_ID_LENGTH, nullable = false)
    private String idempotentId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "posted_at", columnDefinition = "DATETIME")
    private Date postedAt;

    public CommentEntity(final ThreadEntity thread, final String idempotentId, final long accountId, final Date postedAt) {
        this.thread = thread;
        this.idempotentId = idempotentId;
        this.accountId = accountId;
        this.postedAt = postedAt;
    }

    @Setter
    @Column(name = "comment")
    private String comment;
}
