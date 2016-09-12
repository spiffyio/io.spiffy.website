package io.spiffy.notification.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.dto.EntityType;
import io.spiffy.common.util.DateUtil;
import io.spiffy.notification.entity.AlertEntity;
import io.spiffy.notification.repository.AlertRepository;

public class AlertService extends Service<AlertEntity, AlertRepository> {

    @Inject
    public AlertService(final AlertRepository repository) {
        super(repository);
    }

    @Transactional
    public AlertEntity get(final long account, final String idempotentId) {
        return repository.get(account, idempotentId);
    }

    @Transactional
    public AlertEntity post(final long account, final String idempotentId, final EntityType entityType, final String entityId) {
        AlertEntity entity = get(account, idempotentId);
        if (entity == null) {
            entity = new AlertEntity(account, idempotentId, entityType, entityId, DateUtil.now());
        }

        repository.saveOrUpdate(entity);

        return entity;
    }
}
