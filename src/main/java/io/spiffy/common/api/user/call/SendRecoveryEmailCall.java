package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.SendRecoveryEmailInput;

public class SendRecoveryEmailCall extends SpiffyCall<SendRecoveryEmailInput, PostOutput> {

    @Inject
    public SendRecoveryEmailCall(final WebTarget target) {
        super(PostOutput.class, target.path("user/sendrecoveryemail"));
    }
}