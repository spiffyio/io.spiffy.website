package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RedirectController extends Controller {

    private final MediaClient mediaClient;

    @RequestMapping("/halo/**")
    public ModelAndView home(final Context context) {
        return redirect("/", context);
    }

    @RequestMapping("/images/**")
    public ModelAndView images(final Context context) {
        return redirect("https:" + AppConfig.getCdnEndpoint() + context.getRequestUri(), context);
    }
}