package io.spiffy.website.oauth;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class AuthenticationInput {
    @JsonProperty("grant_type")
    private final String grantType = "authorization_code";

    @JsonProperty("client_id")
    private final String clientId;

    @JsonProperty("client_secret")
    private final String clientSecret;

    @JsonProperty("redirect_uri")
    private final String redirectUri;

    @JsonProperty("code")
    private final String code;
}
