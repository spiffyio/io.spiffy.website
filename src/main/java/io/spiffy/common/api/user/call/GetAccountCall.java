package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.user.output.GetAccountOutput;

public class GetAccountCall extends SpiffyCall<GetInput, GetAccountOutput> {

    @Inject
    public GetAccountCall(final WebTarget target) {
        super(GetAccountOutput.class, target.path("user/getaccount"));
    }
}