package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.input.PostAccountInput;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/postaccount")
public class PostAccountAPI extends API<PostAccountInput, PostOutput, AccountService> {

    @Inject
    public PostAccountAPI(final AccountService service) {
        super(PostAccountInput.class, service);
    }

    protected PostOutput api(final PostAccountInput input) {
        final AccountEntity entity = service.post(input.getUserName(), input.getEmailAddress());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}