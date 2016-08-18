package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.website.response.AjaxResponse;
import io.spiffy.website.response.PostsResponse;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeController extends Controller {

    private static final String AFTER_KEY = "after";
    private static final String POSTS_KEY = "posts";

    private final StreamClient streamClient;

    @RequestMapping({ "/", "/stream" })
    public ModelAndView home(final Context context, final @RequestParam(required = false) String start) {
        final List<Post> posts = streamClient.getPosts(start == null ? null : ObfuscateUtil.unobfuscate(start), 24);
        context.addAttribute(AFTER_KEY, posts.get(posts.size() - 1).getPostId());
        context.addAttribute(POSTS_KEY, posts);

        return mav("home", context);
    }

    @RequestMapping("/stream/{post}")
    public ModelAndView post(final Context context, final @PathVariable String post) {
        return mav("post", context);
    }

    @ResponseBody
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public AjaxResponse posts(final Context context, final @RequestParam(required = false) String after,
            final @RequestParam(defaultValue = "24") int quantity) {
        final List<Post> posts = streamClient.getPosts(after == null ? null : ObfuscateUtil.unobfuscate(after), quantity);
        return new PostsResponse(posts, posts.get(posts.size() - 1).getPostId());
    }
}
