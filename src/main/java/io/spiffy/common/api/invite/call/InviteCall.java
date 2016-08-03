package io.spiffy.common.api.invite.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.invite.input.InviteInput;

public class InviteCall extends Call<InviteInput, PostOutput> {

    @Inject
    public InviteCall(final WebTarget target) {
        super(PostOutput.class, target.path("invite"));
    }
}