package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.security.input.GetTokenizedStringInput;
import io.spiffy.common.api.security.output.GetStringOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class GetTokenizedStringCall extends SpiffyCall<GetTokenizedStringInput, GetStringOutput> {

    private static final String PATH = "security/gettokenizedstring";

    @Inject
    public GetTokenizedStringCall(final WebTarget target, final MemcachedClient client) {
        super(GetStringOutput.class, target.path(PATH),
                new APICacheManager<GetTokenizedStringInput, GetStringOutput>(client, PATH) {
                });
    }
}