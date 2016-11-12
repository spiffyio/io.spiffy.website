package io.spiffy.website.oauth;

import lombok.Data;

@Data
public class AuthenticationInput {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String code;
}
