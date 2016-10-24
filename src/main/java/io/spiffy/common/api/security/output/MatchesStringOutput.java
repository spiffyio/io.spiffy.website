package io.spiffy.common.api.security.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchesStringOutput extends APIOutput {
    private static final long serialVersionUID = 382314869679832553L;
    private Boolean matches;
}
