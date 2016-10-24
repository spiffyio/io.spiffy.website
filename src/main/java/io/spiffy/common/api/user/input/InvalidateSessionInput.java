package io.spiffy.common.api.user.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvalidateSessionInput extends APIInput {
    private String sessionId;
    private String token;

    private Long id;
    private Long accountId;

    private String userAgent;
    private String ipAddress;

    public InvalidateSessionInput(final String sessionId, final String token, final String userAgent, final String ipAddress) {
        this(sessionId, token, null, null, userAgent, ipAddress);
    }

    public InvalidateSessionInput(final Long id, final Long accountId, final String userAgent, final String ipAddress) {
        this(null, null, id, accountId, userAgent, ipAddress);
    }
}
