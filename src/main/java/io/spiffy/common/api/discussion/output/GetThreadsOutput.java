package io.spiffy.common.api.discussion.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.discussion.dto.MessengerThread;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetThreadsOutput {
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
