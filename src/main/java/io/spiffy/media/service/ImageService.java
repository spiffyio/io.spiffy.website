package io.spiffy.media.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.util.BaseUtil;
import io.spiffy.common.util.DateUtil;
import io.spiffy.common.util.ImageUtil;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.ImageEntity;
import io.spiffy.media.repository.ImageRepository;

public class ImageService extends Service<ImageEntity, ImageRepository> {

    private static final int THUMBNAIL_SIZE = 160;
    private static final String THUMBNAIL_SUFFIX = "-" + BaseUtil.toBase64(THUMBNAIL_SIZE);

    private static final int MEDIUM_SIZE = 640;
    private static final String MEDIUM_SUFFIX = "-" + BaseUtil.toBase64(MEDIUM_SIZE);

    private final FileService fileService;

    @Inject
    public ImageService(final ImageRepository repository, final FileService fileService) {
        super(repository);
        this.fileService = fileService;
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

    @Transactional
    public ImageEntity process(final ContentEntity content) {
        final FileEntity input = content.getFile();
        if (input == null) {
            logger.warn("missing raw file for: " + content.getName());
            return null;
        }
        fileService.loadValue(input);

        final FileEntity thumbnail = fileService.get(input.getName() + "-thumbnail", input.getType());
        final byte[] thumbnailValue;
        if (thumbnail != null) {
            fileService.loadValue(thumbnail);
            thumbnailValue = thumbnail.getValue();
        } else {
            thumbnailValue = null;
        }

        return process(content, content.getName(), input.getType(), input.getValue(), thumbnailValue);
    }

    @Transactional
    public ImageEntity process(final ContentEntity content, final String name, final MediaType type, final byte[] value,
            byte[] thumbnailValue) {
        if (value == null) {
            logger.warn("unable to retrieve raw file for: " + name);
            return null;
        }

        final byte[] fileValue = MediaType.JPG.equals(type) ? ImageUtil.removeExif(value) : value;
        final byte[] compressValue = ImageUtil.compress(fileValue, type);
        final FileEntity file = fileService.post(name, type, compressValue, FileEntity.Privacy.PUBLIC);

        final byte[] mediumValue = ImageUtil.scale(fileValue, type, MEDIUM_SIZE, null);
        final FileEntity medium;
        if (mediumValue != null) {
            medium = fileService.post(name + MEDIUM_SUFFIX, type, mediumValue, FileEntity.Privacy.PUBLIC);
        } else {
            medium = file;
        }

        thumbnailValue = thumbnailValue == null ? ImageUtil.thumbnail(fileValue, type, THUMBNAIL_SIZE, null) : thumbnailValue;
        final FileEntity thumbnail;
        if (thumbnailValue != null) {
            thumbnail = fileService.post(name + THUMBNAIL_SUFFIX, type, thumbnailValue, FileEntity.Privacy.PUBLIC);
        } else {
            thumbnail = file;
        }

        return post(content, file, medium, thumbnail);
    }

    @Transactional
    public void delete(final ContentEntity content) {
        delete(get(content));
    }

    @Transactional
    public void delete(final ImageEntity entity) {
        if (entity == null) {
            return;
        }

        fileService.delete(entity.getFile());
        fileService.delete(entity.getMedium());
        fileService.delete(entity.getThumbnail());
        entity.setArchivedAt(DateUtil.now());
        repository.saveOrUpdate(entity);
    }

    @Transactional
    public Content getContent(final String name, final ImageEntity entity) {
        if (entity == null) {
            return null;
        }

        final String file = FileService.getUrl(entity.getFile());
        final String medium = FileService.getUrl(entity.getMedium());
        final String thumbnail = FileService.getUrl(entity.getThumbnail());

        return new Content(name, file, medium, thumbnail);
    }
}
