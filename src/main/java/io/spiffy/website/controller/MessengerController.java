package io.spiffy.website.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.UIDUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.MessagesResponse;
import io.spiffy.website.response.ThreadsResponse;

@RequestMapping("/messages")
public class MessengerController extends Controller {

    @AccessControl
    @RequestMapping
    public ModelAndView messenger(final Context context) {
        return mav("messages", context);
    }

    @AccessControl
    @RequestMapping("/{thread}")
    public ModelAndView thread(final Context context, final @PathVariable String thread) {
        return mav("messages", context);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/{thread}", produces = MediaType.APPLICATION_JSON)
    public AjaxResponse messages(final Context context, final @PathVariable String thread) {
        final List<MessagesResponse.Message> messages = new ArrayList<>();
        if ("cjsmile".equalsIgnoreCase(thread)) {
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "yo bro!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "right",
                    "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "yo bro!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "yo bro!"));
        } else if ("maj".equalsIgnoreCase(thread)) {
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DgHpJP-Cg.jpg", "yo yo yo!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "right",
                    "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "yo yo yo!"));
            messages.add(new MessagesResponse.Message(UIDUtil.generateIdempotentId(), "left",
                    "//cdn-beta.spiffy.io/media/DgHpJP-Cg.jpg", "yo yo yo!"));
        }

        return new MessagesResponse(messages);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping("/threads")
    public AjaxResponse threads(final Context context) {
        final List<ThreadsResponse.Thread> threads = new ArrayList<>();
        threads.add(new ThreadsResponse.Thread("cjsmile", "//cdn-beta.spiffy.io/media/DlXRpf-Cg.jpg", "Yesterday", "yo bro!"));
        threads.add(new ThreadsResponse.Thread("maj", "//cdn-beta.spiffy.io/media/DgHpJP-Cg.jpg", "5 days ago",
                "hello how are you today :)"));

        return new ThreadsResponse(threads);
    }
}
