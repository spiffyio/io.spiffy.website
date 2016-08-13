package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.stream.client.StreamClient;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class HomeController extends Controller {

    private static final String POSTS_KEY = "posts";

    private final MediaClient mediaClient;
    private final StreamClient streamClient;

    @RequestMapping("/")
    public ModelAndView home(final Context context) {
        final List<Post> posts = streamClient.getPosts(1000098L, 24);
        context.addAttribute(POSTS_KEY, posts);

        final List<String> urls = new ArrayList<>();
        posts.forEach(p -> urls.add(mediaClient.getMedia(p.getMediaId())));
        context.addAttribute("urls", urls);

        return mav("home", context);
    }
}
