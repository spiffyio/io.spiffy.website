package io.spiffy.media.repository;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.media.entity.MediaEntity;

public class MediaRepository extends HibernateRepository<MediaEntity> {

    @Inject
    public MediaRepository(final SessionFactory sessionFactory) {
        super(MediaEntity.class, sessionFactory);
    }

    public MediaEntity get(final String idempotentId, final MediaType type) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("idempotentId", idempotentId));
        c.add(Restrictions.eq("type", type));
        return (MediaEntity) c.uniqueResult();
    }

    public MediaEntity getByNameAndExtension(final String name, final MediaType type) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("name", name));
        c.add(Restrictions.eq("type", type));
        return (MediaEntity) c.uniqueResult();
    }

    public List<MediaEntity> getByName(final String name) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("name", name));
        return asList(c.list());
    }

    public List<MediaEntity> getByMD5(final String md5) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("md5", md5));
        return asList(c.list());
    }
}
