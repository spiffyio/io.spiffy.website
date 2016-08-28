package io.spiffy.media.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.entity.ImageEntity;

public class ImageRepository extends HibernateRepository<ImageEntity> {

    @Inject
    public ImageRepository(final SessionFactory sessionFactory) {
        super(ImageEntity.class, sessionFactory);
    }

    public ImageEntity get(final ContentEntity content) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("content", content));
        return (ImageEntity) c.uniqueResult();
    }
}
