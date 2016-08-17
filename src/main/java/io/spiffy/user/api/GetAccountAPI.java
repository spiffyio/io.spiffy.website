package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.user.output.GetAccountOutput;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/getaccount")
public class GetAccountAPI extends API<GetInput, GetAccountOutput, AccountService> {

    @Inject
    public GetAccountAPI(final AccountService service) {
        super(GetInput.class, service);
    }

    protected GetAccountOutput api(final GetInput input) {
        final AccountEntity entity = service.get(input.getId());
        if (entity == null) {
            return new GetAccountOutput();
        }

        return new GetAccountOutput(entity.getUserName());
    }
}