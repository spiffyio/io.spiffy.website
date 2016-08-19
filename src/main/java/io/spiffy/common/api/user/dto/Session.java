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
    private Long id;
    private String os;
    private String browser;
    private Date lastActivity;
}
