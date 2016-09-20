package io.spiffy.media.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.util.ConverterUtil;
import io.spiffy.common.util.DateUtil;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.ImageEntity;
import io.spiffy.media.entity.VideoEntity;
import io.spiffy.media.repository.VideoRepository;

public class VideoService extends Service<VideoEntity, VideoRepository> {

    private final FileService fileService;
    private final ImageService imageService;

    @Inject
    public VideoService(final VideoRepository repository, final FileService fileService, final ImageService imageService) {
        super(repository);
        this.fileService = fileService;
        this.imageService = imageService;
    }

    @Transactional
    public VideoEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public VideoEntity get(final ContentEntity content) {
        return repository.get(content);
    }

    @Transactional
    public VideoEntity post(final ContentEntity content, final ImageEntity poster, final FileEntity mp4, final FileEntity webm,
            final FileEntity gif) {
        VideoEntity entity = get(content);
        if (entity == null) {
            entity = new VideoEntity(content, poster, mp4, webm, gif);
        }

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public VideoEntity process(final ContentEntity content) {
        final FileEntity input = content.getFile();
        if (input == null) {
            logger.warn("missing raw file for: " + content.getName());
            return null;
        }

        fileService.loadValue(input);
        final byte[] value = input.getValue();
        if (value == null) {
            logger.warn("unable to retrieve raw file for: " + content.getName());
            return null;
        }

        final MediaType type = input.getType();

        final FileEntity gif;
        if (MediaType.GIF.equals(type)) {
            gif = fileService.post(content.getName(), MediaType.GIF, value, FileEntity.Privacy.PUBLIC);
        } else {
            gif = null;
        }

        final byte[] posterValue = ConverterUtil.convertToPNG(value, content.getName());
        final ImageEntity image = imageService.process(content, content.getName(), MediaType.PNG, posterValue, null);

        final byte[] mp4Value = ConverterUtil.convertToMP4(value, content.getName());
        final FileEntity mp4 = fileService.post(content.getName(), MediaType.MP4, mp4Value, FileEntity.Privacy.PUBLIC);

        final byte[] webmValue = ConverterUtil.convertToWebM(value, content.getName());
        final FileEntity webm = fileService.post(content.getName(), MediaType.WEBM, webmValue, FileEntity.Privacy.PUBLIC);

        return post(content, image, mp4, webm, gif);
    }

    @Transactional
    public void delete(final ContentEntity content) {
        delete(get(content));
    }

    @Transactional
    public void delete(final VideoEntity entity) {
        if (entity == null) {
            return;
        }

        fileService.delete(entity.getMp4());
        fileService.delete(entity.getWebm());
        fileService.delete(entity.getGif());
        imageService.delete(entity.getPoster());
        entity.setArchivedAt(DateUtil.now());
        repository.saveOrUpdate(entity);
    }

    @Transactional
    public Content getContent(final String name, final VideoEntity entity) {
        if (entity == null) {
            return null;
        }

        final Content poster = imageService.getContent(name, entity.getPoster());
        final String mp4 = FileService.getUrl(entity.getMp4());
        final String webm = FileService.getUrl(entity.getWebm());
        final String gif = FileService.getUrl(entity.getGif());

        return new Content(name, poster, mp4, webm, gif);
    }
}
