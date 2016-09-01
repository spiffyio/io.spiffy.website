package io.spiffy.stream.repository;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.stream.entity.PostEntity;

public class PostRepository extends HibernateRepository<PostEntity> {

    @Inject
    public PostRepository(final SessionFactory sessionFactory) {
        super(PostEntity.class, sessionFactory);
    }

    public PostEntity get(final String idempotentId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("idempotentId", idempotentId));
        return (PostEntity) c.uniqueResult();
    }

    public PostEntity getByName(final String name) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("name", name));
        c.add(Restrictions.or(Restrictions.isNull("status"), Restrictions.ne("status", PostEntity.Status.REPORTED)));
        return (PostEntity) c.uniqueResult();
    }

    public List<PostEntity> get(final Long accountId, final Long first, final int maxResults, final boolean includeFirst) {
        final Criteria c = createCriteria();
        c.addOrder(Order.desc("postedAt"));
        c.add(Restrictions.eqOrIsNull("processed", Boolean.TRUE));
        c.add(Restrictions.or(Restrictions.isNull("status"), Restrictions.ne("status", PostEntity.Status.REPORTED)));

        if (accountId != null) {
            c.add(Restrictions.eq("accountId", accountId));
        }

        if (first != null) {
            if (includeFirst) {
                c.add(Restrictions.le("id", first));
            } else {
                c.add(Restrictions.lt("id", first));
            }
        }

        c.setMaxResults(maxResults);
        return asList(c.list());
    }

    public List<PostEntity> getByMediaId(final long mediaId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("mediaId", mediaId));
        return asList(c.list());
    }

    public List<PostEntity> getByMediaIds(final Set<Long> mediaIds) {
        final Criteria c = createCriteria();
        c.add(Restrictions.in("mediaId", mediaIds));
        return asList(c.list());
    }
}
