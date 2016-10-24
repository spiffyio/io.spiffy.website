package io.spiffy.common.api.user.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegisterAccountOutput extends APIOutput {
    private static final long serialVersionUID = -7662861995296913317L;

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
