package io.spiffy.media.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.media.input.PostMediaInput;
import io.spiffy.common.api.media.output.PostMediaOutput;
import io.spiffy.media.entity.ContentEntity;
import io.spiffy.media.service.ContentService;

@RequestMapping("/api/media/postmedia")
public class PostMediaAPI extends API<PostMediaInput, PostMediaOutput, ContentService> {

    @Inject
    public PostMediaAPI(final ContentService service) {
        super(PostMediaInput.class, service);
    }

    protected PostMediaOutput api(final PostMediaInput input) {
        final ContentEntity entity = service.post(input.getAccountId(), input.getIdempotentId(), input.getType(),
                input.getValue(), input.getThumbnail());
        if (entity == null) {
            return new PostMediaOutput("error");
        }

        return new PostMediaOutput(entity.getName());
    }
}