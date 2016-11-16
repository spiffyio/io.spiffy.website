package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.security.input.TokenizeStringInput;
import io.spiffy.common.api.security.output.TokenizeStringOutput;
import io.spiffy.security.service.TokenizedStringService;

@RequestMapping("/api/security/tokenizestring")
public class TokenizeStringAPI extends API<TokenizeStringInput, TokenizeStringOutput, TokenizedStringService> {

    @Inject
    public TokenizeStringAPI(final TokenizedStringService service) {
        super(TokenizeStringInput.class, service);
    }

    protected TokenizeStringOutput api(final TokenizeStringInput input) {
        final String token = service.post(input.getPlainString());
        return new TokenizeStringOutput(token);
    }
}
