package io.spiffy.common.api.discussion.output;

import lombok.*;

import io.spiffy.common.api.discussion.dto.MessengerThread;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreateThreadOutput extends APIOutput {
    private static final long serialVersionUID = 5668752377243716795L;

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
