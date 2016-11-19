package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.springframework.web.bind.annotation.RequestMethod;

public class AmazonAuthenticationCall extends AuthenticationCall {

    @Inject
    public AmazonAuthenticationCall(final Client client) {
        super(client.target("https://api.amazon.com").path("auth/o2/token"), RequestMethod.POST);
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final AuthenticationInput input) {
        final WebTarget webTarget = target.queryParam("grant_type", "authorization_code");
        return super.getBuilder(webTarget, input);
    }
}
