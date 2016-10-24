package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.user.input.GetAccountInput;
import io.spiffy.common.api.user.output.GetAccountOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class GetAccountCall extends SpiffyCall<GetAccountInput, GetAccountOutput> {

    private static final String PATH = "user/getaccount";

    @Inject
    public GetAccountCall(final WebTarget target, final MemcachedClient client) {
        super(GetAccountOutput.class, target.path(PATH), new APICacheManager<GetAccountInput, GetAccountOutput>(client, PATH) {
        });
    }
}