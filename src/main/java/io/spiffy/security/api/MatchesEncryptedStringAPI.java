package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.security.input.MatchesStringInput;
import io.spiffy.common.api.security.output.MatchesStringOutput;
import io.spiffy.security.service.EncryptedStringService;

@RequestMapping("/api/security/matchesencryptedstring")
public class MatchesEncryptedStringAPI extends API<MatchesStringInput, MatchesStringOutput, EncryptedStringService> {

    @Inject
    public MatchesEncryptedStringAPI(final EncryptedStringService service) {
        super(MatchesStringInput.class, service);
    }

    protected MatchesStringOutput api(final MatchesStringInput input) {
        final boolean matches = service.matches(input.getId(), input.getPlainString());
        return new MatchesStringOutput(matches);
    }
}
