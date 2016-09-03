package io.spiffy.user.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.common.util.DateUtil;
import io.spiffy.user.entity.TemporaryCredentialEntity;

public class TemporaryCredentialRepository extends HibernateRepository<TemporaryCredentialEntity> {

    @Inject
    public TemporaryCredentialRepository(final SessionFactory sessionFactory) {
        super(TemporaryCredentialEntity.class, sessionFactory);
    }

    public TemporaryCredentialEntity getByAccountId(final long accountId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("accountId", accountId));
        c.add(Restrictions.gt("expiresAt", DateUtil.now()));
        return (TemporaryCredentialEntity) c.uniqueResult();
    }
}