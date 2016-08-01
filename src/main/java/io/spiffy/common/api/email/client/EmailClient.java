package io.spiffy.common.api.email.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.email.call.GetEmailAddressCall;
import io.spiffy.common.api.email.call.PostEmailAddressCall;
import io.spiffy.common.api.email.input.PostEmailAddressInput;
import io.spiffy.common.api.email.output.GetEmailAddressOutput;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmailClient extends Client {

    private final GetEmailAddressCall getEmailAddressCall;
    private final PostEmailAddressCall postEmailAddressCall;

    public String getEmailAddress(final long id) {
        final GetInput input = new GetInput(id);
        final GetEmailAddressOutput output = getEmailAddressCall.call(input);
        return output.getAddress();
    }

    public long postEmailAddress(final String emailAddress) {
        final PostEmailAddressInput input = new PostEmailAddressInput(emailAddress);
        final PostOutput output = postEmailAddressCall.call(input);
        return output.getId();
    }
}
