package io.spiffy.common.api.email.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.email.input.PostEmailAddressInput;

public class PostEmailAddressClient extends Client<PostEmailAddressInput, PostOutput> {

    @Inject
    public PostEmailAddressClient(final WebTarget target) {
        super(PostOutput.class, target.path("email/postaddress"));
    }
}