package io.spiffy.common.api.media.output;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetAccountMediaOutput extends APIOutput {
    private static final long serialVersionUID = 3472215380445668112L;

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
