package io.spiffy.website.controller;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.spiffy.common.Controller;
import io.spiffy.common.dto.Context;
import io.spiffy.website.manager.WebsiteSQSManager;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DebugController extends Controller {

    private final WebsiteSQSManager sqsManager;

    @ResponseBody
    @RequestMapping("/load")
    public String load(final Context context, final @RequestParam long accountId, final @RequestParam String address)
            throws InterruptedException {
        sqsManager.pushSource(accountId, address);
        return "success";
    }
}
