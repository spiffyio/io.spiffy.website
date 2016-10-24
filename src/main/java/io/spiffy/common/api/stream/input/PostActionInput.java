package io.spiffy.common.api.stream.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostActionInput extends APIInput {
    public enum Action {
        DELETE, REPORT, LIKE, DISLIKE, FAVORITE, SHARE, LINK, DOWNLOAD
    }

    private Long postId;
    private Long accountId;
    private Action action;
}
