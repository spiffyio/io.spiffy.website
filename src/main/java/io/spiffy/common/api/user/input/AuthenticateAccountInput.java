package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;
import io.spiffy.common.api.user.dto.Credentials;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthenticateAccountInput extends APIInput {
    private Credentials credentials;
    private String sessionId;
    private String fingerprint;
    private String userAgent;
    private String ipAddress;
}