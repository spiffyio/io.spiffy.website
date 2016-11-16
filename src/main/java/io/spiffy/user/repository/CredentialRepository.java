package io.spiffy.user.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.common.api.user.dto.Provider;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.entity.CredentialEntity;

public class CredentialRepository extends HibernateRepository<CredentialEntity> {

    @Inject
    public CredentialRepository(final SessionFactory sessionFactory) {
        super(CredentialEntity.class, sessionFactory);
    }

    public CredentialEntity getByAccount(final AccountEntity account) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("account", account));
        return (CredentialEntity) c.uniqueResult();
    }

    public CredentialEntity getByProviderAccount(final Provider provider, final String providerId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("provider", provider));
        c.add(Restrictions.eq("providerId", providerId));
        return (CredentialEntity) c.uniqueResult();
    }
}