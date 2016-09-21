package io.spiffy.common.api.stream.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsInput {
    public enum Type {
        FOLLOWER, FOLLOWEE
    }

    private Long accountId;
    private Type type;
    private Long first;
    private Integer maxResults;
    private Boolean includeFirst;
}
