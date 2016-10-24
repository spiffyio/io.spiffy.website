package io.spiffy.common.api.discussion.input;

import lombok.*;

import io.spiffy.common.api.discussion.dto.ThreadDTO;
import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostCommentInput extends APIInput {
    private ThreadDTO thread;
    private Long accountId;
    private String idempotentId;
    private String comment;
}
