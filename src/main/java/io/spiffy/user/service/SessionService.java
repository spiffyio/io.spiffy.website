package io.spiffy.user.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.util.RandomUtil;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.user.entity.SessionEntity;
import io.spiffy.user.repository.SessionRepository;

public class SessionService extends Service<SessionEntity, SessionRepository> {

    private static final int TOKEN_LENGTH = 128;

    private final SecurityClient securityClient;

    @Inject
    public SessionService(final SessionRepository repository, final SecurityClient securityClient) {
        super(repository);
        this.securityClient = securityClient;
    }

    @Transactional
    public SessionEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public SessionEntity get(final String sessionId) {
        return repository.get(sessionId);
    }

    @Transactional
    public List<SessionEntity> getByAccount(final long accountId) {
        final List<SessionEntity> entities = repository.getByAccount(accountId);
        entities.forEach(e -> e.setIpAddress(securityClient.decryptString(e.getIpAddressId())));
        return entities;
    }

    @Transactional
    public SessionEntity post(final String sessionId, final String token, final long accountId, final Date authenticatedAt,
            final String userAgent, final String ipAddress, final Date invalidatedAt) {
        SessionEntity entity = get(sessionId);

        if (entity != null) {
            if (securityClient.matchesHashedString(entity.getTokenId(), token)) {
                return entity;
            }
        } else {
            final long tokenId = securityClient.hashString(token);
            final long ipAddressId = securityClient.encryptString(ipAddress);
            entity = new SessionEntity(sessionId, tokenId, accountId, authenticatedAt, userAgent, ipAddressId);
            entity.setToken(token);
        }

        entity.setInvalidatedAt(invalidatedAt);

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public SessionEntity create(final String sessionId, final long accountId, final String userAgent, final String ipAddress) {
        return post(sessionId, RandomUtil.randomAlphaNumericString(TOKEN_LENGTH), accountId, new Date(), userAgent, ipAddress,
                null);
    }

    @Transactional
    public boolean matches(final String sessionId, final String token) {
        final SessionEntity entity = get(sessionId);
        if (entity == null) {
            return false;
        }

        return securityClient.matchesHashedString(entity.getTokenId(), token);
    }

    protected void validatePassword(final String password) {
        ValidationUtil.validatePassword("SessionEntity.password", password);
    }
}
