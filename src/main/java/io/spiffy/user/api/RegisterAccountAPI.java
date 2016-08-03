package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.RegisterAccountInput;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/registeraccount")
public class RegisterAccountAPI extends API<RegisterAccountInput, PostOutput, AccountService> {

    @Inject
    public RegisterAccountAPI(final AccountService service) {
        super(RegisterAccountInput.class, service);
    }

    protected PostOutput api(final RegisterAccountInput input) {
        final AccountEntity entity = service.register(input.getUserName(), input.getEmailAddress(), input.getPassword());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}