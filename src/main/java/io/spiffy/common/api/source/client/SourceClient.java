package io.spiffy.common.api.source.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.common.api.source.call.PostUrlCall;
import io.spiffy.common.api.source.input.PostUrlInput;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SourceClient extends Client {

    private final PostUrlCall postUrlCall;

    public long postUrl(final String url, final String domain, final String entityId, final String entityOwner) {
        final PostUrlInput input = new PostUrlInput(url, domain, entityId, entityOwner);
        final PostOutput output = postUrlCall.call(input);
        return output.getId();
    }
}
