package io.spiffy.common.api.stream.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostPostOutput extends APIOutput {
    private static final long serialVersionUID = -7035672374342052678L;

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
