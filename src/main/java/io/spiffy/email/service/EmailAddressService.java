package io.spiffy.email.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.security.client.SecurityClient;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.email.entity.EmailAddressEntity;
import io.spiffy.email.repository.EmailAddressRepository;

public class EmailAddressService extends Service<EmailAddressEntity, EmailAddressRepository> {

    private final SecurityClient securityClient;

    @Inject
    public EmailAddressService(final EmailAddressRepository repository, final SecurityClient securityClient) {
        super(repository);
        this.securityClient = securityClient;
    }

    @Transactional
    public EmailAddressEntity get(final Long id) {
        if (id == null) {
            return null;
        }

        final EmailAddressEntity entity = repository.get(id);
        if (entity == null) {
            return entity;
        }

        entity.setAddress(getAddress(entity.getEncryptedAddressId()));
        return entity;
    }

    @Transactional
    public EmailAddressEntity get(final String address) {
        final long encryptedAddressId = getEncryptedAddressId(address);
        return repository.getByEncryptedAddressId(encryptedAddressId);
    }

    @Transactional
    public EmailAddressEntity getByEncryptedAddressId(final long encryptedAddressId) {
        return repository.getByEncryptedAddressId(encryptedAddressId);
    }

    @Transactional
    public EmailAddressEntity post(final String address) {
        final long encryptedAddressId = getEncryptedAddressId(address);
        EmailAddressEntity entity = getByEncryptedAddressId(encryptedAddressId);

        if (entity == null) {
            entity = new EmailAddressEntity(encryptedAddressId);
        } else {
            // TODO: no archiving
        }

        // TODO: change additional attributes

        repository.saveOrUpdate(entity);

        return entity;
    }

    protected void validateAddress(final String address) {
        ValidationUtil.validateEmailAddress("EmailAddressEntity.address", address);
    }

    private long getEncryptedAddressId(final String address) {
        validateAddress(address);
        final String sanitizedAddress = address.toLowerCase();
        return securityClient.encryptString(sanitizedAddress);
    }

    private String getAddress(final long encryptedAddressId) {
        return securityClient.decryptString(encryptedAddressId);
    }
}