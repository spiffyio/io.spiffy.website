package io.spiffy.security.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.util.UIDUtil;
import io.spiffy.security.entity.EncryptedStringEntity;
import io.spiffy.security.entity.TokenizedStringEntity;
import io.spiffy.security.repository.TokenizedStringRepository;

public class TokenizedStringService extends Service<TokenizedStringEntity, TokenizedStringRepository> {

    private final EncryptedStringService encryptedService;

    @Inject
    public TokenizedStringService(final TokenizedStringRepository repository, final EncryptedStringService encryptedService) {
        super(repository);
        this.encryptedService = encryptedService;
    }

    @Transactional
    public String get(final String token) {
        final TokenizedStringEntity entity = repository.get(token);
        if (entity == null) {
            return null;
        }

        final EncryptedStringEntity encrypted = entity.getEncrypted();
        if (encrypted == null) {
            return null;
        }

        return encrypted.getPlainString();
    }

    @Transactional
    public String post(final String plainString) {
        final String token = UIDUtil.generateIdempotentId();
        final EncryptedStringEntity encrypted = encryptedService.post(plainString);

        final TokenizedStringEntity entity = new TokenizedStringEntity(token, encrypted);
        repository.saveOrUpdate(entity);

        return token;
    }
}
