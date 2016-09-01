package io.spiffy.stream.service;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.stream.input.PostActionInput.Action;
import io.spiffy.common.api.stream.output.PostActionOutput;
import io.spiffy.common.util.DateUtil;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.stream.entity.PostEntity;
import io.spiffy.stream.repository.PostRepository;

public class PostService extends Service<PostEntity, PostRepository> {

    @Inject
    public PostService(final PostRepository repository) {
        super(repository);
    }

    @Transactional
    public PostEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public PostEntity getByName(final String name) {
        return repository.getByName(name);
    }

    @Transactional
    public PostEntity get(final String idempotentId) {
        return repository.get(idempotentId);
    }

    @Transactional
    public List<PostEntity> get(final Long accountId, final Long first, final int maxResults, final boolean includeFirst) {
        return repository.get(accountId, first, maxResults, includeFirst);
    }

    @Transactional
    public List<PostEntity> getByMediaId(final long mediaId) {
        return repository.getByMediaId(mediaId);
    }

    @Transactional
    public List<PostEntity> getByMediaIds(final Set<Long> mediaIds) {
        return repository.getByMediaIds(mediaIds);
    }

    @Transactional
    public PostEntity post(final String idempotentId, final long accountId, final long mediaId, final String title,
            final String description) {
        validateIdempotentId(idempotentId);

        PostEntity entity = get(idempotentId);
        if (entity == null) {
            entity = new PostEntity(idempotentId, accountId, mediaId, DateUtil.now());
        }

        entity.setTitle(title);
        entity.setDescription(description);

        repository.saveOrUpdate(entity);

        if (entity.getName() == null) {
            entity.setName(ObfuscateUtil.obfuscate(entity.getId()));
            repository.saveOrUpdate(entity);
        }

        return entity;
    }

    @Transactional
    public void process(final long id) {
        final PostEntity post = get(id);
        post.setProcessed(true);
        repository.saveOrUpdate(post);
    }

    @Transactional
    public void processByMediaId(final long id) {
        final List<PostEntity> posts = getByMediaId(id);
        for (final PostEntity post : posts) {
            post.setProcessed(true);
            repository.saveOrUpdate(post);
        }
    }

    @Transactional
    public void deleteByMediaIds(final Set<Long> ids) {
        final List<PostEntity> posts = getByMediaIds(ids);
        for (final PostEntity post : posts) {
            post.setArchivedAt(DateUtil.now());
            repository.saveOrUpdate(post);
        }
    }

    @Transactional
    public PostActionOutput action(final long postId, final long accountId, final Action action) {
        final PostEntity entity = get(postId);
        if (entity == null) {
            return new PostActionOutput(PostActionOutput.Error.INVALID_POST);
        }

        if (Action.DELETE.equals(action)) {
            if (entity.getAccountId() != accountId) {
                return new PostActionOutput(PostActionOutput.Error.INSUFFICIENT_PRIVILEGES);
            }
            entity.setArchivedAt(DateUtil.now());
            repository.saveOrUpdate(entity);
        }

        if (Action.REPORT.equals(action)) {
            if (!PostEntity.Status.APPROVED.equals(entity.getStatus())) {
                entity.setStatus(PostEntity.Status.REPORTED);
                repository.saveOrUpdate(entity);
            }
        }

        return new PostActionOutput(true);
    }

    protected void validateIdempotentId(final String idempotentId) {
        ValidationUtil.validateLength("PostEntity.idempotentId", idempotentId, PostEntity.MIN_IDEMPOTENT_ID_LENGTH,
                PostEntity.MAX_IDEMPOTENT_ID_LENGTH);
    }
}