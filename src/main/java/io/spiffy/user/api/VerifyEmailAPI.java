package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.VerifyEmailInput;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/verifyemail")
public class VerifyEmailAPI extends API<VerifyEmailInput, PostOutput, AccountService> {

    @Inject
    public VerifyEmailAPI(final AccountService service) {
        super(VerifyEmailInput.class, service);
    }

    protected PostOutput api(final VerifyEmailInput input) {
        final AccountEntity entity = service.verify(input.getToken());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}