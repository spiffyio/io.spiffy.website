package io.spiffy.media.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.media.dto.ContentType;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.media.input.GetMediaOutput;
import io.spiffy.common.exception.InvalidParameterException;
import io.spiffy.common.util.*;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.ImageEntity;
import io.spiffy.media.entity.VideoEntity;
import io.spiffy.media.manager.SNSManager;
import io.spiffy.media.repository.ContentRepository;

public class ContentService extends Service<ContentEntity, ContentRepository> {

    private static final int THUMBNAIL_SIZE = 640;
    private static final String THUMBNAIL_SUFFIX = "-" + BaseUtil.toBase64(THUMBNAIL_SIZE);

    private final SNSManager snsManager;
    private final FileService fileService;
    private final ImageService imageService;
    private final VideoService videoService;

    @Inject
    public ContentService(final ContentRepository repository, final SNSManager snsManager, final FileService fileService,
            final ImageService imageService, final VideoService videoService) {
        super(repository);
        this.snsManager = snsManager;
        this.fileService = fileService;
        this.imageService = imageService;
        this.videoService = videoService;
    }

    @Transactional
    public ContentEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public ContentEntity get(final long account, final String idempotentId) {
        return repository.get(account, idempotentId);
    }

    @Transactional
    public ContentEntity get(final String name) {
        return repository.get(name);
    }

    @Transactional
    public GetMediaOutput getContent(final long id) {
        return getContent(repository.get(id));
    }

    @Transactional
    public GetMediaOutput getContent(final String name) {
        return getContent(repository.get(name));
    }

    @Transactional
    public GetMediaOutput getContent(final ContentEntity entity) {
        if (entity == null) {
            return new GetMediaOutput(GetMediaOutput.Error.UNKNOWN_CONTENT);
        }

        final GetMediaOutput.Error error;
        if (Boolean.FALSE.equals(entity.getProcessed())) {
            error = GetMediaOutput.Error.UNPROCESSED_CONTENT;
        } else {
            error = GetMediaOutput.Error.UNKNOWN_CONTENT;
        }

        final Content content;
        if (ContentType.IMAGE.equals(entity.getType())) {
            final ImageEntity image = imageService.get(entity);
            if (image == null) {
                return new GetMediaOutput(error);
            }

            final String file = FileService.getUrl(image.getFile());
            final String thumbnail = FileService.getUrl(image.getThumbnail());

            content = new Content(file, thumbnail);
        } else if (ContentType.VIDEO.equals(entity.getType())) {
            final VideoEntity video = videoService.get(entity);
            if (video == null) {
                return new GetMediaOutput(error);
            }

            final String poster = FileService.getUrl(video.getPoster());
            final String mp4 = FileService.getUrl(video.getMp4());
            final String webm = FileService.getUrl(video.getWebm());
            final String gif = FileService.getUrl(video.getGif());

            content = new Content(poster, mp4, webm, gif);
        } else {
            return new GetMediaOutput(GetMediaOutput.Error.UNKNOWN_CONTENT);
        }

        return new GetMediaOutput(content);
    }

    @Transactional
    public ContentEntity post(final long account, final String idempotentId, final MediaType type, final byte[] value) {
        ContentEntity entity = get(account, idempotentId);
        if (entity != null) {
            return entity;
        }

        entity = new ContentEntity(account, idempotentId, asType(type));
        repository.saveOrUpdate(entity);

        entity.setName(ObfuscateUtil.obfuscate(entity.getId()));
        repository.saveOrUpdate(entity);

        final ContentEntity content = entity;
        ThreadUtil.run(() -> process(content, type, value));

        return entity;
    }

    @Transactional
    public void process(final long id) {
        final ContentEntity content = get(id);
        content.setProcessed(true);
        repository.saveOrUpdate(content);
    }

    @Transactional
    public void process(final ContentEntity content, final MediaType type, final byte[] value) {
        if (MediaType.JPG.equals(type) || MediaType.PNG.equals(type)) {
            final byte[] fileValue = MediaType.JPG.equals(type) ? ImageUtil.removeExif(value) : value;
            final byte[] compressValue = ImageUtil.compress(fileValue, type);
            final FileEntity file = fileService.post(content.getName(), type, compressValue);

            final byte[] thumbnailValue = ImageUtil.thumbnail(fileValue, type, THUMBNAIL_SIZE, null);
            final FileEntity thumbnail;
            if (thumbnailValue != null) {
                thumbnail = fileService.post(content.getName() + THUMBNAIL_SUFFIX, type, thumbnailValue);
            } else {
                thumbnail = file;
            }

            imageService.post(content, file, thumbnail);
            snsManager.publish(content.getId());
            return;
        }

        final FileEntity gif;
        if (MediaType.GIF.equals(type)) {
            gif = fileService.post(content.getName(), MediaType.GIF, value);
        } else {
            gif = null;
        }

        final byte[] posterFullValue = ConverterUtil.convertToPNG(value, content.getName());
        final byte[] posterValue = ImageUtil.thumbnail(posterFullValue, MediaType.PNG, THUMBNAIL_SIZE, posterFullValue);
        final FileEntity poster = fileService.post(content.getName(), MediaType.PNG, posterValue);

        final byte[] mp4Value = ConverterUtil.convertToMP4(value, content.getName());
        final FileEntity mp4 = fileService.post(content.getName(), MediaType.MP4, mp4Value);

        final byte[] webmValue = ConverterUtil.convertToWebM(value, content.getName());
        final FileEntity webm = fileService.post(content.getName(), MediaType.WEBM, webmValue);

        videoService.post(content, poster, mp4, webm, gif);
        snsManager.publish(content.getId());
    }

    private static ContentType asType(final MediaType type) {
        if (MediaType.JPG.equals(type) || MediaType.PNG.equals(type)) {
            return ContentType.IMAGE;
        }

        if (MediaType.GIF.equals(type) || MediaType.MP4.equals(type) || MediaType.WEBM.equals(type)) {
            return ContentType.VIDEO;
        }

        throw new InvalidParameterException("type", "unknown type: " + type);
    }
}
