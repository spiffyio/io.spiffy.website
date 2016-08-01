package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.PostAccountInput;

public class PostAccountCall extends Call<PostAccountInput, PostOutput> {

    @Inject
    public PostAccountCall(final WebTarget target) {
        super(PostOutput.class, target.path("user/postaccount"));
    }
}