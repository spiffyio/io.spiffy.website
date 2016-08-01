package io.spiffy.security.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.ValidateOutput;
import io.spiffy.common.api.security.input.ValidateStringInput;
import io.spiffy.security.service.HashedStringService;

@RequestMapping("/api/security/validatestring")
public class ValidateStringAPI extends API<ValidateStringInput, ValidateOutput, HashedStringService> {

    @Inject
    public ValidateStringAPI(final HashedStringService service) {
        super(ValidateStringInput.class, service);
    }

    protected ValidateOutput api(final ValidateStringInput input) {
        final boolean matches = service.matches(input.getId(), input.getPlainString());
        return new ValidateOutput(matches);
    }
}
