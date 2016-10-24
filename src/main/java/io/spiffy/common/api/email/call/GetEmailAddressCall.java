package io.spiffy.common.api.email.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.email.output.GetEmailAddressOutput;
import io.spiffy.common.api.input.GetInput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class GetEmailAddressCall extends SpiffyCall<GetInput, GetEmailAddressOutput> {

    private static final String PATH = "email/getaddress";

    @Inject
    public GetEmailAddressCall(final WebTarget target, final MemcachedClient client) {
        super(GetEmailAddressOutput.class, target.path(PATH),
                new APICacheManager<GetInput, GetEmailAddressOutput>(client, PATH) {
                });
    }
}