package io.spiffy.user.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.user.input.RecoverAccountInput;
import io.spiffy.common.api.user.output.RecoverAccountOutput;
import io.spiffy.common.exception.InvalidParameterException;
import io.spiffy.common.exception.ValidationException;
import io.spiffy.user.service.AccountService;

@RequestMapping("/api/user/recoveraccount")
public class RecoverAccountAPI extends API<RecoverAccountInput, RecoverAccountOutput, AccountService> {

    @Inject
    public RecoverAccountAPI(final AccountService service) {
        super(RecoverAccountInput.class, service);
    }

    protected RecoverAccountOutput api(final RecoverAccountInput input) {
        try {
            return service.recover(input.getEmail(), input.getToken(), input.getPassword(), input.getSessionId(),
                    input.getFingerprint(), input.getUserAgent(), input.getIpAddress());
        } catch (final ValidationException e) {
            return new RecoverAccountOutput(RecoverAccountOutput.Error.INVALID_PASSWORD);
        } catch (final InvalidParameterException e) {
            return new RecoverAccountOutput(RecoverAccountOutput.Error.INVALID_EMAIL);
        }
    }
}