package io.spiffy.common.api.discussion.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.discussion.dto.MessengerThread;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateThreadOutput {
    public enum Error {
        UNKNOWN_NAME, TOO_MANY_NAMES, TOO_FEW_NAMES
    }

    private MessengerThread thread;
    private Error error;

    public CreateThreadOutput(final MessengerThread thread) {
        this.thread = thread;
    }

    public CreateThreadOutput(final Error error) {
        this.error = error;
    }
}
