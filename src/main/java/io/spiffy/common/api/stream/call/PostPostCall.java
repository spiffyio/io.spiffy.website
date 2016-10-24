package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.stream.input.PostPostInput;
import io.spiffy.common.api.stream.output.PostPostOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class PostPostCall extends SpiffyCall<PostPostInput, PostPostOutput> {

    private static final String PATH = "stream/postpost";

    @Inject
    public PostPostCall(final WebTarget target, final MemcachedClient client) {
        super(PostPostOutput.class, target.path(PATH), new APICacheManager<PostPostInput, PostPostOutput>(client, PATH) {
        });
    }
}