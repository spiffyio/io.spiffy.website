package io.spiffy.website.google;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaOutput {
    private Boolean success;
    private String hostname;
    private Date challengeTs;

    @JsonProperty("error-codes")
    private String[] errorCodes;
}
