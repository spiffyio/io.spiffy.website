package io.spiffy.source.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.util.ValidationUtil;
import io.spiffy.source.entity.UrlEntity;
import io.spiffy.source.repository.UrlRepository;

public class UrlService extends Service<UrlEntity, UrlRepository> {

    @Inject
    public UrlService(final UrlRepository repository) {
        super(repository);
    }

    @Transactional
    public UrlEntity get(final String url) {
        return repository.get(url);
    }

    @Transactional
    public UrlEntity post(final String url, final String domain, final String entityId, final String entityOwner) {
        validateDomain(domain);

        UrlEntity entity = get(url);
        if (entity == null) {
            entity = new UrlEntity(url, domain, entityId, entityOwner);
        }

        repository.saveOrUpdate(entity);

        return entity;

    }

    protected void validateDomain(final String domain) {
        ValidationUtil.validateLength("UrlEntity.domain", domain, UrlEntity.MIN_DOMAIN_LENGTH, UrlEntity.MAX_DOMAIN_LENGTH);
    }
}