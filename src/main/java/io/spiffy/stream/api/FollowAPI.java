package io.spiffy.stream.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.BooleanOutput;
import io.spiffy.common.api.stream.input.FollowInput;
import io.spiffy.stream.service.FollowerService;

@RequestMapping("/api/stream/follow")
public class FollowAPI extends API<FollowInput, BooleanOutput, FollowerService> {

    @Inject
    public FollowAPI(final FollowerService service) {
        super(FollowInput.class, service);
    }

    protected BooleanOutput api(final FollowInput input) {
        if (FollowInput.Action.FOLLOW.equals(input.getAction())) {
            service.post(input.getFollowerId(), input.getFolloweeId());
        } else if (FollowInput.Action.UNFOLLOW.equals(input.getAction())) {
            service.unfollow(input.getFollowerId(), input.getFolloweeId());
        }

        return new BooleanOutput(true);
    }
}