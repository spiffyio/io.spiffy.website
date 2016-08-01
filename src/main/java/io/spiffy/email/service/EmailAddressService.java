package io.spiffy.email.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.security.client.EncryptStringClient;
import io.spiffy.common.api.security.client.GetEncryptedStringClient;
import io.spiffy.common.api.security.input.PostStringInput;
import io.spiffy.common.api.security.output.GetStringOutput;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.email.entity.EmailAddressEntity;
import io.spiffy.email.repository.EmailAddressRepository;

public class EmailAddressService extends Service<EmailAddressEntity, EmailAddressRepository> {

    private final EncryptStringClient encryptClient;
    private final GetEncryptedStringClient getEncryptClient;

    @Inject
    public EmailAddressService(final EmailAddressRepository repository, final EncryptStringClient encryptClient,
            final GetEncryptedStringClient getEncryptClient) {
        super(repository);
        this.encryptClient = encryptClient;
        this.getEncryptClient = getEncryptClient;
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
        validateAddress(address);

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
        final PostOutput output = encryptClient.call(new PostStringInput(address));
        return output.getId();
    }

    private String getAddress(final long encryptedAddressId) {
        final GetStringOutput output = getEncryptClient.call(new GetInput(encryptedAddressId));
        return output.getPlainString();
    }
}