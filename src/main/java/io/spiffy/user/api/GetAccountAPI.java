package io.spiffy.user.api;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.user.input.GetAccountInput;
import io.spiffy.common.api.user.output.GetAccountOutput;
import io.spiffy.common.dto.Account;
import io.spiffy.user.entity.AccountEntity;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/getaccount")
public class GetAccountAPI extends API<GetAccountInput, GetAccountOutput, AccountService> {

    @Inject
    public GetAccountAPI(final AccountService service) {
        super(GetAccountInput.class, service);
    }

    protected GetAccountOutput api(final GetAccountInput input) {
        final Account account = input.getAccount();

        final AccountEntity entity;
        if (account.getId() != null) {
            entity = service.get(account.getId());
        } else if (StringUtils.isNotEmpty(account.getUsername())) {
            entity = service.getByUserName(account.getUsername());
        } else if (StringUtils.isNotEmpty(account.getEmail())) {
            entity = service.getByEmailAddress(account.getEmail());
        } else {
            entity = null;
        }

        final GetAccountOutput output = new GetAccountOutput();
        if (entity == null) {
            return output;
        }

        output.setAccount(
                new Account(entity.getId(), entity.getUserName(), entity.getEmailAddress(), entity.getEmailVerified()));

        return output;
    }
}