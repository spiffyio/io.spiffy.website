package io.spiffy.common.api.email.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.email.input.SendEmailInput;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.manager.APICacheManager;

import net.spy.memcached.MemcachedClient;

public class SendEmailCall extends SpiffyCall<SendEmailInput, PostOutput> {

    private static final String PATH = "email/sendemail";

    @Inject
    public SendEmailCall(final WebTarget target, final MemcachedClient client) {
        super(PostOutput.class, target.path(PATH), new APICacheManager<SendEmailInput, PostOutput>(client, PATH) {
        });
    }
}