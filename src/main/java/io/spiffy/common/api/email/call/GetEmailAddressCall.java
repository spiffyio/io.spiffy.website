package io.spiffy.common.api.email.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.email.output.GetEmailAddressOutput;

public class GetEmailAddressCall extends SpiffyCall<GetInput, GetEmailAddressOutput> {

    @Inject
    public GetEmailAddressCall(final WebTarget target) {
        super(GetEmailAddressOutput.class, target.path("email/getaddress"));
    }
}