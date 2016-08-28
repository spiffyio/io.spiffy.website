package io.spiffy.media.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.media.entity.ContentEntity;

public class ContentRepository extends HibernateRepository<ContentEntity> {

    @Inject
    public ContentRepository(final SessionFactory sessionFactory) {
        super(ContentEntity.class, sessionFactory);
    }

    public ContentEntity get(final long account, final String idempotentId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("account", account));
        c.add(Restrictions.eq("idempotentId", idempotentId));
        return (ContentEntity) c.uniqueResult();
    }

    public ContentEntity get(final String name) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("name", name));
        return (ContentEntity) c.uniqueResult();
    }
}
