package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.user.input.RegisterAccountInput;
import io.spiffy.common.api.user.output.RegisterAccountOutput;

public class RegisterAccountCall extends SpiffyCall<RegisterAccountInput, RegisterAccountOutput> {

    @Inject
    public RegisterAccountCall(final WebTarget target) {
        super(RegisterAccountOutput.class, target.path("user/registeraccount"));
    }
}