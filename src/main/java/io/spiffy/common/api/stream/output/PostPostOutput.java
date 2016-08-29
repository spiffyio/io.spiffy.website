package io.spiffy.common.api.stream.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPostOutput {
    public enum Error {
        UNKNOWN_ERROR
    }

    private String name;
    private Error error;

    public PostPostOutput(final String name) {
        this.name = name;
    }

    public PostPostOutput(final Error error) {
        this.error = error;
    }
}
