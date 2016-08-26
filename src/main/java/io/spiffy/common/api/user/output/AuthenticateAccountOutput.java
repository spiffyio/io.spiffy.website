package io.spiffy.common.api.user.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateAccountOutput {
    public enum Error {
        INVALID_PASSWORD, INVALID_EMAIL, UNKNOWN_EMAIL
    }

    private Long accountId;
    private String sessionToken;

    private Error error;

    public AuthenticateAccountOutput(final long accountId, final String sessionToken) {
        this.accountId = accountId;
        this.sessionToken = sessionToken;
    }

    public AuthenticateAccountOutput(final Error error) {
        this.error = error;
    }
}
