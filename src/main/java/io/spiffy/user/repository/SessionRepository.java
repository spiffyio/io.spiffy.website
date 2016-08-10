package io.spiffy.user.repository;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.user.entity.SessionEntity;

public class SessionRepository extends HibernateRepository<SessionEntity> {

    @Inject
    public SessionRepository(final SessionFactory sessionFactory) {
        super(SessionEntity.class, sessionFactory);
    }

    public SessionEntity get(final String sessionId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("sessionId", sessionId));
        c.add(Restrictions.isNull("invalidatedAt"));
        return (SessionEntity) c.uniqueResult();
    }

    public List<SessionEntity> getByAccount(final long accountId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("accountId", accountId));
        c.add(Restrictions.isNull("invalidatedAt"));
        c.addOrder(Order.desc("authenticatedAt"));
        return asList(c.list());
    }
}