package io.spiffy.common.api.security.client;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.security.output.GetStringOutput;

public class GetEncryptedStringClient extends Client<GetInput, GetStringOutput> {

    @Inject
    public GetEncryptedStringClient(final WebTarget target) {
        super(GetStringOutput.class, target.path("security/getencryptedstring"));
    }
}