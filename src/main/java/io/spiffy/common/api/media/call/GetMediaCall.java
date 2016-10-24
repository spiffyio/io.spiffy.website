package io.spiffy.common.api.media.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.input.GetInput;
import io.spiffy.common.api.media.output.GetMediaOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class GetMediaCall extends SpiffyCall<GetInput, GetMediaOutput> {

    private static final String PATH = "media/getmedia";

    @Inject
    public GetMediaCall(final WebTarget target, final MemcachedClient client) {
        super(GetMediaOutput.class, target.path(PATH), new APICacheManager<GetInput, GetMediaOutput>(client, PATH) {
        });
    }
}