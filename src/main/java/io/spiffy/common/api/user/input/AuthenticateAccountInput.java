package io.spiffy.common.api.user.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateAccountInput {
    private String email;
    private String password;
    private String sessionId;
    private String userAgent;
    private String ipAddress;
}