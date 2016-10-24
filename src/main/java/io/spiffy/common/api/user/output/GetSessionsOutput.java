package io.spiffy.common.api.user.output;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.output.APIOutput;
import io.spiffy.common.api.user.dto.Session;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetSessionsOutput extends APIOutput {
    private static final long serialVersionUID = -2633409363561765090L;
    private Long accountId;
    private List<Session> sessions;
}
