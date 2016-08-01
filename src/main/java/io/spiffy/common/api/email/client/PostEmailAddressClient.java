package io.spiffy.common.api.email.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.email.input.PostEmailAddressInput;
import io.spiffy.common.api.email.output.PostEmailAddressOutput;

public class PostEmailAddressClient extends Client<PostEmailAddressInput, PostEmailAddressOutput> {

    @Inject
    public PostEmailAddressClient(final WebTarget target) {
        super(PostEmailAddressOutput.class, target.path("email/postaddress"));
    }
}