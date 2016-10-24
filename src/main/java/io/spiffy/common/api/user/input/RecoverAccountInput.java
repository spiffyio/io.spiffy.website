package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RecoverAccountInput extends APIInput {
    private String email;
    private String token;
    private String password;
    private String sessionId;
    private String fingerprint;
    private String userAgent;
    private String ipAddress;
}