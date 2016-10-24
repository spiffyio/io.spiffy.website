package io.spiffy.common.api.discussion.input;

import lombok.*;

import java.util.Set;

import io.spiffy.common.api.discussion.dto.ThreadDTO;
import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateThreadInput extends APIInput {
    private ThreadDTO thread;
    private Long accountId;
    private Set<String> participants;
}
