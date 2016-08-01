package io.spiffy.common.api.email.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.email.output.GetEmailAddressOutput;

public class GetEmailAddressClient extends Client<GetInput, GetEmailAddressOutput> {

    @Inject
    public GetEmailAddressClient(final WebTarget target) {
        super(GetEmailAddressOutput.class, target.path("email/getaddress"));
    }
}