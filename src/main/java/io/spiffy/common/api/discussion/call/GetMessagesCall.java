package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.discussion.input.GetMessagesInput;
import io.spiffy.common.api.discussion.output.GetMessagesOutput;

public class GetMessagesCall extends SpiffyCall<GetMessagesInput, GetMessagesOutput> {

    @Inject
    public GetMessagesCall(final WebTarget target) {
        super(GetMessagesOutput.class, target.path("discussion/getmessages"), null);
    }
}