package io.spiffy.media.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.media.input.GetMediaOutput;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.ConverterUtil;
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
    public MediaEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public GetMediaOutput getMedia(final long id) {
        final MediaEntity entity = get(id);

        final List<MediaEntity> entities = repository.getByName(entity.getName());
        final List<MediaType> types = new ArrayList<>();
        entities.forEach(e -> types.add(e.getType()));

        types.sort((a, b) -> Integer.compare(a.getPriority(), b.getPriority()));

        return new GetMediaOutput(AppConfig.getCdnEndpoint() + "/" + getKeyWithoutExtension(entity), entity.getName(), types);
    }

    @Transactional
    public MediaEntity get(final String idempotentId, final MediaType type) {
        return repository.get(idempotentId, type);
    }

    @Transactional
    public MediaEntity getByNameAndExtension(final String name, final MediaType type) {
        return repository.getByNameAndExtension(name, type);
    }

    @Transactional
    public MediaEntity getByValue(final String md5, final byte[] value) {
        final List<MediaEntity> entities = repository.getByMD5(md5);
        entities.parallelStream().forEach(e -> e.setValue(mediaManager.get(getKey(e))));

        for (final MediaEntity entity : entities) {
            if (Arrays.equals(entity.getValue(), value)) {
                return entity;
            }
        }

        return null;
    }

    @Transactional
    public MediaEntity post(final String idempotentId, final MediaType type, final byte[] value) {
        final MediaEntity entity = post(idempotentId, type, value, null);

        final String name = entity.getName();

        if (MediaType.GIF.equals(type) || MediaType.MP4.equals(type)) {
            if (getByNameAndExtension(name, MediaType.WEBM) == null) {
                post(idempotentId, MediaType.WEBM, ConverterUtil.convertToWebM(value, name), name);
            }
        }

        if (MediaType.GIF.equals(type) || MediaType.WEBM.equals(type)) {
            if (getByNameAndExtension(name, MediaType.MP4) == null) {
                post(idempotentId, MediaType.MP4, ConverterUtil.convertToMP4(value, name), name);
            }
        }

        return entity;
    }

    @Transactional
    private MediaEntity post(final String idempotentId, final MediaType type, final byte[] value, final String name) {
        validateIdempotentId(idempotentId);

        final String md5 = Base64.getEncoder().encodeToString(DigestUtils.md5Digest(value));

        MediaEntity entity = getByValue(md5, value);
        if (entity != null) {
            return entity;
        }

        entity = get(idempotentId, type);
        if (entity == null) {
            entity = new MediaEntity(idempotentId, type, md5);
        }

        repository.saveOrUpdate(entity);

        if (StringUtils.isEmpty(entity.getName())) {
            if (StringUtils.isEmpty(name)) {
                entity.setName(ObfuscateUtil.obfuscate(entity.getId()));
            } else {
                entity.setName(name);
            }
            mediaManager.put(getKey(entity), value, entity.getType().getContentType(), md5);
            repository.saveOrUpdate(entity);
        }

        return entity;

    }

    protected void validateIdempotentId(final String idempotentId) {
        ValidationUtil.validateLength("MediaEntity.idempotentId", idempotentId, MediaEntity.MIN_IDEMPOTENT_ID_LENGTH,
                MediaEntity.MAX_IDEMPOTENT_ID_LENGTH);
    }

    private static String getKey(final MediaEntity media) {
        return String.format("%s%s", getKeyWithoutExtension(media), media.getType().getSubtype());
    }

    private static String getKeyWithoutExtension(final MediaEntity media) {
        return String.format("media/%s.", media.getName());
    }
}
