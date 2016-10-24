package io.spiffy.common.api.media.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;
import io.spiffy.common.api.media.dto.MediaType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostMediaInput extends APIInput {
    private Long accountId;
    private String idempotentId;
    private MediaType type;
    private byte[] value;
    private byte[] thumbnail;
}
