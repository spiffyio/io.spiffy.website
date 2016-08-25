package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.discussion.input.PostCommentInput;

public class PostCommentCall extends SpiffyCall<PostCommentInput, PostOutput> {

    @Inject
    public PostCommentCall(final WebTarget target) {
        super(PostOutput.class, target.path("discussion/postcomment"));
    }
}