package io.spiffy.common.api.authentication.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.authentication.input.PostStringInput;

public class PostStringClient extends Client<PostStringInput, PostOutput> {

    @Inject
    public PostStringClient(final WebTarget target) {
        super(PostOutput.class, target.path("authentication/poststring"));
    }
}