package io.spiffy.common.api.media.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.media.input.GetMediaOutput;

public class GetMediaCall extends SpiffyCall<GetInput, GetMediaOutput> {

    @Inject
    public GetMediaCall(final WebTarget target) {
        super(GetMediaOutput.class, target.path("media/gettmedia"));
    }
}