package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.RegisterAccountInput;

public class RegisterAccountCall extends Call<RegisterAccountInput, PostOutput> {

    @Inject
    public RegisterAccountCall(final WebTarget target) {
        super(PostOutput.class, target.path("user/registeraccount"));
    }
}