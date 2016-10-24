package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.discussion.input.PostMessageInput;
import io.spiffy.common.api.discussion.output.PostMessageOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class PostMessageCall extends SpiffyCall<PostMessageInput, PostMessageOutput> {

    private static final String PATH = "discussion/postmessage";

    @Inject
    public PostMessageCall(final WebTarget target, final MemcachedClient client) {
        super(PostMessageOutput.class, target.path(PATH),
                new APICacheManager<PostMessageInput, PostMessageOutput>(client, PATH) {
                });
    }
}