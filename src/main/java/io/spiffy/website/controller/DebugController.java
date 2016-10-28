package io.spiffy.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;

import io.spiffy.common.Controller;
import io.spiffy.common.config.AppConfig;
import io.spiffy.common.util.DateUtil;

@RequestMapping("/debug")
public class DebugController extends Controller {

    @ResponseBody
    @RequestMapping
    public String embed() {
        return CloudFrontUrlSigner.getSignedURLWithCannedPolicy("https:" + AppConfig.getCdnEndpoint() + "/content/banner.png",
                AppConfig.getCdnKeyPair(), AppConfig.getCdnPrivateKey(), DateUtil.now(5L));
    }
}