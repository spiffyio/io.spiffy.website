package io.spiffy.user.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.user.entity.AccountEntity;

public class AccountRepository extends HibernateRepository<AccountEntity> {

    @Inject
    public AccountRepository(final SessionFactory sessionFactory) {
        super(AccountEntity.class, sessionFactory);
    }

    public AccountEntity getByUserName(final String userName) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("userName", userName).ignoreCase());
        return (AccountEntity) c.uniqueResult();
    }

    public AccountEntity getByEmailAddressId(final long emailAddressId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("emailAddressId", emailAddressId));
        return (AccountEntity) c.uniqueResult();
    }

    public AccountEntity getByEmailVerificationTokenId(final long id) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("emailVerificationTokenId", id));
        return (AccountEntity) c.uniqueResult();
    }
}
