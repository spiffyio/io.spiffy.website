package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.security.input.PostStringInput;

public class EncryptStringCall extends SpiffyCall<PostStringInput, PostOutput> {

    @Inject
    public EncryptStringCall(final WebTarget target) {
        super(PostOutput.class, target.path("security/encryptstring"));
    }
}