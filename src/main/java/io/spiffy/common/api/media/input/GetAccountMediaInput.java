package io.spiffy.common.api.media.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.media.dto.ContentType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountMediaInput {
    private Long accountId;
    private ContentType type;
    private Long first;
    private Integer maxResults;
    private Boolean includeFirst;
}
