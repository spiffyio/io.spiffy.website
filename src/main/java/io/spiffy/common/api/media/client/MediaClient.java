package io.spiffy.common.api.media.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.media.call.GetMediaCall;
import io.spiffy.common.api.media.call.PostMediaCall;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.media.input.GetMediaOutput;
import io.spiffy.common.api.media.input.PostMediaInput;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class MediaClient extends Client {

    private final GetMediaCall getMediaCall;
    private final PostMediaCall postMediaCall;

    public String getMedia(final long id) {
        final GetInput input = new GetInput(id);
        final GetMediaOutput output = getMediaCall.call(input);

        return output.getUrl();
    }

    public long postMedia(final String idempotentId, final MediaType type, final byte[] value) {
        final PostMediaInput input = new PostMediaInput(idempotentId, type, value);
        final PostOutput output = postMediaCall.call(input);
        return output.getId();
    }
}
