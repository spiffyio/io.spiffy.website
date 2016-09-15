package io.spiffy.discussion.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import io.spiffy.common.Service;
import io.spiffy.common.dto.EntityType;
import io.spiffy.discussion.entity.ParticipantEntity;
import io.spiffy.discussion.entity.ThreadEntity;
import io.spiffy.discussion.repository.ParticipantRepository;

public class ParticipantService extends Service<ParticipantEntity, ParticipantRepository> {

    @Inject
    public ParticipantService(final ParticipantRepository repository) {
        super(repository);
    }

    @Transactional
    public ParticipantEntity get(final long id) {
        return repository.get(id);
    }

    @Transactional
    public ParticipantEntity get(final ThreadEntity thread, final long accountId) {
        return repository.get(thread, accountId);
    }

    @Transactional
    public List<ParticipantEntity> getByThread(final ThreadEntity thread) {
        return repository.getByThread(thread);
    }

    @Transactional
    public List<ParticipantEntity> getByAccount(final EntityType entityType, final long accountId) {
        return repository.getByAccount(accountId, entityType);
    }

    @Transactional
    public ParticipantEntity post(final ThreadEntity thread, final long accountId) {
        ParticipantEntity entity = get(thread, accountId);
        if (entity == null) {
            entity = new ParticipantEntity(thread, accountId);
        } else {
            // TODO archive
        }

        repository.saveOrUpdate(entity);

        return entity;
    }
}
