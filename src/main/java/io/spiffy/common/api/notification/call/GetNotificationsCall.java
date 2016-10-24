package io.spiffy.common.api.notification.call;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import io.spiffy.common.SpiffyCall;
import io.spiffy.common.api.notification.input.GetNotificationsInput;
import io.spiffy.common.api.notification.output.GetNotificationsOutput;

public class GetNotificationsCall extends SpiffyCall<GetNotificationsInput, GetNotificationsOutput> {

    @Inject
    public GetNotificationsCall(final WebTarget target) {
        super(GetNotificationsOutput.class, target.path("notification/getnotifications"));
    }
}