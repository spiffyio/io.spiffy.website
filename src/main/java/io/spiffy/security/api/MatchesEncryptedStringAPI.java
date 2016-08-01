package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.security.input.MatchesStringInput;
import io.spiffy.common.api.security.output.MatchesOutput;
import io.spiffy.security.service.EncryptedStringService;

@RequestMapping("/api/security/matchesencryptedstring")
public class MatchesEncryptedStringAPI extends API<MatchesStringInput, MatchesOutput, EncryptedStringService> {

    @Inject
    public MatchesEncryptedStringAPI(final EncryptedStringService service) {
        super(MatchesStringInput.class, service);
    }

    protected MatchesOutput api(final MatchesStringInput input) {
        final boolean matches = service.matches(input.getId(), input.getPlainString());
        return new MatchesOutput(matches);
    }
}
