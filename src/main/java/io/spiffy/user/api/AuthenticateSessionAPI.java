package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.user.input.AuthenticateSessionInput;
import io.spiffy.common.api.user.output.AuthenticateSessionOutput;
import io.spiffy.common.dto.Account;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/authenticatesession")
public class AuthenticateSessionAPI extends API<AuthenticateSessionInput, AuthenticateSessionOutput, AccountService> {

    @Inject
    public AuthenticateSessionAPI(final AccountService service) {
        super(AuthenticateSessionInput.class, service);
    }

    protected AuthenticateSessionOutput api(final AuthenticateSessionInput input) {
        final AccountEntity entity = service.authenticate(input.getSessionId(), input.getToken(), input.getUserAgent(),
                input.getIpAddress());
        if (entity == null) {
            return new AuthenticateSessionOutput();
        }

        final Account account = new Account(entity.getId(), entity.getUserName());

        return new AuthenticateSessionOutput(account);
    }
}