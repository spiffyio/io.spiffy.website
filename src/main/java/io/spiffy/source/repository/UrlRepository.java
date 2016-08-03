package io.spiffy.source.repository;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.source.entity.UrlEntity;

public class UrlRepository extends HibernateRepository<UrlEntity> {

    @Inject
    public UrlRepository(final SessionFactory sessionFactory) {
        super(UrlEntity.class, sessionFactory);
    }

    public UrlEntity get(final String url) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("url", url));

        final List<UrlEntity> list = asList(c.list());
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }
}
