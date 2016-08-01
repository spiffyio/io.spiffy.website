package io.spiffy.security.repository;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

import io.spiffy.common.HibernateRepository;
import io.spiffy.security.entity.HashedStringEntity;

public class HashedStringRepository extends HibernateRepository<HashedStringEntity> {

    @Inject
    public HashedStringRepository(final SessionFactory sessionFactory) {
        super(HashedStringEntity.class, sessionFactory);
    }
}
