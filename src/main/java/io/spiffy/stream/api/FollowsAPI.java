package io.spiffy.stream.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.output.BooleanOutput;
import io.spiffy.common.api.stream.input.FollowsInput;
import io.spiffy.stream.entity.FollowerEntity;
import io.spiffy.stream.service.FollowerService;

@RequestMapping("/api/stream/follows")
public class FollowsAPI extends API<FollowsInput, BooleanOutput, FollowerService> {

    @Inject
    public FollowsAPI(final FollowerService service) {
        super(FollowsInput.class, service);
    }

    protected BooleanOutput api(final FollowsInput input) {
        final FollowerEntity entity = service.get(input.getFollowerId(), input.getFolloweeId());
        return new BooleanOutput(entity != null);
    }
}