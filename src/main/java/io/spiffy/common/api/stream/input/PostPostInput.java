package io.spiffy.common.api.stream.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPostInput {
    private String idempotentId;
    private Long accountId;
    private Long mediaId;
    private String description;
}
