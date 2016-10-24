package io.spiffy.common.api.stream.output;

import lombok.*;

import io.spiffy.common.api.output.APIOutput;
import io.spiffy.common.api.stream.dto.Post;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetPostOutput extends APIOutput {
    private static final long serialVersionUID = -204040365132959586L;

    public enum Error {
        UNKNOWN_POST, UNPROCESSED_MEDIA
    }

    private Post post;
    private Error error;

    public GetPostOutput(final Post post) {
        this.post = post;
    }

    public GetPostOutput(final Error error) {
        this.error = error;
    }

    public boolean isCahceable() {
        return !GetPostOutput.Error.UNPROCESSED_MEDIA.equals(error);
    }
}
