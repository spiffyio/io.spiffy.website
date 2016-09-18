package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

import io.spiffy.common.Controller;
import io.spiffy.common.api.discussion.client.DiscussionClient;
import io.spiffy.common.api.discussion.dto.MessengerMessage;
import io.spiffy.common.api.discussion.dto.MessengerThread;
import io.spiffy.common.api.discussion.output.CreateThreadOutput;
import io.spiffy.common.api.discussion.output.GetMessagesOutput;
import io.spiffy.common.api.discussion.output.GetThreadsOutput;
import io.spiffy.common.api.discussion.output.PostMessageOutput;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.UIDUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.response.*;

@RequestMapping("/messages")
@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class MessengerController extends Controller {

    private final DiscussionClient discussionClient;

    @AccessControl
    @RequestMapping
    public ModelAndView messenger(final Context context) {
        final List<MessengerThread> threads = getThreads(context);
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
        final Set<String> set = new HashSet<>();
        for (final String participant : participants.split("[,; ]")) {
            if (StringUtils.isBlank(participant)) {
                continue;
            }
            set.add(participant);
        }

        final CreateThreadOutput output = discussionClient.createThread(context.getAccountId(), set);
        if (CreateThreadOutput.Error.TOO_FEW_NAMES.equals(output.getError())) {
            return new BadRequestResponse("participants", "one other name required", null);
        } else if (CreateThreadOutput.Error.TOO_MANY_NAMES.equals(output.getError())) {
            return new BadRequestResponse("participants", "nine names max", null);
        } else if (CreateThreadOutput.Error.UNKNOWN_NAME.equals(output.getError())) {
            return new BadRequestResponse("participants", "unknown name", null);
        }

        return new ThreadResponse(output.getThread());
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
            final @RequestParam String idempotentId, final @RequestParam String message) {
        final Set<String> set = new HashSet<>();
        for (final String participant : thread.split("[,; ]")) {
            if (StringUtils.isBlank(participant)) {
                continue;
            }
            set.add(participant);
        }

        final PostMessageOutput output = discussionClient.postMessage(context.getAccountId(), set, idempotentId, message);
        return new MessageResponse(output.getMessage(), UIDUtil.generateIdempotentId());
    }

    private ModelAndView thread(final Context context, final String thread) {
        context.addAttribute("activeThread", thread);
        if (!"new".equalsIgnoreCase(thread)) {
            context.addAttribute("messages", Lists.reverse(getMessages(context, thread, null)));
        }
        return mav("messages", context);
    }

    @ResponseBody
    @AccessControl
    @RequestMapping(value = "/{thread}", produces = MediaType.APPLICATION_JSON)
    public AjaxResponse messages(final Context context, final @PathVariable String thread,
            final @RequestParam(required = false) String after) {
        return new MessagesResponse(getMessages(context, thread, after));
    }

    @ResponseBody
    @AccessControl
    @RequestMapping("/threads")
    public AjaxResponse threads(final Context context) {
        return new ThreadsResponse(getThreads(context));
    }

    private final List<MessengerThread> getThreads(final Context context) {
        final GetThreadsOutput output = discussionClient.getThreads(context.getAccountId());
        return output.getThreads();
    }

    private final List<MessengerMessage> getMessages(final Context context, final String thread, final String after) {
        final Set<String> set = new HashSet<>();
        for (final String participant : thread.split("[,; ]")) {
            if (StringUtils.isBlank(participant)) {
                continue;
            }
            set.add(participant);
        }

        final GetMessagesOutput output = discussionClient.getMessages(context.getAccountId(), set, after);
        return output.getMessages();
    }
}
