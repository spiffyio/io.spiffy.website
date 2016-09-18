package io.spiffy.discussion.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.discussion.entity.CommentEntity;
import io.spiffy.discussion.entity.ThreadEntity;
import io.spiffy.discussion.repository.CommentRepository;

public class CommentService extends Service<CommentEntity, CommentRepository> {

    @Inject
    public CommentService(final CommentRepository repository) {
        super(repository);
    }

    @Transactional
    public CommentEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public CommentEntity get(final ThreadEntity thread, final String idempotentId, final long accountId) {
        return repository.get(thread, idempotentId, accountId);
    }

    @Transactional
    public List<CommentEntity> get(final ThreadEntity thread, final Long first, final int maxResults) {
        return repository.get(thread, first, maxResults);
    }

    @Transactional
    public List<CommentEntity> getMessages(final ThreadEntity thread, final String after) {
        return repository.getMessages(thread, after);
    }

    @Transactional
    public CommentEntity getMostRecent(final ThreadEntity thread) {
        return repository.getMostRecent(thread);
    }

    @Transactional
    public Set<Long> getCommenters(final ThreadEntity thread) {
        return repository.getCommenters(thread);
    }

    @Transactional
    public CommentEntity post(final ThreadEntity thread, final String idempotentId, final long accountId, final Date postedAt,
            final String comment) {
        CommentEntity entity = get(thread, idempotentId, accountId);
        if (entity == null) {
            entity = new CommentEntity(thread, idempotentId, accountId, postedAt);
        } else {
            // TODO archive
        }

        entity.setComment(comment);

        repository.saveOrUpdate(entity);

        return entity;
    }
}
