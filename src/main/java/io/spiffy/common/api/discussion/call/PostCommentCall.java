package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.discussion.input.PostCommentInput;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class PostCommentCall extends SpiffyCall<PostCommentInput, PostOutput> {

    private static final String PATH = "discussion/postcomment";

    @Inject
    public PostCommentCall(final WebTarget target, final MemcachedClient client) {
        super(PostOutput.class, target.path(PATH), new APICacheManager<PostCommentInput, PostOutput>(client, PATH) {
        });
    }
}