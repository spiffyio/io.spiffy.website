package io.spiffy.common.api.stream.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowInput {
    public enum Action {
        FOLLOW, UNFOLLOW
    }

    private Long followerId;
    private Long followeeId;
    private Action action;
}
