package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;

@RequestMapping("/error")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ErrorController extends Controller {

    private final MediaClient mediaClient;

    @RequestMapping("/**")
    public ModelAndView embed() {
        return new ModelAndView("embed");
    }
}