package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.output.BooleanOutput;
import io.spiffy.common.api.stream.input.FollowInput;

public class FollowCall extends SpiffyCall<FollowInput, BooleanOutput> {

    @Inject
    public FollowCall(final WebTarget target) {
        super(BooleanOutput.class, target.path("stream/follow"));
    }
}