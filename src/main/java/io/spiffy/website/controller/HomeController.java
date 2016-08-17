package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeController extends Controller {

    private static final String POSTS_KEY = "posts";

    private final StreamClient streamClient;

    @RequestMapping("/")
    public ModelAndView home(final Context context) {
        final List<Post> posts = streamClient.getPosts(null, 24);
        context.addAttribute(POSTS_KEY, posts);

        return mav("home", context);
    }
}
