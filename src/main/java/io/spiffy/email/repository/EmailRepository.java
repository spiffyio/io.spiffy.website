package io.spiffy.email.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.email.entity.EmailEntity;

public class EmailRepository extends HibernateRepository<EmailEntity> {

    @Inject
    public EmailRepository(final SessionFactory sessionFactory) {
        super(EmailEntity.class, sessionFactory);
    }

    public EmailEntity get(final String idempotentId) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("idempotentId", idempotentId));
        return (EmailEntity) c.uniqueResult();
    }
}
