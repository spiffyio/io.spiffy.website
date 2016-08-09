package io.spiffy.common.api.email.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.email.input.PostEmailAddressInput;

public class PostEmailAddressCall extends SpiffyCall<PostEmailAddressInput, PostOutput> {

    @Inject
    public PostEmailAddressCall(final WebTarget target) {
        super(PostOutput.class, target.path("email/postaddress"));
    }
}