package io.spiffy.common.api.stream.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetPostsInput extends APIInput {
    public enum Type {
        FOLLOWER, FOLLOWEE
    }

    private Long accountId;
    private Type type;
    private Long first;
    private Integer maxResults;
    private Boolean includeFirst;
}
