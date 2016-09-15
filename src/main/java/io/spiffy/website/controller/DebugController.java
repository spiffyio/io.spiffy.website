package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.common.dto.EntityType;
import io.spiffy.common.util.JsonUtil;
import io.spiffy.discussion.service.ParticipantService;
import io.spiffy.media.service.ContentService;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DebugController extends Controller {

    private final ContentService contentService;
    private final ParticipantService participantService;

    @ResponseBody
    @RequestMapping("/reprocess")
    public String reprocess(final Context context, final @RequestParam String id) {
        contentService.process(Long.parseLong(id));
        return "success";
    }

    @ResponseBody
    @RequestMapping("/participants")
    public String participants(final Context context) {
        return JsonUtil.serialize(participantService.getByAccount(EntityType.POST, context.getAccountId()));
    }
}
