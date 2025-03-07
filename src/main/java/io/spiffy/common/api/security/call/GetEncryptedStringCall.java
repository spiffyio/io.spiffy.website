package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.input.GetInput;
import io.spiffy.common.api.security.output.GetStringOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class GetEncryptedStringCall extends SpiffyCall<GetInput, GetStringOutput> {

    private static final String PATH = "security/getencryptedstring";

    @Inject
    public GetEncryptedStringCall(final WebTarget target, final MemcachedClient client) {
        super(GetStringOutput.class, target.path(PATH), new APICacheManager<GetInput, GetStringOutput>(client, PATH) {
        });
    }
}