package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.api.user.input.SendVerifyEmailInput;

public class SendVerifyEmailCall extends SpiffyCall<SendVerifyEmailInput, PostOutput> {

    @Inject
    public SendVerifyEmailCall(final WebTarget target) {
        super(PostOutput.class, target.path("user/sendverifyemail"));
    }
}