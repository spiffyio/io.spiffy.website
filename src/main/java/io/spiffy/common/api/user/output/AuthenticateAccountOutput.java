package io.spiffy.common.api.user.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthenticateAccountOutput extends APIOutput {
    private static final long serialVersionUID = 8037483277099587425L;

    public enum Error {
        INVALID_PASSWORD, INVALID_EMAIL, UNKNOWN_CREDENTIALS, UNKNOWN_EMAIL
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
