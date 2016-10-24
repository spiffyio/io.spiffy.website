package io.spiffy.common.api.stream.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostActionOutput extends APIOutput {
    private static final long serialVersionUID = 2883714441177370584L;

    public enum Error {
        INSUFFICIENT_PRIVILEGES, INVALID_POST
    }

    private Boolean success;
    private Error error;

    public PostActionOutput(final boolean success) {
        this.success = success;
    }

    public PostActionOutput(final Error error) {
        success = false;
        this.error = error;
    }
}
