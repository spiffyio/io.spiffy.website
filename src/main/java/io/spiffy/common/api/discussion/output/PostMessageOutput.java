package io.spiffy.common.api.discussion.output;

import lombok.*;

import io.spiffy.common.api.discussion.dto.MessengerMessage;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostMessageOutput extends APIOutput {
    private static final long serialVersionUID = -1967153218470755618L;
    private MessengerMessage message;
}
