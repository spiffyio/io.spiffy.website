package io.spiffy.media.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.FileEntity;
import io.spiffy.media.entity.VideoEntity;
import io.spiffy.media.repository.VideoRepository;

public class VideoService extends Service<VideoEntity, VideoRepository> {

    @Inject
    public VideoService(final VideoRepository repository) {
        super(repository);
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
    public VideoEntity post(final ContentEntity content, final FileEntity poster, final FileEntity mp4, final FileEntity webm,
            final FileEntity gif) {
        VideoEntity entity = get(content);
        if (entity == null) {
            entity = new VideoEntity(content, poster, mp4, webm, gif);
        }

        repository.saveOrUpdate(entity);

        return entity;
    }
}
