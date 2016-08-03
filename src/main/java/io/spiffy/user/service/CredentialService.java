package io.spiffy.user.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.user.entity.CredentialEntity;
import io.spiffy.user.repository.CredentialRepository;

public class CredentialService extends Service<CredentialEntity, CredentialRepository> {

    private final SecurityClient securityClient;

    @Inject
    public CredentialService(final CredentialRepository repository, final SecurityClient securityClient) {
        super(repository);
        this.securityClient = securityClient;
    }

    @Transactional
    public CredentialEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public CredentialEntity getByAccountId(final long accountId) {
        return repository.getByAccountId(accountId);
    }

    @Transactional
    public CredentialEntity post(final long accountId, final String password) {
        CredentialEntity entity = getByAccountId(accountId);

        if (entity != null) {
            if (securityClient.matchesHashedString(entity.getPasswordId(), password)) {
                return entity;
            }
        } else {
            entity = new CredentialEntity(accountId);
        }

        validatePassword(password);

        final long passwordId = securityClient.hashString(password);
        entity.setPasswordId(passwordId);

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public boolean matches(final long accountId, final String password) {
        final CredentialEntity entity = getByAccountId(accountId);
        if (entity == null) {
            return false;
        }

        return securityClient.matchesHashedString(entity.getPasswordId(), password);
    }

    protected void validatePassword(final String password) {
        ValidationUtil.validatePassword("CredentialEntity.password", password);
    }
}
