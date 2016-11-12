package io.spiffy.website.oauth;

import javax.inject.Inject;
import javax.ws.rs.client.Client;

public class GoogleInformationCall extends InformationCall {

    @Inject
    public GoogleInformationCall(final Client client) {
        super(client.target("https://www.googleapis.com").path("userinfo/v2/me"));
    }
}
