package io.spiffy.common.api.security.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.security.input.PostStringInput;

public class EncryptStringClient extends Client<PostStringInput, PostOutput> {

    @Inject
    public EncryptStringClient(final WebTarget target) {
        super(PostOutput.class, target.path("security/encryptstring"));
    }
}