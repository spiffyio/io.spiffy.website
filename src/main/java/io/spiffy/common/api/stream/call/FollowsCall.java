package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.output.BooleanOutput;
import io.spiffy.common.api.stream.input.FollowsInput;

public class FollowsCall extends SpiffyCall<FollowsInput, BooleanOutput> {

    @Inject
    public FollowsCall(final WebTarget target) {
        super(BooleanOutput.class, target.path("stream/follows"));
    }
}