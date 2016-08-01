package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.security.input.MatchesStringInput;
import io.spiffy.common.api.security.output.MatchesOutput;
import io.spiffy.security.service.HashedStringService;

@RequestMapping("/api/security/matcheshashedstring")
public class MatchesHashedStringAPI extends API<MatchesStringInput, MatchesOutput, HashedStringService> {

    @Inject
    public MatchesHashedStringAPI(final HashedStringService service) {
        super(MatchesStringInput.class, service);
    }

    protected MatchesOutput api(final MatchesStringInput input) {
        final boolean matches = service.matches(input.getId(), input.getPlainString());
        return new MatchesOutput(matches);
    }
}
