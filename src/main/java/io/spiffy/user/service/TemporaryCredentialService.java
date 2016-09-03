package io.spiffy.user.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.util.DateUtil;
import io.spiffy.user.entity.TemporaryCredentialEntity;
import io.spiffy.user.repository.TemporaryCredentialRepository;

public class TemporaryCredentialService extends Service<TemporaryCredentialEntity, TemporaryCredentialRepository> {

    private final SecurityClient securityClient;

    @Inject
    public TemporaryCredentialService(final TemporaryCredentialRepository repository, final SecurityClient securityClient) {
        super(repository);
        this.securityClient = securityClient;
    }

    @Transactional
    public TemporaryCredentialEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public TemporaryCredentialEntity getByAccountId(final long accountId) {
        return repository.getByAccountId(accountId);
    }

    @Transactional
    public TemporaryCredentialEntity post(final long accountId, final String password) {
        TemporaryCredentialEntity entity = getByAccountId(accountId);
        if (entity != null) {
            entity.setArchivedAt(DateUtil.now());
            repository.saveOrUpdate(entity);
        }

        final long passwordId = securityClient.hashString(password);
        entity = new TemporaryCredentialEntity(accountId, passwordId, DateUtil.now(30));

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public boolean matches(final long accountId, final String password) {
        final TemporaryCredentialEntity entity = getByAccountId(accountId);
        if (entity == null) {
            return false;
        }

        return securityClient.matchesHashedString(entity.getPasswordId(), password);
    }

    @Transactional
    public void invalidate(final long accountId, final String token) {
        final TemporaryCredentialEntity entity = getByAccountId(accountId);
        if (entity == null) {
            return;
        }

        entity.setArchivedAt(DateUtil.now());
        repository.saveOrUpdate(entity);
    }
}
