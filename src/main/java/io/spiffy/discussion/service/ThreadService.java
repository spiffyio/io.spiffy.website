package io.spiffy.discussion.service;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.discussion.dto.ThreadDTO;
import io.spiffy.common.dto.EntityType;
import io.spiffy.common.util.DateUtil;
import io.spiffy.discussion.entity.CommentEntity;
import io.spiffy.discussion.entity.ThreadEntity;
import io.spiffy.discussion.manager.DiscussionSNSManager;
import io.spiffy.discussion.repository.ThreadRepository;

public class ThreadService extends Service<ThreadEntity, ThreadRepository> {

    private final CommentService commentService;
    private final DiscussionSNSManager snsManager;

    @Inject
    public ThreadService(final ThreadRepository repository, final CommentService commentService,
            final DiscussionSNSManager snsManager) {
        super(repository);
        this.commentService = commentService;
        this.snsManager = snsManager;
    }

    @Transactional
    public ThreadEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public ThreadEntity get(final EntityType entityType, final String entityId) {
        return repository.get(entityType, entityId);
    }

    @Transactional
    public ThreadEntity get(final ThreadDTO thread) {
        if (thread.getId() != null) {
            return get(thread.getId());
        }

        return post(thread.getEntityType(), thread.getEntityId());
    }

    @Transactional
    public ThreadEntity post(final EntityType entityType, final String entityId) {
        ThreadEntity entity = get(entityType, entityId);
        if (entity == null) {
            entity = new ThreadEntity(entityType, entityId);
            repository.saveOrUpdate(entity);
        }

        return entity;
    }

    @Transactional
    public List<CommentEntity> getComments(final ThreadDTO thread, final Long first, final int maxResults) {
        return commentService.get(get(thread), first, maxResults);
    }

    @Transactional
    public CommentEntity postComment(final ThreadDTO thread, final String idempotentId, final Long accountId,
            final String comment) {
        final ThreadEntity entity = get(thread);
        if (entity == null) {
            return null;
        }

        final CommentEntity commentEntity = commentService.post(entity, idempotentId, accountId, DateUtil.now(), comment);
        snsManager.publish(commentEntity.getId(), Long.parseLong(thread.getEntityId()), new HashSet<Long>());

        return commentEntity;
    }
}
