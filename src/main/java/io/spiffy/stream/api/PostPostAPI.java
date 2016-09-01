package io.spiffy.stream.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.output.GetMediaOutput;
import io.spiffy.common.api.stream.input.PostPostInput;
import io.spiffy.common.api.stream.output.PostPostOutput;
import io.spiffy.stream.entity.PostEntity;
import io.spiffy.stream.service.PostService;

@RequestMapping("/api/stream/postpost")
public class PostPostAPI extends API<PostPostInput, PostPostOutput, PostService> {

    private final MediaClient mediaClient;

    @Inject
    public PostPostAPI(final PostService service, final MediaClient mediaClient) {
        super(PostPostInput.class, service);
        this.mediaClient = mediaClient;
    }

    protected PostPostOutput api(final PostPostInput input) {
        final PostEntity entity = service.post(input.getIdempotentId(), input.getAccountId(), input.getMediaId(),
                input.getTitle(), input.getDescription());
        if (entity == null) {
            return new PostPostOutput(PostPostOutput.Error.UNKNOWN_ERROR);
        }

        final GetMediaOutput mediaOutput = mediaClient.getMedia(entity.getMediaId());
        if (mediaOutput.getError() == null) {
            service.process(entity.getId());
        }

        return new PostPostOutput(entity.getName());
    }
}