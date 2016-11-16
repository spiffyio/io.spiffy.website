package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.security.input.GetTokenizedStringInput;
import io.spiffy.common.api.security.output.GetStringOutput;
import io.spiffy.security.service.TokenizedStringService;

@RequestMapping("/api/security/gettokenizedstring")
public class GetTokenizedStringAPI extends API<GetTokenizedStringInput, GetStringOutput, TokenizedStringService> {

    @Inject
    public GetTokenizedStringAPI(final TokenizedStringService service) {
        super(GetTokenizedStringInput.class, service);
    }

    protected GetStringOutput api(final GetTokenizedStringInput input) {
        final String plainString = service.get(input.getToken());
        return new GetStringOutput(plainString);
    }
}
