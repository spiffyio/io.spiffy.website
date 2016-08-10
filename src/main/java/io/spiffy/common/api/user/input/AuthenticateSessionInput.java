package io.spiffy.common.api.user.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateSessionInput {
    private String sessionId;
    private String token;
    private String userAgent;
    private String ipAddress;
}
