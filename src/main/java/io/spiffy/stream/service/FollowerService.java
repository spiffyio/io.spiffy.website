package io.spiffy.stream.service;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.util.DateUtil;
import io.spiffy.stream.entity.FollowerEntity;
import io.spiffy.stream.repository.FollowerRepository;

public class FollowerService extends Service<FollowerEntity, FollowerRepository> {

    @Inject
    public FollowerService(final FollowerRepository repository) {
        super(repository);
    }

    @Transactional
    public FollowerEntity get(final long followerId, final long followeeId) {
        return repository.get(followerId, followeeId);
    }

    @Transactional
    public Set<Long> getFollowees(final long followerId) {
        return repository.getFollowees(followerId);
    }

    @Transactional
    public FollowerEntity post(final long followerId, final long followeeId) {
        FollowerEntity entity = get(followerId, followeeId);
        if (entity == null) {
            entity = new FollowerEntity(followerId, followeeId);
        }

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public void unfollow(final long followerId, final long followeeId) {
        final FollowerEntity entity = get(followerId, followeeId);
        if (entity == null) {
            return;
        }

        entity.setArchivedAt(DateUtil.now());

        return;
    }
}
