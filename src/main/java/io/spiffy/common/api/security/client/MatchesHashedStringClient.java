package io.spiffy.common.api.security.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.security.input.MatchesStringInput;
import io.spiffy.common.api.security.output.MatchesOutput;

public class MatchesHashedStringClient extends Client<MatchesStringInput, MatchesOutput> {

    @Inject
    public MatchesHashedStringClient(final WebTarget target) {
        super(MatchesOutput.class, target.path("security/matcheshashedstring"));
    }
}