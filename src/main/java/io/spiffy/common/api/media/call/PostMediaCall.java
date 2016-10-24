package io.spiffy.common.api.media.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.media.input.PostMediaInput;
import io.spiffy.common.api.media.output.PostMediaOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class PostMediaCall extends SpiffyCall<PostMediaInput, PostMediaOutput> {

    private static final String PATH = "media/postmedia";

    @Inject
    public PostMediaCall(final WebTarget target, final MemcachedClient client) {
        super(PostMediaOutput.class, target.path(PATH), new APICacheManager<PostMediaInput, PostMediaOutput>(client, PATH) {
        });
    }
}