package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.user.input.GetSessionsInput;
import io.spiffy.common.api.user.output.GetSessionsOutput;

public class GetSessionsCall extends SpiffyCall<GetSessionsInput, GetSessionsOutput> {

    @Inject
    public GetSessionsCall(final WebTarget target) {
        super(GetSessionsOutput.class, target.path("user/getsessions"), null);
    }
}