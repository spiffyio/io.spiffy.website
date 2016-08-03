package io.spiffy.common.api.user.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.call.PostAccountCall;
import io.spiffy.common.api.user.call.RegisterAccountCall;
import io.spiffy.common.api.user.input.PostAccountInput;
import io.spiffy.common.api.user.input.RegisterAccountInput;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserClient extends Client {

    final PostAccountCall postAccountCall;
    final RegisterAccountCall registerAccountCall;

    public long postAccount(final String userName, final String emailAddress) {
        final PostAccountInput input = new PostAccountInput(userName, emailAddress);
        final PostOutput output = postAccountCall.call(input);
        return output.getId();
    }

    public long registerAccount(final String userName, final String emailAddress, final String password) {
        final RegisterAccountInput input = new RegisterAccountInput(userName, emailAddress, password);
        final PostOutput output = registerAccountCall.call(input);
        return output.getId();
    }
}
