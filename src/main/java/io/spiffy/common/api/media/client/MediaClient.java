package io.spiffy.common.api.media.client;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.media.call.GetAccountMediaCall;
import io.spiffy.common.api.media.call.GetMediaCall;
import io.spiffy.common.api.media.call.PostMediaCall;
import io.spiffy.common.api.media.dto.ContentType;
import io.spiffy.common.api.media.dto.MediaType;
import io.spiffy.common.api.media.input.GetAccountMediaInput;
import io.spiffy.common.api.media.input.PostMediaInput;
import io.spiffy.common.api.media.output.GetAccountMediaOutput;
import io.spiffy.common.api.media.output.GetMediaOutput;
import io.spiffy.common.api.media.output.PostMediaOutput;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class MediaClient extends Client {

    private final GetAccountMediaCall getAccountMediaCall;
    private final GetMediaCall getMediaCall;
    private final PostMediaCall postMediaCall;

    public GetAccountMediaOutput getAccountMedia(final long id, final ContentType type, final Long first, final int maxResults,
            final boolean includeFirst) {
        final GetAccountMediaInput input = new GetAccountMediaInput(id, type, first, maxResults, includeFirst);
        final GetAccountMediaOutput output = getAccountMediaCall.call(input);
        return output;
    }

    public GetMediaOutput getMedia(final long id) {
        final GetInput input = new GetInput(id);
        final GetMediaOutput output = getMediaCall.call(input);
        return output;
    }

    public String postMedia(final long accountId, final String idempotentId, final MediaType type, final byte[] value) {
        final PostMediaInput input = new PostMediaInput(accountId, idempotentId, type, value);
        final PostMediaOutput output = postMediaCall.call(input);
        return output.getName();
    }
}
