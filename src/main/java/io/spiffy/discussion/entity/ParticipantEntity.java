package io.spiffy.discussion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "DISCUSSION_PARTICIPANTS", uniqueConstraints = @UniqueConstraint(columnNames = { "thread_id", "account_id",
        "archived_at" }))
public class ParticipantEntity extends HibernateEntity {

    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    private ThreadEntity thread;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    public ParticipantEntity(final ThreadEntity thread, final long accountId) {
        this.thread = thread;
        this.accountId = accountId;
    }
}
