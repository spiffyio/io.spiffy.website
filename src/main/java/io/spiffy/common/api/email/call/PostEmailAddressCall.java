package io.spiffy.common.api.email.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.email.input.PostEmailAddressInput;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class PostEmailAddressCall extends SpiffyCall<PostEmailAddressInput, PostOutput> {

    private static final String PATH = "email/postaddress";

    @Inject
    public PostEmailAddressCall(final WebTarget target, final MemcachedClient client) {
        super(PostOutput.class, target.path(PATH), new APICacheManager<PostEmailAddressInput, PostOutput>(client, PATH) {
        });
    }
}