package io.spiffy.media.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.media.entity.MediaEntity;
import io.spiffy.media.manager.MediaManager;
import io.spiffy.media.repository.MediaRepository;

public class MediaService extends Service<MediaEntity, MediaRepository> {

    private final MediaManager mediaManager;

    @Inject
    public MediaService(final MediaRepository repository, final MediaManager mediaManager) {
        super(repository);
        this.mediaManager = mediaManager;
    }

    @Transactional
    public MediaEntity get(final String idempotentId) {
        return repository.get(idempotentId);
    }

    @Transactional
    public MediaEntity post(final String idempotentId, final MediaType type, final byte[] value) {
        validateIdempotentId(idempotentId);

        final String md5 = DigestUtils.md5DigestAsHex(value);

        MediaEntity entity = get(idempotentId);
        if (entity == null) {
            entity = new MediaEntity(idempotentId, type, md5);
        }

        repository.saveOrUpdate(entity);

        if (StringUtils.isEmpty(entity.getName())) {
            entity.setName(ObfuscateUtil.obfuscate(entity.getId()));
            mediaManager.put(getKey(entity), value, "image/" + entity.getType().name().toLowerCase(), md5);
            repository.saveOrUpdate(entity);
        }

        return entity;

    }

    protected void validateIdempotentId(final String idempotentId) {
        ValidationUtil.validateLength("MediaEntity.idempotentId", idempotentId, MediaEntity.MIN_IDEMPOTENT_ID_LENGTH,
                MediaEntity.MAX_IDEMPOTENT_ID_LENGTH);
    }

    private String getKey(final MediaEntity media) {
        final String extension = media.getType().name().toLowerCase();
        return String.format("images/%ss/%s.%s", extension, media.getName(), extension);
    }
}
