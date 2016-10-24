package io.spiffy.common.api.discussion.output;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.discussion.dto.MessengerThread;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetThreadsOutput extends APIOutput {
    private static final long serialVersionUID = -1054560311147778426L;

    public enum Error {

    }

    private List<MessengerThread> threads;
    private Error error;

    public GetThreadsOutput(final List<MessengerThread> threads) {
        this.threads = threads;
    }

    public GetThreadsOutput(final Error error) {
        this.error = error;
    }
}
