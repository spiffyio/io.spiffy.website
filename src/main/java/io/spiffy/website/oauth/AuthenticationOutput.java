package io.spiffy.website.oauth;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationOutput {

    @JsonProperty("access_token")
    private String accessToken;
}
