package io.spiffy.common.api.source.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.source.input.PostUrlInput;

public class PostUrlCall extends Call<PostUrlInput, PostOutput> {

    @Inject
    public PostUrlCall(final WebTarget target) {
        super(PostOutput.class, target.path("source/posturl"));
    }
}