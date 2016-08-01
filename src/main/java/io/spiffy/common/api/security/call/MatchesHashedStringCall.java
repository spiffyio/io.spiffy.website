package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Call;
import io.spiffy.common.api.security.input.MatchesStringInput;
import io.spiffy.common.api.security.output.MatchesStringOutput;

public class MatchesHashedStringCall extends Call<MatchesStringInput, MatchesStringOutput> {

    @Inject
    public MatchesHashedStringCall(final WebTarget target) {
        super(MatchesStringOutput.class, target.path("security/matcheshashedstring"));
    }
}