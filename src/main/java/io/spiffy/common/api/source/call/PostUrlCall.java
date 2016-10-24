package io.spiffy.common.api.source.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.api.source.input.PostUrlInput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class PostUrlCall extends SpiffyCall<PostUrlInput, PostOutput> {

    private static final String PATH = "source/posturl";

    @Inject
    public PostUrlCall(final WebTarget target, final MemcachedClient client) {
        super(PostOutput.class, target.path(PATH), new APICacheManager<PostUrlInput, PostOutput>(client, PATH) {
        });
    }
}