package io.spiffy.media.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.media.dto.ContentType;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.media.output.GetAccountMediaOutput;
import io.spiffy.common.api.media.output.GetMediaOutput;
import io.spiffy.common.exception.InvalidParameterException;
import io.spiffy.common.util.*;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.ImageEntity;
import io.spiffy.media.entity.VideoEntity;
import io.spiffy.media.manager.SNSManager;
import io.spiffy.media.repository.ContentRepository;

public class ContentService extends Service<ContentEntity, ContentRepository> {

    private static final int THUMBNAIL_SIZE = 160;
    private static final String THUMBNAIL_SUFFIX = "-" + BaseUtil.toBase64(THUMBNAIL_SIZE);

    private static final int MEDIUM_SIZE = 640;
    private static final String MEDIUM_SUFFIX = "-" + BaseUtil.toBase64(MEDIUM_SIZE);

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
    public List<ContentEntity> get(final long account, final List<String> names) {
        return repository.get(account, names);
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
    public GetAccountMediaOutput getContent(final long accountId, final ContentType type, final Long first,
            final int maxResults, final boolean includeFirst) {
        final List<ContentEntity> entities = repository.get(accountId, type, first, maxResults, includeFirst);

        final List<Content> contents = new ArrayList<>();
        for (final ContentEntity entity : entities) {
            final GetMediaOutput output = getContent(entity);
            if (output.getContent() != null) {
                contents.add(output.getContent());
            }
        }

        return new GetAccountMediaOutput(contents);
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
            final String medium = FileService.getUrl(image.getMedium());
            final String thumbnail = FileService.getUrl(image.getThumbnail());

            content = new Content(entity.getName(), file, medium, thumbnail);
        } else if (ContentType.VIDEO.equals(entity.getType())) {
            final VideoEntity video = videoService.get(entity);
            if (video == null) {
                return new GetMediaOutput(error);
            }

            final String poster = FileService.getUrl(video.getPoster());
            final String mp4 = FileService.getUrl(video.getMp4());
            final String webm = FileService.getUrl(video.getWebm());
            final String gif = FileService.getUrl(video.getGif());
            final String thumbnail = FileService.getUrl(video.getThumbnail());

            content = new Content(entity.getName(), poster, mp4, webm, gif, thumbnail);
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

        final FileEntity file = fileService.post(account + "/" + idempotentId, type, value, FileEntity.Privacy.RAW);

        entity = new ContentEntity(account, idempotentId, asType(type), file);
        repository.saveOrUpdate(entity);

        entity.setName(ObfuscateUtil.obfuscate(entity.getId()));
        repository.saveOrUpdate(entity);

        final ContentEntity content = entity;
        ThreadUtil.run(() -> process(content, type, value));

        return entity;
    }

    @Transactional
    public void delete(final long accountId, final List<String> names) {
        final Set<Long> ids = new HashSet<>();
        final List<ContentEntity> entities = get(accountId, names);
        for (final ContentEntity entity : entities) {
            ids.add(entity.getId());
            entity.setArchivedAt(DateUtil.now());
            repository.saveOrUpdate(entity);
        }

        snsManager.publish(ids);
    }

    @Transactional
    public void delete(final long id) {
        final ContentEntity entity = get(id);
        if (entity == null) {
            return;
        }

        if (ContentType.IMAGE.equals(entity.getType())) {
            final ImageEntity image = imageService.get(entity);
            imageService.delete(image);
            fileService.delete(image.getFile());
            fileService.delete(image.getMedium());
            fileService.delete(image.getThumbnail());
        } else if (ContentType.VIDEO.equals(entity.getType())) {
            final VideoEntity video = videoService.get(entity);
            videoService.delete(video);
            fileService.delete(video.getPoster());
            fileService.delete(video.getMp4());
            fileService.delete(video.getWebm());
            fileService.delete(video.getGif());
            fileService.delete(video.getThumbnail());
        }

        fileService.delete(entity.getFile());
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
            final FileEntity file = fileService.post(content.getName(), type, compressValue, FileEntity.Privacy.PUBLIC);

            final byte[] mediumValue = ImageUtil.scale(fileValue, type, MEDIUM_SIZE, null);
            final FileEntity medium;
            if (mediumValue != null) {
                medium = fileService.post(content.getName() + MEDIUM_SUFFIX, type, mediumValue, FileEntity.Privacy.PUBLIC);
            } else {
                medium = file;
            }

            final byte[] thumbnailValue = ImageUtil.thumbnail(fileValue, type, THUMBNAIL_SIZE, null);
            final FileEntity thumbnail;
            if (thumbnailValue != null) {
                thumbnail = fileService.post(content.getName() + THUMBNAIL_SUFFIX, type, thumbnailValue,
                        FileEntity.Privacy.PUBLIC);
            } else {
                thumbnail = file;
            }

            imageService.post(content, file, medium, thumbnail);
            snsManager.publish(content.getId());
            return;
        }

        final FileEntity gif;
        if (MediaType.GIF.equals(type)) {
            gif = fileService.post(content.getName(), MediaType.GIF, value, FileEntity.Privacy.PUBLIC);
        } else {
            gif = null;
        }

        final byte[] posterFullValue = ConverterUtil.convertToPNG(value, content.getName());
        final byte[] posterValue = ImageUtil.scale(posterFullValue, MediaType.PNG, MEDIUM_SIZE, posterFullValue, false);
        final FileEntity poster = fileService.post(content.getName(), MediaType.PNG, posterValue, FileEntity.Privacy.PUBLIC);

        final byte[] mp4Value = ConverterUtil.convertToMP4(value, content.getName());
        final FileEntity mp4 = fileService.post(content.getName(), MediaType.MP4, mp4Value, FileEntity.Privacy.PUBLIC);

        final byte[] webmValue = ConverterUtil.convertToWebM(value, content.getName());
        final FileEntity webm = fileService.post(content.getName(), MediaType.WEBM, webmValue, FileEntity.Privacy.PUBLIC);

        final byte[] thumbnailValue = ImageUtil.thumbnail(posterFullValue, MediaType.PNG, THUMBNAIL_SIZE, null);
        final FileEntity thumbnail;
        if (thumbnailValue != null) {
            thumbnail = fileService.post(content.getName() + THUMBNAIL_SUFFIX, MediaType.PNG, thumbnailValue,
                    FileEntity.Privacy.PUBLIC);
        } else {
            thumbnail = poster;
        }

        videoService.post(content, poster, mp4, webm, gif, thumbnail);
        snsManager.publish(content.getId());
    }

    private static ContentType asType(final MediaType type) {
        if (MediaType.JPG.equals(type) || MediaType.PNG.equals(type)) {
            return ContentType.IMAGE;
        }

        if (MediaType.GIF.equals(type) || MediaType.MOV.equals(type) || MediaType.MP4.equals(type)
                || MediaType.WEBM.equals(type)) {
            return ContentType.VIDEO;
        }

        throw new InvalidParameterException("type", "unknown type: " + type);
    }
}
