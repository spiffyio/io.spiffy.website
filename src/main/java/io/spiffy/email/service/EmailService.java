package io.spiffy.email.service;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.api.email.dto.EmailProperties;
import io.spiffy.common.api.email.dto.EmailType;
import io.spiffy.common.util.DateUtil;
import io.spiffy.common.util.JsonUtil;
import io.spiffy.email.entity.EmailAddressEntity;
import io.spiffy.email.entity.EmailEntity;
import io.spiffy.email.manager.EmailManager;
import io.spiffy.email.repository.EmailRepository;

public class EmailService extends Service<EmailEntity, EmailRepository> {

    private final EmailAddressService emailAddressService;
    private final EmailManager emailManager;

    @Inject
    public EmailService(final EmailRepository repository, final EmailAddressService emailAddressService,
            final EmailManager emailManager) {
        super(repository);
        this.emailAddressService = emailAddressService;
        this.emailManager = emailManager;
    }

    @Transactional
    public EmailEntity get(final String idempotentId) {
        return repository.get(idempotentId);
    }

    @Transactional
    public EmailEntity post(final String idempotentId, final String emailAddress, final EmailType type, final long accountId,
            final EmailProperties properties) {
        EmailEntity entity = get(idempotentId);

        if (entity == null) {
            final EmailAddressEntity address = emailAddressService.post(emailAddress);
            entity = new EmailEntity(idempotentId, address, type, accountId, JsonUtil.serialize(properties));
            entity.setProperties(properties);
        } else {
            entity.setProperties(JsonUtil.deserialize(EmailProperties.class, entity.getPropertiesJson()));
        }

        repository.saveOrUpdate(entity);

        return entity;
    }

    @Transactional
    public EmailEntity send(final String idempotentId, final String emailAddress, final EmailType type, final long accountId,
            final EmailProperties properties) {
        final EmailEntity entity = post(idempotentId, emailAddress, type, accountId, properties);
        if (entity.getSentAt() != null) {
            return entity;
        }

        final Date sentAt = DateUtil.now();
        emailManager.send(type.getTemplate(), type.getSubject(), entity.getEmailAddress().getAddress(), sentAt,
                entity.getProperties());

        entity.setSentAt(sentAt);
        repository.saveOrUpdate(entity);

        return entity;
    }

}
