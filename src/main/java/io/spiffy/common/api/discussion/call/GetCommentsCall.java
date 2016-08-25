package io.spiffy.common.api.discussion.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.discussion.input.GetCommentsInput;
import io.spiffy.common.api.discussion.output.GetCommentsOutput;

public class GetCommentsCall extends SpiffyCall<GetCommentsInput, GetCommentsOutput> {

    @Inject
    public GetCommentsCall(final WebTarget target) {
        super(GetCommentsOutput.class, target.path("discussion/getcomments"), null);
    }
}