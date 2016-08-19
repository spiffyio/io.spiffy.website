package io.spiffy.common.api.user.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvalidateSessionInput {
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
