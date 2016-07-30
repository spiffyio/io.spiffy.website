package io.spiffy.api.invite;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;

public class InviteClient extends Client<InviteInput, InviteOutput> {

    @Inject
    public InviteClient(final WebTarget target) {
        super(InviteOutput.class, target.path("invite"));
    }
}
