package io.spiffy.stream.repository;

import java.util.List;

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

    public List<PostEntity> get(final Long first, final int maxResults) {
        final Criteria c = createCriteria();
        c.addOrder(Order.desc("postedAt"));

        if (first != null) {
            c.add(Restrictions.lt("id", first));
        }

        c.setMaxResults(maxResults);
        return asList(c.list());
    }
}
