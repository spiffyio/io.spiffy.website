package io.spiffy.common.api.media.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.media.input.PostMediaInput;

public class PostMediaCall extends SpiffyCall<PostMediaInput, PostOutput> {

    @Inject
    public PostMediaCall(final WebTarget target) {
        super(PostOutput.class, target.path("media/postmedia"));
    }
}