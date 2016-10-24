package io.spiffy.common.api.stream.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostPostInput extends APIInput {
    private String idempotentId;
    private Long accountId;
    private Long mediaId;
    private String description;
}
