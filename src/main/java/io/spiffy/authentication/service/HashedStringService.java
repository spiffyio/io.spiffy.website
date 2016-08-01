package io.spiffy.authentication.service;

import static io.spiffy.common.util.SecurityUtil.*;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.authentication.entity.HashedStringEntity;
import io.spiffy.authentication.repository.HashedStringRepository;
import io.spiffy.common.Service;
import io.spiffy.common.util.ValidationUtil;

public class HashedStringService extends Service<HashedStringEntity, HashedStringRepository> {

    public static final int MIN_PLAIN_TEXT_LENGTH = 1;
    public static final int MAX_PLAIN_TEXT_LENGTH = 256;

    @Inject
    public HashedStringService(final HashedStringRepository repository) {
        super(repository);
    }

    @Transactional
    public boolean matches(final long id, final String plainString) {
        final HashedStringEntity entity = get(id);
        if (entity == null) {
            return false;
        }

        return entity.matches(plainString);
    }

    @Transactional
    public HashedStringEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public HashedStringEntity post(final String plainString) {
        validateEncryptedString(plainString);
        final byte[] salt = getSalt();
        final byte[] hash = getHash(plainString, salt);

        final HashedStringEntity encryptedString = new HashedStringEntity(toHex(hash), toHex(salt));

        repository.saveOrUpdate(encryptedString);

        return encryptedString;
    }

    protected void validateEncryptedString(final String plainString) {
        ValidationUtil.validateLength("HashedStringEntity.plainString", plainString, MIN_PLAIN_TEXT_LENGTH,
                MAX_PLAIN_TEXT_LENGTH);
    }

    protected void validateHash(final String hash) {
        ValidationUtil.validateLength("HashedStringEntity.hash", hash, HashedStringEntity.MIN_HASH_LENGTH,
                HashedStringEntity.MAX_HASH_LENGTH);
    }

    protected void validateSelt(final String salt) {
        ValidationUtil.validateLength("HashedStringEntity.salt", salt, HashedStringEntity.MIN_SALT_LENGTH,
                HashedStringEntity.MAX_SALT_LENGTH);
    }
}