package io.spiffy.security.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.util.SecurityUtil;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.security.entity.EncryptedStringEntity;
import io.spiffy.security.repository.EncryptedStringRepository;

public class EncryptedStringService extends Service<EncryptedStringEntity, EncryptedStringRepository> {

    public static final int MIN_PLAIN_TEXT_LENGTH = 1;
    public static final int MAX_PLAIN_TEXT_LENGTH = 256;

    @Inject
    public EncryptedStringService(final EncryptedStringRepository repository) {
        super(repository);
    }

    @Transactional
    public boolean matches(final long id, final String plainString) {
        final EncryptedStringEntity entity = get(id);
        if (entity == null) {
            return false;
        }

        return entity.matches(plainString);
    }

    @Transactional
    public EncryptedStringEntity get(final Long id) {
        if (id == null) {
            return null;
        }
        return repository.get(id);
    }

    @Transactional
    public EncryptedStringEntity get(final String encrypted) {
        return repository.get(encrypted);
    }

    @Transactional
    public EncryptedStringEntity post(final String plainString) {
        validatePlainString(plainString);

        final String encrypted = SecurityUtil.encrypt(plainString);
        validateEncrypted(encrypted);

        EncryptedStringEntity entity = get(encrypted);

        if (entity == null) {
            entity = new EncryptedStringEntity(encrypted);
        } else {
            // TODO: no archiving
        }

        // TODO: change additional attributes

        repository.saveOrUpdate(entity);

        return entity;
    }

    protected void validatePlainString(final String plainString) {
        ValidationUtil.validateLength("EncryptedStringEntity.plainString", plainString, MIN_PLAIN_TEXT_LENGTH,
                MAX_PLAIN_TEXT_LENGTH);
    }

    protected void validateEncrypted(final String encrypted) {
        ValidationUtil.validateLength("EncryptedStringEntity.encrypted", encrypted, EncryptedStringEntity.MIN_ENCRYPTED_LENGTH,
                EncryptedStringEntity.MAX_ENCRYPTED_LENGTH);
    }
}