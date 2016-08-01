package io.spiffy.common.api.security.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.security.call.*;
import io.spiffy.common.api.security.input.MatchesStringInput;
import io.spiffy.common.api.security.input.PostStringInput;
import io.spiffy.common.api.security.output.GetStringOutput;
import io.spiffy.common.api.security.output.MatchesStringOutput;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SecurityClient extends Client {

    private final EncryptStringCall encryptStringCall;
    private final GetEncryptedStringCall getEncryptedStringCall;
    private final HashStringCall hashStringCall;
    private final MatchesEncryptedStringCall matchesEncryptedStringCall;
    private final MatchesHashedStringCall matchesHashedStringCall;

    public long encryptString(final String plainString) {
        final PostStringInput input = new PostStringInput(plainString);
        final PostOutput output = encryptStringCall.call(input);
        return output.getId();
    }

    public String decryptString(final long id) {
        final GetInput input = new GetInput(id);
        final GetStringOutput output = getEncryptedStringCall.call(input);
        return output.getPlainString();
    }

    public long hashString(final String plainString) {
        final PostStringInput input = new PostStringInput(plainString);
        final PostOutput output = hashStringCall.call(input);
        return output.getId();
    }

    public boolean matchesEncryptedString(final long id, final String plainString) {
        final MatchesStringInput input = new MatchesStringInput(id, plainString);
        final MatchesStringOutput output = matchesEncryptedStringCall.call(input);
        return output.getMatches();
    }

    public boolean matchesHashedString(final long id, final String plainString) {
        final MatchesStringInput input = new MatchesStringInput(id, plainString);
        final MatchesStringOutput output = matchesHashedStringCall.call(input);
        return output.getMatches();
    }
}
