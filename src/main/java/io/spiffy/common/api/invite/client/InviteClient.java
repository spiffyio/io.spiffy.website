package io.spiffy.common.api.invite.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.invite.input.InviteInput;

public class InviteClient extends Client<InviteInput, PostOutput> {

    @Inject
    public InviteClient(final WebTarget target) {
        super(PostOutput.class, target.path("invite"));
    }
}
