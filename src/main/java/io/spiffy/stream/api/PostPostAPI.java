package io.spiffy.stream.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.stream.input.PostPostInput;
import io.spiffy.stream.entity.PostEntity;
import io.spiffy.stream.service.PostService;

@RequestMapping("/api/stream/postpost")
public class PostPostAPI extends API<PostPostInput, PostOutput, PostService> {

    @Inject
    public PostPostAPI(final PostService service) {
        super(PostPostInput.class, service);
    }

    protected PostOutput api(final PostPostInput input) {
        final PostEntity entity = service.post(input.getIdempotentId(), input.getAccountId(), input.getMediaId(),
                input.getTitle(), input.getDescription());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}