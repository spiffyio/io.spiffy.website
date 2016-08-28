package io.spiffy.common.api.media.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.media.dto.MediaType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMediaInput {
    private Long accountId;
    private String idempotentId;
    private MediaType type;
    private byte[] value;
}
