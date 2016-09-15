package io.spiffy.discussion.repository;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.common.dto.EntityType;
import io.spiffy.discussion.entity.ParticipantEntity;
import io.spiffy.discussion.entity.ThreadEntity;

public class ParticipantRepository extends HibernateRepository<ParticipantEntity> {

    @Inject
    public ParticipantRepository(final SessionFactory sessionFactory) {
        super(ParticipantEntity.class, sessionFactory);
    }

    public ParticipantEntity get(final ThreadEntity thread, final long accountId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("thread", thread));
        c.add(Restrictions.eq("accountId", accountId));
        return (ParticipantEntity) c.uniqueResult();
    }

    public List<ParticipantEntity> getByThread(final ThreadEntity thread) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("thread", thread));
        return asList(c.list());
    }

    public List<ParticipantEntity> getByAccount(final long accountId, final EntityType entityType) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("accountId", accountId));
        c.createAlias("thread", "thread");
        c.add(Restrictions.eq("thread.entityType", entityType));
        return asList(c.list());
    }
}
