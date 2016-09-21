package io.spiffy.stream.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.stream.entity.FollowerEntity;

public class FollowerRepository extends HibernateRepository<FollowerEntity> {

    @Inject
    public FollowerRepository(final SessionFactory sessionFactory) {
        super(FollowerEntity.class, sessionFactory);
    }

    public FollowerEntity get(final long followerId, final long followeeId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("followerId", followerId));
        c.add(Restrictions.eq("followeeId", followeeId));
        return (FollowerEntity) c.uniqueResult();
    }

    public Set<Long> getFollowees(final long followerId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("followerId", followerId));

        c.setProjection(Projections.distinct(Projections.property("followeeId")));

        final List<Object> result = asSingleProjectionList(c.list());
        final Set<Long> set = new HashSet<>();

        result.forEach(o -> set.add((Long) o));

        return set;
    }
}
