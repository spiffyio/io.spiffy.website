package io.spiffy.media.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.media.entity.MediaEntity;

public class MediaRepository extends HibernateRepository<MediaEntity> {

    @Inject
    public MediaRepository(final SessionFactory sessionFactory) {
        super(MediaEntity.class, sessionFactory);
    }

    public MediaEntity get(final String idempotentId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("idempotentId", idempotentId));
        return (MediaEntity) c.uniqueResult();
    }
}
