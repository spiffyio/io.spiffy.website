package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.stream.output.GetPostOutput;

public class GetPostCall extends SpiffyCall<GetInput, GetPostOutput> {

    @Inject
    public GetPostCall(final WebTarget target) {
        super(GetPostOutput.class, target.path("stream/getpost"));
    }
}