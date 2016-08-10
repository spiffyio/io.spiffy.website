package io.spiffy.common.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Date authenticatedAt;
    private String userAgent;
    private String ipAddress;
}
