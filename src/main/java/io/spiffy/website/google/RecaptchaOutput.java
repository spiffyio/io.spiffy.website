package io.spiffy.website.google;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaOutput {
    private Boolean success;
    private String hostname;
}
