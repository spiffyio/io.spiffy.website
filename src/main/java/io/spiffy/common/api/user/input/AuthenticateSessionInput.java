package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthenticateSessionInput extends APIInput {
    private String sessionId;
    private String token;
    private String userAgent;
    private String ipAddress;
}
