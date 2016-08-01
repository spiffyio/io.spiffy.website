package io.spiffy.common.api.security.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.ValidateOutput;
import io.spiffy.common.api.security.input.ValidateStringInput;

public class ValidateStringClient extends Client<ValidateStringInput, ValidateOutput> {

    @Inject
    public ValidateStringClient(final WebTarget target) {
        super(ValidateOutput.class, target.path("security/validatestring"));
    }
}