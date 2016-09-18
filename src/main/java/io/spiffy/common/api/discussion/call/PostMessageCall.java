package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.discussion.input.PostMessageInput;
import io.spiffy.common.api.discussion.output.PostMessageOutput;

public class PostMessageCall extends SpiffyCall<PostMessageInput, PostMessageOutput> {

    @Inject
    public PostMessageCall(final WebTarget target) {
        super(PostMessageOutput.class, target.path("discussion/postmessage"));
    }
}