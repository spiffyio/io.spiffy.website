package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.user.input.AuthenticateAccountInput;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;

public class AuthenticateAccountCall extends Call<AuthenticateAccountInput, AuthenticateAccountOutput> {

    @Inject
    public AuthenticateAccountCall(final WebTarget target) {
        super(AuthenticateAccountOutput.class, target.path("user/authenticateaccount"));
    }
}