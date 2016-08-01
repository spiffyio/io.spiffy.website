package io.spiffy.common.api.invite.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.invite.call.InviteCall;
import io.spiffy.common.api.invite.input.InviteInput;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InviteClient extends Client {

    private final InviteCall inviteCall;

    public boolean invite(final String emailAddress) {
        final InviteInput input = new InviteInput(emailAddress);
        final PostOutput output = inviteCall.call(input);
        return output.getId() != null;
    }
}
