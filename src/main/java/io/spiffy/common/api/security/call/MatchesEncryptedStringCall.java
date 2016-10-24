package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.security.input.MatchesStringInput;
import io.spiffy.common.api.security.output.MatchesStringOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class MatchesEncryptedStringCall extends SpiffyCall<MatchesStringInput, MatchesStringOutput> {

    private static final String PATH = "security/matchesencryptedstring";

    @Inject
    public MatchesEncryptedStringCall(final WebTarget target, final MemcachedClient client) {
        super(MatchesStringOutput.class, target.path(PATH),
                new APICacheManager<MatchesStringInput, MatchesStringOutput>(client, PATH) {
                });
    }
}