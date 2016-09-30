package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.api.notification.client.NotificationClient;
import io.spiffy.common.dto.Context;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.cache.Poll;
import io.spiffy.website.cache.PollCache;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DebugController extends Controller {

    private final PollCache pollCache;
    private final NotificationClient notificationClient;

    @ResponseBody
    @AccessControl
    @RequestMapping("/debug/poll")
    public Poll longpoll(final Context context, final @RequestParam(required = false) Integer etag)
            throws InterruptedException {
        final Poll poll;
        if (etag != null) {
            poll = pollCache.get(context.getAccountId(), () -> newPoll(context), etag);
        } else {
            poll = pollCache.get(context.getAccountId(), () -> newPoll(context));
        }

        return poll;
    }

    private Poll newPoll(final Context context) {
        final Poll poll = new Poll();
        poll.setNotifications(notificationClient.getUnreadCount(context.getAccountId()));
        return poll;
    }
}
