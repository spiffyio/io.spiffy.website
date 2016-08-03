package io.spiffy.media.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.media.input.PostMediaInput;
import io.spiffy.media.entity.MediaEntity;
import io.spiffy.media.service.MediaService;

@RequestMapping("/api/media/postmedia")
public class PostMediaAPI extends API<PostMediaInput, PostOutput, MediaService> {

    @Inject
    public PostMediaAPI(final MediaService service) {
        super(PostMediaInput.class, service);
    }

    protected PostOutput api(final PostMediaInput input) {
        final MediaEntity entity = service.post(input.getIdempotentId(), input.getType(), input.getValue());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}