package io.spiffy.website.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
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
import io.spiffy.common.manager.CacheManager;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PollController extends Controller {

    private final CacheManager cacheManager;

    @Data
    @AllArgsConstructor
    public static class Poll {
        private int notifications;
    }

    private static Map<Long, Poll> polls = new HashMap<>();

    @ResponseBody
    @RequestMapping("/set")
    public String set(final Context context, final @RequestParam int value) throws UnknownHostException {
        final Poll poll;
        synchronized (polls) {
            poll = polls.getOrDefault(context.getAccountId(), new Poll(value));
            polls.put(context.getAccountId(), poll);
        }

        synchronized (poll) {
            poll.setNotifications(value);
            poll.notifyAll();
        }

        return "{\"host\":\"" + cacheManager.get("foo") + "\"}";
    }

    @ResponseBody
    @RequestMapping("/longpoll")
    public Poll longpoll(final Context context) throws InterruptedException {
        final String value = context.getIfNoneMatch();

        final Poll poll;
        synchronized (polls) {
            poll = polls.getOrDefault(context.getAccountId(), new Poll(0));
            polls.put(context.getAccountId(), poll);
        }

        if (Integer.toString(poll.hashCode()).equalsIgnoreCase(value)) {
            synchronized (poll) {
                poll.wait();
            }
        }

        context.setResponseETag(poll.hashCode());
        return poll;
    }
}
