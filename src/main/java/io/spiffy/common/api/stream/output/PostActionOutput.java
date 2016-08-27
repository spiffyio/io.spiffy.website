package io.spiffy.common.api.stream.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostActionOutput {
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
