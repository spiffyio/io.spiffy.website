package io.spiffy.invite.service;

import javax.inject.Inject;

import io.spiffy.common.Service;
import io.spiffy.invite.entity.InviteEntity;
import io.spiffy.invite.repository.InviteRepository;

public class InviteService extends Service<InviteEntity, InviteRepository> {

    @Inject
    public InviteService(final InviteRepository repository) {
        super(repository);
    }

    public void post(final String email) {
        final InviteEntity entity = new InviteEntity();
        entity.setEmail(email);
        repository.save(entity);
    }
}
