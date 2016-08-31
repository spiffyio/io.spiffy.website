package io.spiffy.media.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.config.AppConfig;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.FileEntity.Privacy;
import io.spiffy.media.manager.S3Manager;
import io.spiffy.media.repository.FileRepository;

public class FileService extends Service<FileEntity, FileRepository> {

    private final S3Manager mediaManager;

    @Inject
    public FileService(final FileRepository repository, final S3Manager mediaManager) {
        super(repository);
        this.mediaManager = mediaManager;
    }

    @Transactional
    public FileEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public FileEntity get(final String name, final MediaType type) {
        return repository.get(name, type);
    }

    @Transactional
    public FileEntity getByValue(final byte[] value) {
        final String md5 = getMd5(value);
        final List<FileEntity> entities = repository.getByMD5(md5);
        entities.parallelStream().forEach(e -> e.setValue(mediaManager.get(getKey(e))));

        for (final FileEntity entity : entities) {
            if (Arrays.equals(entity.getValue(), value)) {
                return entity;
            }
        }

        return null;
    }

    @Transactional
    public FileEntity post(final String name, final MediaType type, final byte[] value, final Privacy privacy) {
        FileEntity entity = get(name, type);
        if (entity == null) {
            final String md5 = getMd5(value);
            entity = new FileEntity(name, type, md5, privacy);
            mediaManager.put(getKey(entity), value, entity.getType().getContentType(), md5);
        }

        repository.saveOrUpdate(entity);

        return entity;

    }

    public static String getUrl(final FileEntity file) {
        if (file == null) {
            return null;
        }

        return AppConfig.getCdnEndpoint() + "/" + getKey(file);
    }

    private static String getKey(final FileEntity file) {
        return String.format("%s/%s.%s", file.getPrivacy().getBucket(), file.getName(), file.getType().getSubtype());
    }

    private static String getMd5(final byte[] value) {
        return Base64.getEncoder().encodeToString(DigestUtils.md5Digest(value));
    }
}
