package io.spiffy.stream.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "STREAM_FOLLOWERS", uniqueConstraints = @UniqueConstraint(columnNames = { "follower_id", "followee_id",
        "archived_at" }) )
public class FollowerEntity extends HibernateEntity {

    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @Column(name = "followee_id", nullable = false)
    private Long followeeId;

    public FollowerEntity(final long followerId, final long followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }
}
