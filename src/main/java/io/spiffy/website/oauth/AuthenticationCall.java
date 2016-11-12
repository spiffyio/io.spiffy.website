package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.springframework.web.bind.annotation.RequestMethod;

import io.spiffy.common.Call;

public abstract class AuthenticationCall extends Call<AuthenticationInput, AuthenticationOutput> {

    @Inject
    public AuthenticationCall(final WebTarget target, final RequestMethod method) {
        super(AuthenticationOutput.class, target, method);
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final AuthenticationInput input) {
        final WebTarget webTarget = target //
                .queryParam("client_id", input.getClientId()) //
                .queryParam("client_secret", input.getClientSecret()) //
                .queryParam("redirect_uri", input.getRedirectUri()) //
                .queryParam("code", input.getCode());

        return super.getBuilder(webTarget, input);
    }
}
