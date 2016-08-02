package io.spiffy.user.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.user.entity.CredentialEntity;

public class CredentialRepository extends HibernateRepository<CredentialEntity> {

    @Inject
    public CredentialRepository(final SessionFactory sessionFactory) {
        super(CredentialEntity.class, sessionFactory);
    }

    public CredentialEntity getByAccountId(final long accountId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("accountId", accountId));
        return (CredentialEntity) c.uniqueResult();
    }
}