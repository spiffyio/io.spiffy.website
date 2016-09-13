package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.media.service.ContentService;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class DebugController extends Controller {

    private final ContentService contentService;

    @ResponseBody
    @RequestMapping("/reprocess")
    public String home(final Context context, final @RequestParam String id) {
        contentService.process(Long.parseLong(id));
        return "success";
    }
}
