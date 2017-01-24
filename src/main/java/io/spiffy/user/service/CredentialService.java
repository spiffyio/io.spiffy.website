package io.spiffy.user.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.api.user.dto.Credentials;
import io.spiffy.common.api.user.dto.Provider;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.user.entity.AccountEntity;
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
    public CredentialEntity getByAccount(final AccountEntity account) {
        return repository.getByAccount(account);
    }

    @Transactional
    public CredentialEntity getByCredentials(final Credentials credentials) {
        return repository.getByProviderAccount(credentials.getProvider(), credentials.getProviderId());
    }

    @Transactional
    public CredentialEntity post(final AccountEntity account, final Credentials credentials) {
        validateProviderId(credentials.getProviderId());

        final CredentialEntity byAccount = getByAccount(account);
        final CredentialEntity byCredentials = getByCredentials(credentials);

        CredentialEntity entity;
        if (byAccount == null) {
            entity = byCredentials;
        } else if (byCredentials == null) {
            entity = byAccount;
        } else {
            if (byAccount.getId() != byCredentials.getId()) {
                throw new RuntimeException("credentials already used for a different account");
            }
            entity = byAccount;
        }

        if (entity == null) {
            entity = new CredentialEntity(account, credentials.getProvider(), credentials.getProviderId());

            if (Provider.EMAIL.equals(credentials.getProvider())) {
                validatePassword(credentials.getPassword());
                final long passwordId = securityClient.hashString(credentials.getPassword());
                entity.setPasswordId(passwordId);
            }
        } else if (Provider.EMAIL.equals(credentials.getProvider())) {
            if (securityClient.matchesHashedString(entity.getPasswordId(), credentials.getPassword())) {
                return entity;
            }

            validatePassword(credentials.getPassword());
            final long passwordId = securityClient.hashString(credentials.getPassword());
            entity.setPasswordId(passwordId);
        }

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public boolean matches(final Credentials credentials) {
        final CredentialEntity entity = getByCredentials(credentials);
        return matches(entity, credentials);
    }

    @Transactional
    public boolean matches(final AccountEntity account, final Credentials credentials) {
        final CredentialEntity entity = getByCredentials(credentials);
        if (matches(entity, credentials)) {
            return false;
        }

        return account.getId() == entity.getAccount().getId();
    }

    public boolean matches(final CredentialEntity entity, final Credentials credentials) {
        if (entity == null) {
            return false;
        }

        if (Provider.EMAIL.equals(credentials.getProvider())) {
            return securityClient.matchesHashedString(entity.getPasswordId(), credentials.getPassword());
        }

        return true;
    }

    protected void validateProviderId(final String providerId) {
        ValidationUtil.validateLength("CredentialEntity.providerId", providerId, CredentialEntity.MIN_PROVIDER_ID_LENGTH,
                CredentialEntity.MAX_PROVIDER_ID_LENGTH);
    }

    protected void validatePassword(final String password) {
        ValidationUtil.validatePassword("CredentialEntity.password", password);
    }
}
