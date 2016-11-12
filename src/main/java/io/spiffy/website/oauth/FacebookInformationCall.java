package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

public class FacebookInformationCall extends InformationCall {

    @Inject
    public FacebookInformationCall(final Client client) {
        super(client.target("https://graph.facebook.com").path("v2.8/me"));
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final InformationInput input) {
        final WebTarget webTarget = target.queryParam("fields", "id,email");
        return super.getBuilder(webTarget, input);
    }
}
