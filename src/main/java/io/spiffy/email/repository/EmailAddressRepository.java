package io.spiffy.email.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.email.entity.EmailAddressEntity;

public class EmailAddressRepository extends HibernateRepository<EmailAddressEntity> {

    @Inject
    public EmailAddressRepository(final SessionFactory sessionFactory) {
        super(EmailAddressEntity.class, sessionFactory);
    }

    public EmailAddressEntity get(final String address) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("address", address).ignoreCase());
        return (EmailAddressEntity) c.uniqueResult();
    }
}
