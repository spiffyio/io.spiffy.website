package io.spiffy.common.api.discussion.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetThreadsInput extends APIInput {
    private long accountId;
}
