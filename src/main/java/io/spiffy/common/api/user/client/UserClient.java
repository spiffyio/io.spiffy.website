package io.spiffy.common.api.user.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.user.call.PostAccountCall;
import io.spiffy.common.api.user.input.PostAccountInput;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserClient extends Client {

    final PostAccountCall postAccountCall;

    public long postAccount(final String userName, final String emailAddress) {
        final PostAccountInput input = new PostAccountInput(userName, emailAddress);
        final PostOutput output = postAccountCall.call(input);
        return output.getId();
    }
}
