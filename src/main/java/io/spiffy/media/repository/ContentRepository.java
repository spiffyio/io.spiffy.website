package io.spiffy.media.repository;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.common.api.media.dto.ContentType;
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

    public List<ContentEntity> get(final long account, final List<String> names) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("account", account));
        c.add(Restrictions.in("name", names));
        return asList(c.list());
    }

    public List<ContentEntity> get(final long accountId, final ContentType type, final Long first, final int maxResults,
            final boolean includeFirst) {
        final Criteria c = createCriteria();
        c.addOrder(Order.desc("id"));
        c.add(Restrictions.eq("account", accountId));
        c.add(Restrictions.eq("type", type));
        c.add(Restrictions.eqOrIsNull("processed", true));

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
}
