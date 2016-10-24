package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.api.user.input.SendRecoveryEmailInput;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/sendrecoveryemail")
public class SendRecoveryEmailAPI extends API<SendRecoveryEmailInput, PostOutput, AccountService> {

    @Inject
    public SendRecoveryEmailAPI(final AccountService service) {
        super(SendRecoveryEmailInput.class, service);
    }

    protected PostOutput api(final SendRecoveryEmailInput input) {
        final AccountEntity entity = service.sendRecoveryEmail(input.getEmail());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}