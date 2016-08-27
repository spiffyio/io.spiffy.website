package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.stream.input.PostActionInput;
import io.spiffy.common.api.stream.output.PostActionOutput;

public class PostActionCall extends SpiffyCall<PostActionInput, PostActionOutput> {

    @Inject
    public PostActionCall(final WebTarget target) {
        super(PostActionOutput.class, target.path("stream/postaction"));
    }
}