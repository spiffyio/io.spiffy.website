package io.spiffy.common.api.stream.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.stream.dto.Post;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostOutput {
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

}
