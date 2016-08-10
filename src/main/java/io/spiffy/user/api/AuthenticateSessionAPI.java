package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.user.input.AuthenticateSessionInput;
import io.spiffy.common.api.user.output.AuthenticateSessionOutput;
import io.spiffy.user.entity.SessionEntity;
import io.spiffy.user.service.SessionService;

@RequestMapping("/api/user/authenticatesession")
public class AuthenticateSessionAPI extends API<AuthenticateSessionInput, AuthenticateSessionOutput, SessionService> {

    @Inject
    public AuthenticateSessionAPI(final SessionService service) {
        super(AuthenticateSessionInput.class, service);
    }

    protected AuthenticateSessionOutput api(final AuthenticateSessionInput input) {
        final SessionEntity entity = service.authenticate(input.getSessionId(), input.getToken(), input.getUserAgent(),
                input.getIpAddress());
        if (entity == null) {
            return new AuthenticateSessionOutput();
        }
        return new AuthenticateSessionOutput(entity.getAccountId());
    }
}