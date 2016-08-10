package io.spiffy.common.api.user.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.user.dto.Session;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSessionsOutput {
    private Long accountId;
    private List<Session> sessions;
}
