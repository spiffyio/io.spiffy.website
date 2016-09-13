package io.spiffy.common.api.notification.client;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.notification.call.GetNotificationsCall;
import io.spiffy.common.api.notification.call.GetUnreadCountCall;
import io.spiffy.common.api.notification.dto.Notification;
import io.spiffy.common.api.notification.input.GetNotificationsInput;
import io.spiffy.common.api.notification.input.GetUnreadCountInput;
import io.spiffy.common.api.notification.output.GetNotificationsOutput;
import io.spiffy.common.api.notification.output.GetUnreadCountOutput;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class NotificationClient extends Client {

    private final GetNotificationsCall getNotificationsCall;
    private final GetUnreadCountCall getUnreadCountCall;

    public List<Notification> getNoficiations(final long accountId) {
        final GetNotificationsInput input = new GetNotificationsInput(accountId);
        final GetNotificationsOutput output = getNotificationsCall.call(input);
        if (output == null) {
            return new ArrayList<Notification>();
        }
        if (output.getNotifications() == null) {
            return new ArrayList<Notification>();
        }
        return output.getNotifications();
    }

    public long getUnreadCount(final long accountId) {
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
