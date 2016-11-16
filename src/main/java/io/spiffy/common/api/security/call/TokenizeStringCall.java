package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.security.input.TokenizeStringInput;
import io.spiffy.common.api.security.output.TokenizeStringOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class TokenizeStringCall extends SpiffyCall<TokenizeStringInput, TokenizeStringOutput> {

    private static final String PATH = "security/tokenizestring";

    @Inject
    public TokenizeStringCall(final WebTarget target, final MemcachedClient client) {
        super(TokenizeStringOutput.class, target.path(PATH),
                new APICacheManager<TokenizeStringInput, TokenizeStringOutput>(client, PATH) {
                });
    }
}