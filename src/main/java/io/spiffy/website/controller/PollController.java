package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.api.notification.client.NotificationClient;
import io.spiffy.common.dto.Context;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.response.LongpollResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class PollController extends Controller {

    private final NotificationClient notificationClient;

    @ResponseBody
    @AccessControl
    @RequestMapping("/longpoll")
    public LongpollResponse longpoll(final Context context) throws InterruptedException {
        final String value = context.getIfNoneMatch();

        final LongpollResponse poll = new LongpollResponse();
        updatePoll(context, poll);

        if (StringUtils.equalsIgnoreCase(value, Integer.toString(poll.hashCode()))) {
            synchronized (poll) {
                poll.wait(15000);
            }
            updatePoll(context, poll);
        }

        context.setResponseETag(poll.hashCode());
        return poll;
    }

    private void updatePoll(final Context context, final LongpollResponse poll) {
        final long accountId = context.getAccountId();
        final long unreadCount = notificationClient.getUnreadCount(accountId);
        poll.setNotifications(unreadCount);
    }
}
