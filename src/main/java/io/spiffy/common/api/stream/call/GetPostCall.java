package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.input.GetInput;
import io.spiffy.common.api.stream.output.GetPostOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class GetPostCall extends SpiffyCall<GetInput, GetPostOutput> {

    private static final String PATH = "stream/getpost";

    @Inject
    public GetPostCall(final WebTarget target, final MemcachedClient client) {
        super(GetPostOutput.class, target.path(PATH), new APICacheManager<GetInput, GetPostOutput>(client, PATH) {
        });
    }
}