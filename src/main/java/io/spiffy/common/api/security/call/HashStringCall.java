package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.security.input.PostStringInput;

public class HashStringCall extends Call<PostStringInput, PostOutput> {

    @Inject
    public HashStringCall(final WebTarget target) {
        super(PostOutput.class, target.path("security/hashstring"));
    }
}