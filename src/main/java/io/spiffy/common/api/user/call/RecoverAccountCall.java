package io.spiffy.common.api.user.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.user.input.RecoverAccountInput;
import io.spiffy.common.api.user.output.RecoverAccountOutput;

public class RecoverAccountCall extends SpiffyCall<RecoverAccountInput, RecoverAccountOutput> {

    @Inject
    public RecoverAccountCall(final WebTarget target) {
        super(RecoverAccountOutput.class, target.path("user/recoveraccount"), null);
    }
}