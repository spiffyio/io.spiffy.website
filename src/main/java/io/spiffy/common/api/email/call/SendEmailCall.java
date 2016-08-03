package io.spiffy.common.api.email.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.email.input.SendEmailInput;

public class SendEmailCall extends Call<SendEmailInput, PostOutput> {

    @Inject
    public SendEmailCall(final WebTarget target) {
        super(PostOutput.class, target.path("email/sendemail"));
    }
}