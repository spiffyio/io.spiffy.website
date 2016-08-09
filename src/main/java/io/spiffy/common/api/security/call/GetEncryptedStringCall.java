package io.spiffy.common.api.security.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.security.output.GetStringOutput;

public class GetEncryptedStringCall extends SpiffyCall<GetInput, GetStringOutput> {

    @Inject
    public GetEncryptedStringCall(final WebTarget target) {
        super(GetStringOutput.class, target.path("security/getencryptedstring"));
    }
}