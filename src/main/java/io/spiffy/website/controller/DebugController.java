package io.spiffy.website.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.common.dto.EntityType;
import io.spiffy.common.util.JsonUtil;
import io.spiffy.discussion.service.ParticipantService;
import io.spiffy.media.service.ContentService;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class DebugController extends Controller {

    private final ContentService contentService;
    private final ParticipantService participantService;

    @ResponseBody
    @RequestMapping("/reprocess")
    public String reprocess(final Context context, final @RequestParam String id) {
        contentService.process(Long.parseLong(id));
        return "success";
    }

    @ResponseBody
    @RequestMapping("/participants")
    public String participants(final Context context) {
        return JsonUtil.serialize(participantService.getByAccount(EntityType.POST, context.getAccountId()));
    }

    @Data
    @AllArgsConstructor
    public static class Poll {
        private String value;
    }

    private static Map<Long, Poll> polls = new HashMap<>();

    @RequestMapping("/debug")
    public ModelAndView debug(final Context context) {
        return mav("debug", context);
    }

    @ResponseBody
    @RequestMapping("/set")
    public String set(final Context context, final @RequestParam String value) {
        final Poll poll;
        synchronized (polls) {
            poll = polls.getOrDefault(context.getAccountId(), new Poll(value));
            polls.put(context.getAccountId(), poll);
        }

        synchronized (poll) {
            poll.setValue(value);
            poll.notifyAll();
        }

        return "{\"success\":true}";
    }

    @ResponseBody
    @RequestMapping("/longpoll")
    public String longpoll(final Context context, final @RequestParam String value) throws InterruptedException {
        final Poll poll;
        synchronized (polls) {
            poll = polls.getOrDefault(context.getAccountId(), new Poll(value));
            polls.put(context.getAccountId(), poll);
        }

        if (poll.getValue().equalsIgnoreCase(value)) {
            synchronized (poll) {
                poll.wait();
            }
        }

        if (poll.getValue().equalsIgnoreCase("error")) {
            context.setResponseStatus(HttpStatus.BAD_REQUEST);
            return "{\"error\":\"balls\"}";
        }

        return "{\"value\":\"" + poll.getValue() + "\"}";
    }
}
