package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.api.security.input.PostStringInput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class EncryptStringCall extends SpiffyCall<PostStringInput, PostOutput> {

    private static final String PATH = "security/encryptstring";

    @Inject
    public EncryptStringCall(final WebTarget target, final MemcachedClient client) {
        super(PostOutput.class, target.path(PATH), new APICacheManager<PostStringInput, PostOutput>(client, PATH) {
        });
    }
}