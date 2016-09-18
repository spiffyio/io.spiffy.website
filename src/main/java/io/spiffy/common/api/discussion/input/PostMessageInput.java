package io.spiffy.common.api.discussion.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import io.spiffy.common.api.discussion.dto.ThreadDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMessageInput {
    private ThreadDTO thread;
    private Long accountId;
    private Set<String> participants;
    private String idempotentId;
    private String comment;
}
