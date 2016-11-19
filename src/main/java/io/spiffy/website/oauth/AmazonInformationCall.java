package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

public class AmazonInformationCall extends InformationCall {

    @Inject
    public AmazonInformationCall(final Client client) {
        super(client.target("https://api.amazon.com").path("user/profile"));
    }

    @Override
    public Invocation.Builder getBuilder(final WebTarget target, final InformationInput input) {
        final WebTarget webTarget = target.property("Authorization", "bearer " + input.getAccessToken());
        return super.getBuilder(webTarget, input);
    }
}
