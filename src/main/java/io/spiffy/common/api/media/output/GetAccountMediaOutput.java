package io.spiffy.common.api.media.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.media.dto.Content;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountMediaOutput {
    public enum Error {
    }

    private List<Content> contents;
    private Error error;

    public GetAccountMediaOutput(final List<Content> contents) {
        this.contents = contents;
    }

    public GetAccountMediaOutput(final Error error) {
        this.error = error;
    }
}
