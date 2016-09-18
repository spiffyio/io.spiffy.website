package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.discussion.input.GetThreadsInput;
import io.spiffy.common.api.discussion.output.GetThreadsOutput;

public class GetThreadsCall extends SpiffyCall<GetThreadsInput, GetThreadsOutput> {

    @Inject
    public GetThreadsCall(final WebTarget target) {
        super(GetThreadsOutput.class, target.path("discussion/getthreads"), null);
    }
}