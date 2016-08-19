package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.user.input.AuthenticateSessionInput;
import io.spiffy.common.api.user.output.AuthenticateSessionOutput;

public class AuthenticateSessionCall extends SpiffyCall<AuthenticateSessionInput, AuthenticateSessionOutput> {

    @Inject
    public AuthenticateSessionCall(final WebTarget target) {
        super(AuthenticateSessionOutput.class, target.path("user/authenticatesession"), null);
    }
}