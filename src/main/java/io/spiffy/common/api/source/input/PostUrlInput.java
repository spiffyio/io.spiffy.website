package io.spiffy.common.api.source.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostUrlInput extends APIInput {
    private String url;
    private String domain;
    private String entityId;
    private String entityOwner;
}
