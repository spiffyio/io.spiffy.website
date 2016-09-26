package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.website.cache.Poll;
import io.spiffy.website.cache.PollCache;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PollController extends Controller {

    private final PollCache pollCache;
    private static Map<Long, Poll> polls = new HashMap<>();

    @ResponseBody
    @RequestMapping("/set")
    public Poll set(final Context context, final @RequestParam int value) throws UnknownHostException {
        final Poll poll;
        synchronized (polls) {
            poll = polls.getOrDefault(context.getAccountId(), new Poll());
            poll.setNotifications(value);
        }

        synchronized (poll) {
            poll.notifyAll();
        }

        return poll;
    }

    @ResponseBody
    @RequestMapping("/longpoll")
    public Poll longpoll(final Context context) throws InterruptedException {
        final String value = context.getIfNoneMatch();

        final Poll poll;
        synchronized (polls) {
            poll = polls.getOrDefault(context.getAccountId(), new Poll());
            polls.put(context.getAccountId(), poll);
        }

        if (Integer.toString(poll.hashCode()).equalsIgnoreCase(value)) {
            synchronized (poll) {
                poll.wait(300000);
            }
        }

        context.setResponseETag(poll.hashCode());
        return poll;
    }
}
