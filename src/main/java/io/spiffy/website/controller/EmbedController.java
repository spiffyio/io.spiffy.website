package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.dto.Context;

@RequestMapping("/embed")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmbedController extends Controller {

    private final MediaClient mediaClient;

    @ResponseBody
    @RequestMapping("/**")
    public String embed(final Context context) {
        return context.getRequestUri();
    }
}