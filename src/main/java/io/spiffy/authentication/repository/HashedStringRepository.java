package io.spiffy.authentication.repository;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

import io.spiffy.authentication.entity.HashedStringEntity;
import io.spiffy.common.HibernateRepository;

public class HashedStringRepository extends HibernateRepository<HashedStringEntity> {

    @Inject
    public HashedStringRepository(final SessionFactory sessionFactory) {
        super(HashedStringEntity.class, sessionFactory);
    }
}
