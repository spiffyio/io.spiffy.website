package io.spiffy.common.api.media.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.media.input.PostMediaInput;
import io.spiffy.common.api.media.output.PostMediaOutput;

public class PostMediaCall extends SpiffyCall<PostMediaInput, PostMediaOutput> {

    @Inject
    public PostMediaCall(final WebTarget target) {
        super(PostMediaOutput.class, target.path("media/postmedia"));
    }
}