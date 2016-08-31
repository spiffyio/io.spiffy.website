package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.discussion.client.DiscussionClient;
import io.spiffy.common.api.discussion.dto.ThreadDTO.EntityType;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.stream.input.PostActionInput;
import io.spiffy.common.api.stream.output.GetPostOutput;
import io.spiffy.common.api.stream.output.PostActionOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.website.annotation.AccessControl;
import io.spiffy.website.annotation.Csrf;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.BadRequestResponse;
import io.spiffy.website.response.PostsResponse;
import io.spiffy.website.response.SuccessResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class HomeController extends Controller {

    private static final String AFTER_KEY = "after";
    private static final String COMMENTS_KEY = "comments";
    private static final String POST_KEY = "post";
    private static final String POSTS_KEY = "posts";
    private static final String UNPROCESSED_KEY = "unprocessed";
    private static final String USER_KEY = "user";

    private final DiscussionClient discussionClient;
    private final StreamClient streamClient;
    private final UserClient userClient;

    @RequestMapping({ "/", "/stream" })
    public ModelAndView home(final Context context, final @RequestParam(required = false) String user,
            final @RequestParam(required = false) String start) {
        final Account account = userClient.getAccount(new Account(user));
        final List<Post> posts = streamClient.getPosts(account == null ? null : account.getId(),
                start == null ? null : ObfuscateUtil.unobfuscate(start), 10, true);
        if (CollectionUtils.isEmpty(posts)) {
            return mav("stream", context);
        }

        if (posts.size() >= 5) {
            posts.add(3, Post.ad());
        }
        if (posts.size() >= 10) {
            posts.add(8, Post.ad());
        }

        context.addAttribute(USER_KEY, user);
        context.addAttribute(AFTER_KEY, posts.get(posts.size() - 1).getPostId());
        context.addAttribute(POSTS_KEY, posts);

        return mav("stream", context);
    }

    @RequestMapping("/{user}/stream")
    public ModelAndView userStream(final Context context, final @PathVariable String user,
            final @RequestParam(required = false) String start) {
        final Account account = userClient.getAccount(new Account(user));
        return home(context, account != null ? account.getUsername() : null, start);
    }

    @RequestMapping("/stream/{postId}")
    public ModelAndView post(final Context context, final @PathVariable String postId) {
        final long post = ObfuscateUtil.unobfuscate(postId);

        final GetPostOutput output = streamClient.getPost(post);
        context.addAttribute(POST_KEY, output.getPost());

        if (GetPostOutput.Error.UNPROCESSED_MEDIA.equals(output.getError())) {
            context.addAttribute(UNPROCESSED_KEY, postId);
        }

        context.addAttribute(COMMENTS_KEY, discussionClient.getComments(EntityType.POST, "" + post, null, 24));

        return mav("post", context);
    }

    @ResponseBody
    @Csrf("action")
    @AccessControl(returnUri = "/stream")
    @RequestMapping(value = "/stream/{postId}/action", method = RequestMethod.POST)
    public AjaxResponse action(final Context context, final @PathVariable String postId,
            final @RequestParam("action") String actionName) {
        final long post = ObfuscateUtil.unobfuscate(postId);

        final PostActionInput.Action action;
        try {
            action = PostActionInput.Action.valueOf(actionName.toUpperCase());
        } catch (final IllegalArgumentException e) {
            return new BadRequestResponse("action", "invalid action");
        }

        final PostActionOutput output = streamClient.postAction(post, context.getAccountId(), action);

        if (PostActionOutput.Error.INSUFFICIENT_PRIVILEGES.equals(output.getError())) {
            return new BadRequestResponse("post", "insufficient privileges");
        } else if (PostActionOutput.Error.INVALID_POST.equals(output.getError())) {
            return new BadRequestResponse("post", "invalid post");
        }

        return new SuccessResponse(Boolean.TRUE.equals(output.getSuccess()));
    }

    @ResponseBody
    @Csrf("comment")
    @AccessControl(returnUri = "/stream")
    @RequestMapping(value = "/stream/{postId}/comment", method = RequestMethod.POST)
    public AjaxResponse comment(final Context context, final @PathVariable String postId, final @RequestParam String comment,
            final @RequestParam String idempotentId) {
        final long post = ObfuscateUtil.unobfuscate(postId);

        final boolean success = discussionClient.postComment(EntityType.POST, "" + post, context.getAccountId(), idempotentId,
                comment);

        return new SuccessResponse(success);
    }

    @ResponseBody
    @Csrf("posts")
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public AjaxResponse posts(final Context context, final @RequestParam(required = false) String user,
            final @RequestParam(required = false) String after, final @RequestParam(defaultValue = "10") int quantity) {
        final Account account = userClient.getAccount(new Account(user));
        final List<Post> posts = streamClient.getPosts(account == null ? null : account.getId(),
                after == null ? null : ObfuscateUtil.unobfuscate(after), quantity, false);
        if (CollectionUtils.isEmpty(posts)) {
            return new PostsResponse(null, null);
        }

        if (posts.size() >= 5) {
            posts.add(3, Post.ad());
        }
        if (posts.size() >= 10) {
            posts.add(8, Post.ad());
        }

        return new PostsResponse(posts, posts.get(posts.size() - 1).getPostId());
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView postsGet(final Context context, final @RequestParam(required = false) String user,
            final @RequestParam(required = false) String after, final @RequestParam(defaultValue = "24") int quantity) {

        final StringBuilder builder = new StringBuilder();
        if (user != null) {
            builder.append(user + "/");
        }
        builder.append("/stream");

        if (after != null) {
            builder.append("?start=" + after);
        }

        return redirect(builder.toString(), context);
    }
}
