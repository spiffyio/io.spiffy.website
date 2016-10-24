package io.spiffy.common.api.stream.input;

import lombok.*;

import io.spiffy.common.api.input.APIInput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FollowInput extends APIInput {
    public enum Action {
        FOLLOW, UNFOLLOW
    }

    private Long followerId;
    private Long followeeId;
    private Action action;
}
