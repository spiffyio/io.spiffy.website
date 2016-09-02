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
import io.spiffy.common.util.DateUtil;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.ImageEntity;
import io.spiffy.media.entity.VideoEntity;
import io.spiffy.media.manager.SNSManager;
import io.spiffy.media.repository.ContentRepository;

public class ContentService extends Service<ContentEntity, ContentRepository> {

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
            final Content content = getContent(entity).getContent();
            if (content != null) {
                contents.add(content);
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
            content = imageService.getContent(entity.getName(), image);
        } else if (ContentType.VIDEO.equals(entity.getType())) {
            final VideoEntity video = videoService.get(entity);
            content = videoService.getContent(entity.getName(), video);
        } else {
            return new GetMediaOutput(error);
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
        snsManager.publishPosted(content.getId());

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
            imageService.delete(entity);
        } else if (ContentType.VIDEO.equals(entity.getType())) {
            videoService.delete(entity);
        }

        fileService.delete(entity.getFile());
    }

    @Transactional
    public void process(final long id) {
        final ContentEntity content = get(id);

        if (ContentType.IMAGE.equals(content.getType())) {
            imageService.process(content);
        } else if (ContentType.VIDEO.equals(content.getType())) {
            videoService.process(content);
        } else {
            return;
        }

        content.setProcessed(true);
        repository.saveOrUpdate(content);

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
