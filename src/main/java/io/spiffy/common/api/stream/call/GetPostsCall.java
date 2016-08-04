package io.spiffy.common.api.stream.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.stream.input.GetPostsInput;
import io.spiffy.common.api.stream.output.GetPostsOutput;

public class GetPostsCall extends Call<GetPostsInput, GetPostsOutput> {

    @Inject
    public GetPostsCall(final WebTarget target) {
        super(GetPostsOutput.class, target.path("stream/getposts"));
    }
}