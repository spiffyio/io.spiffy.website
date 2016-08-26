package io.spiffy.common.api.user.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAccountOutput {
    public enum Error {
        INCORRECT_PASSWORD, INVALID_PASSWORD, INVALID_EMAIL, INVALID_USERNAME, EXISTING_EMAIL, EXISTING_USERNAME
    }

    private Long accountId;
    private String sessionToken;

    private Error error;

    public RegisterAccountOutput(final long accountId, final String sessionToken) {
        this.accountId = accountId;
        this.sessionToken = sessionToken;
    }

    public RegisterAccountOutput(final Error error) {
        this.error = error;
    }
}
