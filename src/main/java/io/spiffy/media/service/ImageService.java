package io.spiffy.media.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.ImageEntity;
import io.spiffy.media.repository.ImageRepository;

public class ImageService extends Service<ImageEntity, ImageRepository> {

    @Inject
    public ImageService(final ImageRepository repository) {
        super(repository);
    }

    @Transactional
    public ImageEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public ImageEntity get(final ContentEntity content) {
        return repository.get(content);
    }

    @Transactional
    public ImageEntity post(final ContentEntity content, final FileEntity file, final FileEntity medium,
            final FileEntity thumbnail) {
        ImageEntity entity = get(content);
        if (entity == null) {
            entity = new ImageEntity(content, file, medium, thumbnail);
        }

        repository.saveOrUpdate(entity);

        return entity;
    }
}
