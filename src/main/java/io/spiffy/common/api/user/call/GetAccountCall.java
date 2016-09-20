package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.user.input.GetAccountInput;
import io.spiffy.common.api.user.output.GetAccountOutput;

public class GetAccountCall extends SpiffyCall<GetAccountInput, GetAccountOutput> {

    @Inject
    public GetAccountCall(final WebTarget target) {
        super(GetAccountOutput.class, target.path("user/getaccount"), null);
    }
}