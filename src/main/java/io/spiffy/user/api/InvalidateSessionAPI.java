package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.InvalidateSessionInput;
import io.spiffy.user.entity.SessionEntity;
import io.spiffy.user.service.SessionService;

@RequestMapping("/api/user/invalidatesession")
public class InvalidateSessionAPI extends API<InvalidateSessionInput, PostOutput, SessionService> {

    @Inject
    public InvalidateSessionAPI(final SessionService service) {
        super(InvalidateSessionInput.class, service);
    }

    protected PostOutput api(final InvalidateSessionInput input) {
        final SessionEntity entity = service.invalidate(input.getSessionId(), input.getToken(), input.getUserAgent(),
                input.getIpAddress());
        if (entity == null) {
            return new PostOutput();
        }

        return new PostOutput(entity.getId());
    }
}