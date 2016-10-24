package io.spiffy.common.api.media.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;
import io.spiffy.common.api.media.dto.ContentType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetAccountMediaInput extends APIInput {
    private Long accountId;
    private ContentType type;
    private Long first;
    private Integer maxResults;
    private Boolean includeFirst;
}
