package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Client;

import org.springframework.web.bind.annotation.RequestMethod;

public class FacebookAuthenticationCall extends AuthenticationCall {

    @Inject
    public FacebookAuthenticationCall(final Client client) {
        super(client.target("https://graph.facebook.com").path("v2.8/oauth/access_token"), RequestMethod.GET);
    }
}
