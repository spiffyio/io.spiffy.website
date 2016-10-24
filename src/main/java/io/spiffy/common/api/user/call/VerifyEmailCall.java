package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.api.user.input.VerifyEmailInput;

public class VerifyEmailCall extends SpiffyCall<VerifyEmailInput, PostOutput> {

    @Inject
    public VerifyEmailCall(final WebTarget target) {
        super(PostOutput.class, target.path("user/verifyemail"));
    }
}