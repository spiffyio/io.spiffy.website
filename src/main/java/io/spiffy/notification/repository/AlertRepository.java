package io.spiffy.notification.repository;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.notification.entity.AlertEntity;

public class AlertRepository extends HibernateRepository<AlertEntity> {

    @Inject
    public AlertRepository(final SessionFactory sessionFactory) {
        super(AlertEntity.class, sessionFactory);
    }

    public AlertEntity get(final long account, final String idempotentId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("account", account));
        c.add(Restrictions.eq("idempotentId", idempotentId));
        return (AlertEntity) c.uniqueResult();
    }

    public List<AlertEntity> getByAccount(final long account) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("account", account));
        c.addOrder(Order.desc("sentAt"));
        return asList(c.list());
    }

    public long getUnreadCount(final long account) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("account", account));
        c.add(Restrictions.isNull("readAt"));
        c.setProjection(Projections.rowCount());
        final Long count = (Long) c.uniqueResult();
        return count == null ? 0L : count;
    }
}