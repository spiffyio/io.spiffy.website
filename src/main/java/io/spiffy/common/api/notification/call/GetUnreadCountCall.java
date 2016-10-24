package io.spiffy.common.api.notification.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.notification.input.GetUnreadCountInput;
import io.spiffy.common.api.notification.output.GetUnreadCountOutput;

public class GetUnreadCountCall extends SpiffyCall<GetUnreadCountInput, GetUnreadCountOutput> {

    @Inject
    public GetUnreadCountCall(final WebTarget target) {
        super(GetUnreadCountOutput.class, target.path("notification/getunreadcount"));
    }
}