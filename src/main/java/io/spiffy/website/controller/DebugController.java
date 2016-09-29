package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.website.cache.Poll;
import io.spiffy.website.cache.PollCache;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class DebugController extends Controller {

    private final PollCache pollCache;

    @ResponseBody
    @RequestMapping("/debug/poll")
    public Poll longpoll(final Context context, final @RequestParam Long key,
            final @RequestParam(required = false) Long notifications, final @RequestParam(required = false) Integer etag)
                    throws InterruptedException {
        final Poll poll;
        if (notifications != null) {
            poll = pollCache.get(key, () -> newPoll(0));
            poll.setNotifications(notifications);
            pollCache.put(key, poll);
        } else if (etag != null) {
            poll = pollCache.get(key, () -> newPoll(0), etag);
        } else {
            poll = pollCache.get(key, () -> newPoll(0));
        }

        System.out.println(poll.hashCode());

        return poll;
    }

    private Poll newPoll(final long notifications) {
        final Poll poll = new Poll();
        poll.setNotifications(notifications);
        return poll;
    }
}
