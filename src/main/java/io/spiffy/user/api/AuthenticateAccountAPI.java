package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.user.input.AuthenticateAccountInput;
import io.spiffy.common.api.user.output.AuthenticateAccountOutput;
import io.spiffy.common.exception.InvalidParameterException;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/authenticateaccount")
public class AuthenticateAccountAPI extends API<AuthenticateAccountInput, AuthenticateAccountOutput, AccountService> {

    @Inject
    public AuthenticateAccountAPI(final AccountService service) {
        super(AuthenticateAccountInput.class, service);
    }

    protected AuthenticateAccountOutput api(final AuthenticateAccountInput input) {
        try {
            return service.authenticate(input.getCredentials(), input.getSessionId(), input.getFingerprint(),
                    input.getUserAgent(), input.getIpAddress());
        } catch (final InvalidParameterException e) {
            return new AuthenticateAccountOutput(AuthenticateAccountOutput.Error.INVALID_EMAIL);
        }
    }
}