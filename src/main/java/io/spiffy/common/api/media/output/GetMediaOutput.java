package io.spiffy.common.api.media.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.media.dto.Content;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMediaOutput {
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
}
