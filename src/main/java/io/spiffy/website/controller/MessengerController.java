package io.spiffy.website.controller;

import java.util.*;

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

    private static final Map<String, String> ICONS;
    private static final String DEFAULT_ICON = "//cdn.spiffy.io/media/DxrwtJ-Cg.jpg";

    static {
        final Map<String, String> icons = new HashMap<>();
        icons.put("john", "//cdn.spiffy.io/media/MPMBQx-Cg.jpg");
        icons.put("johnrich", "//cdn.spiffy.io/media/CVQrJj-Cg.jpg");
        icons.put("maj", "//cdn.spiffy.io/media/MTdfxC-Cg.jpg");
        icons.put("cjsmile", "//cdn.spiffy.io/media/CnPfCX-Cg.jpg");
        icons.put("dadtv1234", "//cdn.spiffy.io/media/GQfhNV-Cg.jpg");

        ICONS = Collections.unmodifiableMap(icons);
    }

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

    @Csrf("message")
    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/{thread}/message", method = RequestMethod.POST)
    public AjaxResponse newMessage(final Context context, final @PathVariable String thread,
            final @RequestParam String message) {
        System.out.println(thread + ": " + message);
        return new SuccessResponse(true);
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
        threads.add(new ThreadsResponse.Thread("maj", ICONS.getOrDefault("maj", DEFAULT_ICON), "5 days ago",
                "hello how are you today :)"));
        threads.add(new ThreadsResponse.Thread("cjsmile", ICONS.getOrDefault("cjsmile", DEFAULT_ICON), "Yesterday", "yo bro!"));
        threads.add(new ThreadsResponse.Thread("pewp", ICONS.getOrDefault("pewp", DEFAULT_ICON), "Today", "pewp"));
        return threads;
    }

    private final List<MessagesResponse.Message> getMessages(final Context context, final String thread) {
        final List<MessagesResponse.Message> messages = new ArrayList<>();
        if ("cjsmile".equalsIgnoreCase(thread)) {
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    ICONS.getOrDefault("cjsmile", DEFAULT_ICON), "yo bro!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "right",
                    ICONS.getOrDefault("john", DEFAULT_ICON), "yo bro!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    ICONS.getOrDefault("cjsmile", DEFAULT_ICON), "yo bro!"));
        } else if ("maj".equalsIgnoreCase(thread)) {
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    ICONS.getOrDefault("maj", DEFAULT_ICON), "yo yo yo!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "right",
                    ICONS.getOrDefault("john", DEFAULT_ICON), "yo yo yo!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    ICONS.getOrDefault("maj", DEFAULT_ICON), "yo yo yo!"));
        }
        return messages;
    }
}
