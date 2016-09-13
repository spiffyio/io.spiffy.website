package io.spiffy.discussion.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.discussion.entity.CommentEntity;
import io.spiffy.discussion.entity.ThreadEntity;

public class CommentRepository extends HibernateRepository<CommentEntity> {

    @Inject
    public CommentRepository(final SessionFactory sessionFactory) {
        super(CommentEntity.class, sessionFactory);
    }

    public CommentEntity get(final ThreadEntity thread, final String idempotentId, final long accountId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("thread", thread));
        c.add(Restrictions.eq("idempotentId", idempotentId));
        c.add(Restrictions.eq("accountId", accountId));
        return (CommentEntity) c.uniqueResult();
    }

    public List<CommentEntity> get(final ThreadEntity thread, final Long first, final int maxResults) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("thread", thread));
        c.addOrder(Order.asc("postedAt"));

        if (first != null) {
            c.add(Restrictions.gt("id", first));
        }

        c.setMaxResults(maxResults);
        return asList(c.list());
    }

    public Set<Long> getCommenters(final ThreadEntity thread) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("thread", thread));

        c.setProjection(Projections.distinct(Projections.property("accountId")));

        final List<Object> result = asSingleProjectionList(c.list());
        final Set<Long> set = new HashSet<>();

        result.forEach(o -> set.add((Long) o));

        return set;
    }
}
