package io.spiffy.common.api.user.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RecoverAccountOutput extends APIOutput {
    private static final long serialVersionUID = 390720837772380731L;

    public enum Error {
        INVALID_TOKEN, INVALID_PASSWORD, INVALID_EMAIL, UNKNOWN_EMAIL
    }

    private Long accountId;
    private String sessionToken;

    private Error error;

    public RecoverAccountOutput(final long accountId, final String sessionToken) {
        this.accountId = accountId;
        this.sessionToken = sessionToken;
    }

    public RecoverAccountOutput(final Error error) {
        this.error = error;
    }
}
