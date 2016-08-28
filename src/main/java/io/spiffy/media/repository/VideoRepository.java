package io.spiffy.media.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.VideoEntity;

public class VideoRepository extends HibernateRepository<VideoEntity> {

    @Inject
    public VideoRepository(final SessionFactory sessionFactory) {
        super(VideoEntity.class, sessionFactory);
    }

    public VideoEntity get(final ContentEntity content) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("content", content));
        return (VideoEntity) c.uniqueResult();
    }
}
