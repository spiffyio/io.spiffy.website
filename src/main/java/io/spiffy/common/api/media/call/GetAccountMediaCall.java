package io.spiffy.common.api.media.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.media.input.GetAccountMediaInput;
import io.spiffy.common.api.media.output.GetAccountMediaOutput;

public class GetAccountMediaCall extends SpiffyCall<GetAccountMediaInput, GetAccountMediaOutput> {

    @Inject
    public GetAccountMediaCall(final WebTarget target) {
        super(GetAccountMediaOutput.class, target.path("media/getaccountmedia"));
    }
}
