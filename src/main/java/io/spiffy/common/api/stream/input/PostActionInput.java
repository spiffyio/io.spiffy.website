package io.spiffy.common.api.stream.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostActionInput {
    public enum Action {
        DELETE, REPORT, LIKE, DISLIKE, FAVORITE, SHARE, LINK, DOWNLOAD
    }

    private Long postId;
    private Long accountId;
    private Action action;
}
