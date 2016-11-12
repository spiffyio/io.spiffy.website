package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.springframework.web.bind.annotation.RequestMethod;

public class GoogleAuthenticationCall extends AuthenticationCall {

    @Inject
    public GoogleAuthenticationCall(final Client client) {
        super(client.target("https://www.googleapis.com").path("oauth2/v4/token"), RequestMethod.POST);
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final AuthenticationInput input) {
        final WebTarget webTarget = target.queryParam("grant_type", "authorization_code");
        return super.getBuilder(webTarget, input);
    }
}
