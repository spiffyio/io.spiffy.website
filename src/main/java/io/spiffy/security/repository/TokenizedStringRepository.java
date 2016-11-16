package io.spiffy.security.repository;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import io.spiffy.common.HibernateRepository;
import io.spiffy.security.entity.TokenizedStringEntity;

public class TokenizedStringRepository extends HibernateRepository<TokenizedStringEntity> {

    @Inject
    public TokenizedStringRepository(final SessionFactory sessionFactory) {
        super(TokenizedStringEntity.class, sessionFactory);
    }

    public TokenizedStringEntity get(final String token) {
        final Criteria c = createCriteria();
        c.add(Restrictions.eq("token", token));
        return (TokenizedStringEntity) c.uniqueResult();
    }
}
