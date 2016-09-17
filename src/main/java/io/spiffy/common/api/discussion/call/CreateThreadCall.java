package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.discussion.input.CreateThreadInput;
import io.spiffy.common.api.discussion.output.CreateThreadOutput;

public class CreateThreadCall extends SpiffyCall<CreateThreadInput, CreateThreadOutput> {

    @Inject
    public CreateThreadCall(final WebTarget target) {
        super(CreateThreadOutput.class, target.path("discussion/createthread"), null);
    }
}