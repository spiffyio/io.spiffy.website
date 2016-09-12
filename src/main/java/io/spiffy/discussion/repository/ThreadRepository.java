package io.spiffy.discussion.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.common.dto.EntityType;
import io.spiffy.discussion.entity.ThreadEntity;

public class ThreadRepository extends HibernateRepository<ThreadEntity> {

    @Inject
    public ThreadRepository(final SessionFactory sessionFactory) {
        super(ThreadEntity.class, sessionFactory);
    }

    public ThreadEntity get(final EntityType entityType, final String entityId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("entityType", entityType));
        c.add(Restrictions.eq("entityId", entityId));
        return (ThreadEntity) c.uniqueResult();
    }
}
