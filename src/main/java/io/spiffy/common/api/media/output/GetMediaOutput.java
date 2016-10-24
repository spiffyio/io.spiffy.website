package io.spiffy.common.api.media.output;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetMediaOutput extends APIOutput {
    private static final long serialVersionUID = -3081019288649563041L;

    public enum Error {
        UNKNOWN_CONTENT, UNPROCESSED_CONTENT
    }

    private Content content;
    private Error error;

    public GetMediaOutput(final Content content) {
        this.content = content;
    }

    public GetMediaOutput(final Error error) {
        this.error = error;
    }

    @JsonIgnore
    public boolean isCacheable() {
        return !GetMediaOutput.Error.UNPROCESSED_CONTENT.equals(error);
    }
}
