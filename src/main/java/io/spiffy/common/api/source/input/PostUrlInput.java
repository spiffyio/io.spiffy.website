package io.spiffy.common.api.source.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUrlInput {
    private String url;
    private String domain;
    private String entityId;
    private String entityOwner;
}
