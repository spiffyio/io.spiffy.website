package io.spiffy.authentication.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.authentication.service.HashedStringService;
import io.spiffy.common.API;
import io.spiffy.common.api.ValidateOutput;
import io.spiffy.common.api.authentication.input.ValidateStringInput;

@RequestMapping("/api/authentication/validatestring")
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
