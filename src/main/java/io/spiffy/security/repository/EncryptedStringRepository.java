package io.spiffy.security.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.security.entity.EncryptedStringEntity;

public class EncryptedStringRepository extends HibernateRepository<EncryptedStringEntity> {

    @Inject
    public EncryptedStringRepository(final SessionFactory sessionFactory) {
        super(EncryptedStringEntity.class, sessionFactory);
    }

    public EncryptedStringEntity get(final String encrypted) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("encrypted", encrypted));
        return (EncryptedStringEntity) c.uniqueResult();
    }
}
