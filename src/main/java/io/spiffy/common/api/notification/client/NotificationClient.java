package io.spiffy.common.api.notification.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.notification.call.GetUnreadCountCall;
import io.spiffy.common.api.notification.input.GetUnreadCountInput;
import io.spiffy.common.api.notification.output.GetUnreadCountOutput;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class NotificationClient extends Client {

    private final GetUnreadCountCall getUnreadCountCall;

    public long getUnreadCountCall(final long accountId) {
        final GetUnreadCountInput input = new GetUnreadCountInput(accountId);
        final GetUnreadCountOutput output = getUnreadCountCall.call(input);
        if (output == null) {
            return 0L;
        }
        if (output.getCount() == null) {
            return 0L;
        }
        return output.getCount();
    }
}
