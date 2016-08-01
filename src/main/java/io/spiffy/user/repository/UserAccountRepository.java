package io.spiffy.user.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.user.entity.UserAccountEntity;

public class UserAccountRepository extends HibernateRepository<UserAccountEntity> {

    @Inject
    public UserAccountRepository(final SessionFactory sessionFactory) {
        super(UserAccountEntity.class, sessionFactory);
    }

    public UserAccountEntity getByUserName(final String userName) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("userName", userName).ignoreCase());
        return (UserAccountEntity) c.uniqueResult();
    }

    public UserAccountEntity getByEmailAddressId(final long emailAddressId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("emailAddressId", emailAddressId));
        return (UserAccountEntity) c.uniqueResult();
    }
}
