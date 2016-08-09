package io.spiffy.website.google;

import lombok.Data;

@Data
public class RecaptchaInput {
    private final String response;
    private final String remoteip;
}
