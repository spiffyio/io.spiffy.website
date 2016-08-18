package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("/")
    public ModelAndView home(final Context context) {
        final List<Post> posts = streamClient.getPosts(null, 24);
        context.addAttribute(AFTER_KEY, posts.get(posts.size() - 1).getPostId());
        context.addAttribute(POSTS_KEY, posts);

        return mav("home", context);
    }

    @ResponseBody
    @RequestMapping("/posts")
    public AjaxResponse posts(final Context context, final @RequestParam(required = false) String after,
            final @RequestParam(defaultValue = "24") int quantity) {
        final List<Post> posts = streamClient.getPosts(after == null ? null : ObfuscateUtil.unobfuscate(after), quantity);
        return new PostsResponse(posts, posts.get(posts.size() - 1).getPostId());
    }
}
