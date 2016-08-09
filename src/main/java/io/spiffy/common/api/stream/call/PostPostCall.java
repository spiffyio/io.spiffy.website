package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.stream.input.PostPostInput;

public class PostPostCall extends SpiffyCall<PostPostInput, PostOutput> {

    @Inject
    public PostPostCall(final WebTarget target) {
        super(PostOutput.class, target.path("stream/postpost"));
    }
}