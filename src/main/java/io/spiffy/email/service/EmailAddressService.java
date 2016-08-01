package io.spiffy.email.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.email.entity.EmailAddressEntity;
import io.spiffy.email.repository.EmailAddressRepository;

public class EmailAddressService extends Service<EmailAddressEntity, EmailAddressRepository> {

    @Inject
    public EmailAddressService(final EmailAddressRepository repository) {
        super(repository);
    }

    @Transactional
    public EmailAddressEntity get(final Long id) {
        if (id == null) {
            return null;
        }
        return repository.get(id);
    }

    @Transactional
    public EmailAddressEntity get(final String address) {
        return repository.get(address);
    }

    @Transactional
    public EmailAddressEntity post(final String address) {
        validateAddress(address);

        EmailAddressEntity entity = get(address);

        if (entity == null) {
            entity = new EmailAddressEntity(address);
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
}