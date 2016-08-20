package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.email.client.EmailClient;
import io.spiffy.common.api.user.input.AuthenticateSessionInput;
import io.spiffy.common.api.user.output.AuthenticateSessionOutput;
import io.spiffy.common.dto.Account;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/authenticatesession")
public class AuthenticateSessionAPI extends API<AuthenticateSessionInput, AuthenticateSessionOutput, AccountService> {

    private final EmailClient emailClient;

    @Inject
    public AuthenticateSessionAPI(final AccountService service, final EmailClient emailClient) {
        super(AuthenticateSessionInput.class, service);
        this.emailClient = emailClient;
    }

    protected AuthenticateSessionOutput api(final AuthenticateSessionInput input) {
        final AccountEntity entity = service.authenticate(input.getSessionId(), input.getToken(), input.getUserAgent(),
                input.getIpAddress());
        if (entity == null) {
            return new AuthenticateSessionOutput();
        }

        final Account account = new Account(entity.getId(), entity.getUserName(),
                emailClient.getEmailAddress(entity.getEmailAddressId()), entity.getEmailVerified());

        return new AuthenticateSessionOutput(account);
    }
}