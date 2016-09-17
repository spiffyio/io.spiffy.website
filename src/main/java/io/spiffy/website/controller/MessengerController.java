package io.spiffy.website.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.UIDUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.MessagesResponse;
import io.spiffy.website.response.SuccessResponse;
import io.spiffy.website.response.ThreadsResponse;

@RequestMapping("/messages")
public class MessengerController extends Controller {

    @AccessControl
    @RequestMapping
    public ModelAndView messenger(final Context context) {
        final List<ThreadsResponse.Thread> threads = getThreads(context);
        context.addAttribute("threads", threads);
        final String thread = threads.isEmpty() ? "new" : threads.get(0).getId();
        return thread(context, thread);
    }

    @AccessControl
    @RequestMapping("/new")
    public ModelAndView newThread(final Context context) {
        context.addAttribute("threads", getThreads(context));
        return thread(context, "new");
    }

    @Csrf("new")
    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public AjaxResponse newThread(final Context context, final @RequestParam String participants) {
        System.out.println(participants);
        return new SuccessResponse(true);
    }

    @AccessControl
    @RequestMapping("/{thread}")
    public ModelAndView messenger(final Context context, final @PathVariable String thread) {
        context.addAttribute("threads", getThreads(context));
        return thread(context, thread);
    }

    private ModelAndView thread(final Context context, final String thread) {
        context.addAttribute("activeThread", thread);
        if (!"new".equalsIgnoreCase(thread)) {
            context.addAttribute("messages", getMessages(context, thread));
        }
        return mav("messages", context);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/{thread}", produces = MediaType.APPLICATION_JSON)
    public AjaxResponse messages(final Context context, final @PathVariable String thread) {
        return new MessagesResponse(getMessages(context, thread));
    }

    @ResponseBody
    @AccessControl
    @RequestMapping("/threads")
    public AjaxResponse threads(final Context context) {
        return new ThreadsResponse(getThreads(context));
    }

    private final List<ThreadsResponse.Thread> getThreads(final Context context) {
        final List<ThreadsResponse.Thread> threads = new ArrayList<>();
        threads.add(new ThreadsResponse.Thread("maj", "//cdn-beta.spiffy.io/media/DgHpJP-Cg.jpg", "5 days ago",
                "hello how are you today :)"));
        threads.add(new ThreadsResponse.Thread("cjsmile", "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "Yesterday", "yo bro!"));
        return threads;
    }

    private final List<MessagesResponse.Message> getMessages(final Context context, final String thread) {
        final List<MessagesResponse.Message> messages = new ArrayList<>();
        if ("cjsmile".equalsIgnoreCase(thread)) {
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "yo bro!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "right",
                    "//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg", "yo bro!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "yo bro!"));
        } else if ("maj".equalsIgnoreCase(thread)) {
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DgHpJP-Cg.jpg", "yo yo yo!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "right",
                    "//cdn-beta.spiffy.io/media/FXmVHZ-Cg.jpg", "yo yo yo!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DgHpJP-Cg.jpg", "yo yo yo!"));
        }
        return messages;
    }
}
