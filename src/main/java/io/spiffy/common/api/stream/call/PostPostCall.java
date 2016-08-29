package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.stream.input.PostPostInput;
import io.spiffy.common.api.stream.output.PostPostOutput;

public class PostPostCall extends SpiffyCall<PostPostInput, PostPostOutput> {

    @Inject
    public PostPostCall(final WebTarget target) {
        super(PostPostOutput.class, target.path("stream/postpost"));
    }
}