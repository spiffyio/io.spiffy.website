package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.user.input.RegisterAccountInput;
import io.spiffy.common.api.user.output.RegisterAccountOutput;
import io.spiffy.common.exception.InvalidParameterException;
import io.spiffy.common.exception.ValidationException;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/registeraccount")
public class RegisterAccountAPI extends API<RegisterAccountInput, RegisterAccountOutput, AccountService> {

    @Inject
    public RegisterAccountAPI(final AccountService service) {
        super(RegisterAccountInput.class, service);
    }

    protected RegisterAccountOutput api(final RegisterAccountInput input) {
        try {
            return service.register(input.getUserName(), input.getEmailAddress(), input.getPassword());
        } catch (final ValidationException e) {
            if (e.getMessage().contains("CredentialEntity.password")) {
                return new RegisterAccountOutput(RegisterAccountOutput.Error.INVALID_PASSWORD);
            }
            return new RegisterAccountOutput(RegisterAccountOutput.Error.INVALID_USERNAME);
        } catch (final InvalidParameterException e) {
            return new RegisterAccountOutput(RegisterAccountOutput.Error.INVALID_EMAIL);
        }
    }
}