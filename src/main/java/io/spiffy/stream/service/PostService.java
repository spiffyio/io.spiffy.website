package io.spiffy.stream.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.stream.entity.PostEntity;
import io.spiffy.stream.repository.PostRepository;

public class PostService extends Service<PostEntity, PostRepository> {

    @Inject
    public PostService(final PostRepository repository) {
        super(repository);
    }

    @Transactional
    public PostEntity get(final String idempotentId) {
        return repository.get(idempotentId);
    }

    @Transactional
    public List<PostEntity> get(final Long first, final Integer maxResults) {
        return repository.get(first, maxResults);
    }

    @Transactional
    public PostEntity post(final String idempotentId, final long accountId, final long mediaId, final String title,
            final String description) {
        validateIdempotentId(idempotentId);

        PostEntity entity = get(idempotentId);
        if (entity == null) {
            entity = new PostEntity(idempotentId, accountId, mediaId, new Date());
        }

        entity.setTitle(title);
        entity.setDescription(description);

        repository.saveOrUpdate(entity);

        return entity;

    }

    protected void validateIdempotentId(final String idempotentId) {
        ValidationUtil.validateLength("PostEntity.idempotentId", idempotentId, PostEntity.MIN_IDEMPOTENT_ID_LENGTH,
                PostEntity.MAX_IDEMPOTENT_ID_LENGTH);
    }
}