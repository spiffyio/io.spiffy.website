package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.SendVerifyEmailInput;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/sendverifyemail")
public class SendVerifyEmailAPI extends API<SendVerifyEmailInput, PostOutput, AccountService> {

    @Inject
    public SendVerifyEmailAPI(final AccountService service) {
        super(SendVerifyEmailInput.class, service);
    }

    protected PostOutput api(final SendVerifyEmailInput input) {
        final AccountEntity entity = service.sendVerificationEmail(input.getAccountId(), input.getEmail(),
                input.getIdempotentId());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}