package io.spiffy.common.api.discussion.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.discussion.dto.ThreadDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentInput {
    private ThreadDTO thread;
    private Long accountId;
    private String idempotentId;
    private String comment;
}
